package com.dtdl.sherlock.mtsherlock.repository

import com.dtdl.sherlock.mtsherlock.model.User
import org.springframework.data.jpa.repository.EntityGraph
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?


    @EntityGraph(attributePaths = arrayOf("authorities"))
    fun findOneWithAuthoritiesByUsername(username: String?): Optional<User>

//    @EntityGraph(attributePaths = arrayOf("authorities"))
//    fun findOneWithAuthoritiesByEmailIgnoreCase(email: String?): Optional<User>

}
