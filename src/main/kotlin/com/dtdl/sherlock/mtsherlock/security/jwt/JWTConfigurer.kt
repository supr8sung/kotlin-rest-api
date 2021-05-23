package com.dtdl.sherlock.mtsherlock.security.jwt

import org.springframework.security.config.annotation.SecurityConfigurerAdapter
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

class JWTConfigurer(val tokenProvider: TokenProvider) :
    SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity>() {


    override fun configure(http: HttpSecurity?) {
        val filter = JWTFilter(tokenProvider)
        http?.addFilterBefore(filter, UsernamePasswordAuthenticationFilter::class.java)

    }
}
