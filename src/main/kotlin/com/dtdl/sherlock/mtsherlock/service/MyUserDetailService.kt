package com.dtdl.sherlock.mtsherlock.service

import com.dtdl.sherlock.mtsherlock.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*


@Component
class MyUserDetailService(val userService: UserRepository) : UserDetailsService {
    override fun loadUserByUsername(username: String?): UserDetails {
        val user = userService.findByUsername(username!!)
        user ?: throw UsernameNotFoundException("User not found with given username")
        return User(user.username, user.password, ArrayList())
    }
}