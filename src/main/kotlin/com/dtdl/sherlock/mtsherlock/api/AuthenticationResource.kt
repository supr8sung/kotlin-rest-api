package com.dtdl.sherlock.mtsherlock.api

import com.dtdl.sherlock.mtsherlock.dto.UserDTO
import com.dtdl.sherlock.mtsherlock.security.jwt.TokenProvider
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid


@RestController
@RequestMapping("/authenticate")
class AuthenticationResource(
    val tokenProvider: TokenProvider,
    val authenticationManagerBuilder: AuthenticationManagerBuilder
) {

    @PostMapping
    fun authorize(@Valid @RequestBody userDTO: UserDTO): ResponseEntity<JWTToken> {
        val authenticationToken = UsernamePasswordAuthenticationToken(userDTO.username, userDTO.password)
        val authentication = authenticationManagerBuilder.`object`.authenticate(authenticationToken)
        SecurityContextHolder.getContext().authentication = authentication
        val jwt = tokenProvider.createToken(authentication, true)
        val httpHeaders = HttpHeaders()
        httpHeaders.add(HttpHeaders.AUTHORIZATION, "Bearer $jwt")
        return ResponseEntity(JWTToken(jwt), httpHeaders, HttpStatus.OK);


    }

    class JWTToken(@get:JsonProperty("id_token") var idToken: String)
}