package br.com.email.vo

import jakarta.persistence.Column
import java.time.LocalDateTime

data class SecurityVO(
    var id: Long = 0,
    @Column(nullable = false)
    var code: String = "",
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    var status: StatusVO? = null,
    var counter: Int? = 0,
    @Column(nullable = false)
    var expiration: LocalDateTime? = null,
)
