package com.dtdl.sherlock.mtsherlock

import com.dtdl.sherlock.mtsherlock.model.Authority
import com.dtdl.sherlock.mtsherlock.model.User
import com.dtdl.sherlock.mtsherlock.repository.AuthorityRepository
import com.dtdl.sherlock.mtsherlock.repository.UserRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder


@SpringBootApplication
class MtSherlockApplication(
    val userRepository: UserRepository,
    val authorityRepository: AuthorityRepository,
    val bCryptPasswordEncoder: BCryptPasswordEncoder
) :
    CommandLineRunner {


    override fun run(vararg args: String?) {


        val encodedPassword = bCryptPasswordEncoder.encode("admin")
        val adminRole = authorityRepository.save(Authority("ROLE_ADMIN"))

        val admin = userRepository.save(
            User(
                name = "Supreet Singh",
                username = "admin",
                password = encodedPassword,
                authorities = setOf(adminRole)
            )
        )


    }
}

fun main(args: Array<String>) {

    runApplication<MtSherlockApplication>(*args)

}
