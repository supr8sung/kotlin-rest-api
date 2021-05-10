package com.dtdl.sherlock.mtsherlock.config.util

import com.dtdl.sherlock.mtsherlock.constants.JWT_AUTHORIZATION_PREFIX
import com.dtdl.sherlock.mtsherlock.exception.MtSherlockAuthenticationException
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.io.Serializable
import java.util.*
import java.util.function.Function


@Component
class JwtTokenUtil : Serializable {
    private val serialVersionUID = -2550185165626007488L
    val JWT_TOKEN_VALIDITY = (5 * 60 * 60).toLong()

    @Value("\${jwt.secret}")
    private val secret: String? = null


    fun getUsernameFromToken(token: String): String? {

        return getClaimFromToken(token, Claims::getSubject)
    }

    fun getExpirationDateFromToken(token: String): Date? {
        return getClaimFromToken(token, Claims::getExpiration)
    }

    fun <T> getClaimFromToken(token: String, claimsResolver: Function<Claims, T>): T {
        val claims = getAllClaimsFromToken(token)
        return claimsResolver.apply(claims)
    }

    private fun getAllClaimsFromToken(token: String): Claims {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String): Boolean {
        val expiration = getExpirationDateFromToken(token)
        return expiration!!.before(Date())
    }

    fun generateToken(userDetails: UserDetails): String {
        val claims: Map<String, Any> = HashMap()
        return doGenerateToken(claims, userDetails.username)
    }

    private fun doGenerateToken(claims: Map<String, Any>, subject: String): String {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + JWT_TOKEN_VALIDITY * 1000))
            .signWith(SignatureAlgorithm.HS512, secret).compact()
    }

    //validate token
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        val username = getUsernameFromToken(token)
        return username == userDetails.username && !isTokenExpired(token)
    }

    fun getJWt(authorizationHeader: String?): String {

        if (Objects.isNull(authorizationHeader) || !authorizationHeader!!.startsWith(JWT_AUTHORIZATION_PREFIX)) {
            throw MtSherlockAuthenticationException("Invalid Jwt token")
        }
        return authorizationHeader.substring(7);

    }
//
//    fun getLoggedInUser(username: String): User {
//        val user = userService.findByUserName(username);
//        return user ?: throw MtSherlockAuthenticationException("Username not found")
//    }
//
//    fun extractJwtRoles(claims: Jws<Claims>): ArrayList<*>? {
//        return claims.body.get("roles", ArrayList::class.java);
//    }
//
//    fun validateAndGetJwtClaims(jwt: String, bffCacheClientLoader: String): Jws<Claims> {
//        val claims: Jws<Claims>
//        try {
//            claims = Jwts.parser().setSigningKey(getpublicKey(bffCacheClientLoader)).parseClaimsJws(jwt)
//        } catch (ex: ExpiredJwtException) {
//            throw MtSherlockAuthenticationException("Jwt token expired")
//        } catch (ex: SignatureException) {
//            throw MtSherlockAuthenticationException("Invalid signature")
//        }
//        verifyJwtStructure(claims)
//        validateIssuerAndAudience(claims)
//        return claims
//
//    }
//
//    private fun getpublicKey(bffCacheClientLoader: String): Any {
//
//    }
//
//    private fun validateIssuerAndAudience(claims: Jws<Claims>?) {
//        TODO("Not yet implemented")
//    }
//
//    private fun verifyJwtStructure(claims: Jws<Claims>) {
//        if (Objects.isNull(claims) || isSubmissingInClaims(claims) || isRolesMissingInClaims(claims)) {
//            throw MtSherlockAuthenticationException("Invalid format for Jwt token")
//        }
//    }
//
//    private fun isRolesMissingInClaims(claims: Jws<Claims>) {
//        extractJwtRoles(claims)
//    }
//
//    private fun isSubmissingInClaims(claims: Jws<Claims>?): Boolean = claims?.body?.subject.isNullOrBlank()
//
//
//    fun extractUserName(claims: Jws<Claims>) = claims.body.subject
}