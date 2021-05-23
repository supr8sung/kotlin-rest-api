package com.dtdl.sherlock.mtsherlock.security.util

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import java.util.stream.Collectors

import org.springframework.security.core.authority.SimpleGrantedAuthority

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User


class SecurityUtils {
    private val LOG: Logger = LoggerFactory.getLogger(SecurityUtils::class.java)

    fun getCurrentUsername(): String? {
        val authentication = SecurityContextHolder.getContext().authentication

        authentication ?: let {
            LOG.debug("no authentication in security context found");
            return null;
        }
        var username: String? = null
        if (authentication.principal is UserDetails) {
            val springSecurityUser = authentication.principal as UserDetails
            username = springSecurityUser.username
        } else if (authentication.principal is String) {
            username = authentication.principal as String
        }

        LOG.debug("found username '{}' in security context", username)

        return username
    }



}