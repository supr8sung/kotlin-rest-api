package com.dtdl.sherlock.mtsherlock.security.jwt

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.security.SecurityException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.security.Key
import java.util.*
import java.util.stream.Collectors


@Component
class TokenProvider : InitializingBean {
    constructor(
        @Value("\${jwt.base64-secret}") base64Secret: String,
        @Value("\${jwt.token-validity-in-seconds}") tokenValidityInSeconds: Long,
        @Value("\${jwt.token-validity-in-seconds-for-remember-me}") tokenValidityInSecondsForRememberMe: Long
    ) {
        this.base64Secret = base64Secret;
        this.tokenValidityInMilliseconds = tokenValidityInSeconds * 1000;
        this.tokenValidityInMillisecondsForRememberMe = tokenValidityInSecondsForRememberMe * 1000;
    }


    private val log: Logger = LoggerFactory.getLogger(TokenProvider::class.java)


    private val AUTHORITIES_KEY = "auth"

    private var base64Secret: String;
    private var tokenValidityInMilliseconds: Long;
    private var tokenValidityInMillisecondsForRememberMe: Long;

    private var key: Key? = null

    fun validateToken(authToken: String?): Boolean {
        try {
            Jwts.parser().setSigningKey(key).parseClaimsJws(authToken);
            return true;
        } catch (ex: Exception) {
            when (ex) {
                is SecurityException -> {
                    log.info("Invalid JWT signature.");
                    log.trace("Invalid JWT signature trace: {}", ex);
                }
                is ExpiredJwtException -> {
                    log.info("Expired JWT token.");
                    log.trace("Expired JWT token trace: {}", ex);
                }
                is UnsupportedOperationException -> {
                    log.info("Unsupported JWT token.");
                    log.trace("Unsupported JWT token trace: {}", ex);
                }
                is IllegalArgumentException -> {
                    log.info("JWT token compact of handler are invalid.");
                    log.trace("JWT token compact of handler are invalid trace: {}", ex);
                }
            }

        }
        return false
    }


    fun getAuthentication(token: String?): Authentication {
        val claims: Claims = Jwts.parser().setSigningKey(key).parseClaimsJws(token).body

        val authorities: Collection<GrantedAuthority?> =
            Arrays.stream(claims[AUTHORITIES_KEY].toString().split(",").toTypedArray())
                .map { role: String? -> SimpleGrantedAuthority(role) }
                .collect(Collectors.toList())
        val principal = org.springframework.security.core.userdetails.User(claims.subject, "", authorities)

        return UsernamePasswordAuthenticationToken(principal, token, authorities)


    }


    override fun afterPropertiesSet() {
        val bytes = Decoders.BASE64.decode(base64Secret)
        this.key = Keys.hmacShaKeyFor(bytes);

    }

    fun createToken(authentication: Authentication, rememberMe: Boolean): String {
        val authorities = authentication.authorities.stream()
            .map(GrantedAuthority::getAuthority)
            .collect(Collectors.joining(","))
        val now = Date().time

        val validity: Date = if (rememberMe) {
            Date(now + tokenValidityInMillisecondsForRememberMe)
        } else {
            Date(now + tokenValidityInMilliseconds)
        }

        return Jwts.builder()
            .setSubject(authentication.name)
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS512)
            .setExpiration(validity)
            .compact()

    }
}



