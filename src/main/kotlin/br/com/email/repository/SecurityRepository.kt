package br.com.email.repository

import br.com.email.entities.Security
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository

@Repository
interface SecurityRepository : JpaRepository<Security?, Long?> {

    @Query("SELECT s FROM Security s WHERE s.email =:email")
    fun confirmEmailAddress(@Param("email") email: String?): Security?

    @Query("SELECT s FROM Security s WHERE s.code =:code")
    fun checkCodeExistent(@Param("code") code: String?): Security?
}
