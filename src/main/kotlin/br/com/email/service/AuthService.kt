package br.com.email.service

import br.com.email.entities.Email
import br.com.email.entities.Security
import br.com.email.entities.Status
import br.com.email.exceptions.ResourceNotFoundException
import br.com.email.repository.SecurityRepository
import br.com.email.utils.ConverterUtils.parseObject
import br.com.email.vo.SecurityVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.util.*

@Service
class AuthService {

    @Autowired
    private lateinit var securityRepository: SecurityRepository

    @Autowired
    private lateinit var emailService: EmailService

    private var counter: Int = 0

    fun confirmEmailAddress(email: Email) {
        val checkExistingEmail: Security? = securityRepository.confirmEmailAddress(email.email)
        if (checkExistingEmail != null) {
            checkExistingEmail.expiration?.let {
                if (it.isAfter(LocalDateTime.now())) {
                    if (checkExistingEmail.counter!! == 2) {
                        checkExistingEmail.status = Status.BLOCK
                        securityRepository.save(checkExistingEmail)
                    } else {
                        checkExistingEmail.counter = ++counter
                        securityRepository.save(checkExistingEmail)
                        emailService.sendEmailToConfirmation(
                            email.email,
                            "Confirmação do endereço de email",
                            "Code: ${checkExistingEmail.code}"
                        )
                    }
                } else {
                    checkExistingEmail.status = Status.AVAILABLE
                    counter = 0
                    checkExistingEmail.counter = 0
                    checkExistingEmail.expiration = LocalDateTime.now().plusMinutes(2)
                    securityRepository.save(checkExistingEmail)
                    emailService.sendEmailToConfirmation(
                        email.email,
                        "Confirmação do endereço de email",
                        "Code: ${checkExistingEmail.code}"
                    )
                }
            }
        } else {
            val saveNewKeySecurity = Security()
            saveNewKeySecurity.code = UUID.randomUUID().toString()
            saveNewKeySecurity.email = email.email
            saveNewKeySecurity.status = Status.AVAILABLE
            saveNewKeySecurity.expiration = LocalDateTime.now().plusMinutes(2)
            securityRepository.save(saveNewKeySecurity)
            emailService.sendEmailToConfirmation(
                email.email,
                "Confirmação do endereço de email",
                "Code: ${saveNewKeySecurity.code}"
            )
        }
    }

    fun checkCodeExistent(code: String): SecurityVO {
        val entity: Security? = securityRepository.checkCodeExistent(code)
        entity?.let {
            val security: Security = parseObject(it, Security::class.java)
            return parseObject(security, SecurityVO::class.java)
        } ?: throw ResourceNotFoundException("Code invalid")
    }
}
