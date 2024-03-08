package br.com.email.entities

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "tb_security")
data class Security(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long = 0,
    @Column(nullable = false)
    var code: String = "",
    @Column(nullable = false, unique = true)
    var email: String = "",
    @Column(nullable = false)
    var status: Status? = null,
    var counter: Int? = 0,
    @Column(nullable = false)
    var expiration: LocalDateTime? = null,
)

enum class Status {
    AVAILABLE,
    BLOCK
}
