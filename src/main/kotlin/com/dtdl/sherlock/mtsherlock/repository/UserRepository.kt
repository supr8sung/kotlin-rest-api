package com.dtdl.sherlock.mtsherlock.repository

import com.dtdl.sherlock.mtsherlock.model.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<User, UUID> {
    fun findByUsername(username: String): User?

}
