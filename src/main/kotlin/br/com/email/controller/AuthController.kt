package br.com.email.controller

import br.com.email.entities.Email
import br.com.email.service.AuthService
import br.com.email.vo.SecurityVO
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping(value = ["/auth"])
class AuthController {

    @Autowired
    private lateinit var authService: AuthService

    @PostMapping(value = ["/confirm-email-address"])
    fun confirmEmailAddress(@RequestBody email: Email): ResponseEntity<Email> {
        authService.confirmEmailAddress(email)
        return ResponseEntity.noContent().build()
    }

    @GetMapping(value = ["/check-code-existent/{code}"])
    fun checkCodeExistent(@PathVariable code: String): SecurityVO {
        return authService.checkCodeExistent(code)
    }
}
