package com.dtdl.sherlock.mtsherlock.security.service

import com.dtdl.sherlock.mtsherlock.model.User
import com.dtdl.sherlock.mtsherlock.repository.UserRepository
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import java.util.*
import java.util.stream.Collectors


@Component
class JwtUserDetailsService(val userRepository: UserRepository) : UserDetailsService {
    private val log: Logger = LoggerFactory.getLogger(JwtUserDetailsService::class.java)

    override fun loadUserByUsername(username: String?): UserDetails {

        log.debug("Authenticating user '{}'", username)

//        if (EmailValidator().isValid(username, null)) {
//
//            return userRepository.findOneWithAuthoritiesByEmailIgnoreCase(username)
//                .map { user ->
//                        createSpringSecurityUser(username, user)
//
//                }
//                .orElseThrow { UsernameNotFoundException("User with email $username was not found in the database") }
//        }

        val lowercaseLogin: String? = username?.toLowerCase(Locale.ENGLISH)
        return userRepository.findOneWithAuthoritiesByUsername(lowercaseLogin)
            .map { user -> createSpringSecurityUser(lowercaseLogin, user) }
            .orElseThrow { UsernameNotFoundException("User $lowercaseLogin was not found in the database") }
    }

    private fun createSpringSecurityUser(
        lowercaseLogin: String?,
        user: User
    ): org.springframework.security.core.userdetails.User {
//        if (!user.isActivated()) {
//            throw UserNotActivatedException("User $lowercaseLogin was not activated")
//        }
        val grantedAuthorities: List<GrantedAuthority> = user.authorities.stream()
            .map { authority -> SimpleGrantedAuthority(authority.name) }
            .collect(Collectors.toList())
        return org.springframework.security.core.userdetails.User(
            user.username,
            user.password,
            grantedAuthorities
        )
    }
}