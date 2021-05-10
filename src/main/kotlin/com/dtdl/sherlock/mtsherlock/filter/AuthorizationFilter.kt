package com.dtdl.sherlock.mtsherlock.filter

import com.dtdl.sherlock.mtsherlock.config.util.JwtTokenUtil
import com.dtdl.sherlock.mtsherlock.service.MyUserDetailService
import com.dtdl.sherlock.mtsherlock.service.UserService
import io.jsonwebtoken.ExpiredJwtException
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
import org.springframework.web.servlet.HandlerExceptionResolver
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AuthorizationFilter(
    val jwtTokenUtil: JwtTokenUtil,
    val userService: MyUserDetailService,
    @Qualifier("handlerExceptionResolver") val exceptionResolver: HandlerExceptionResolver

) : OncePerRequestFilter() {


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, chain: FilterChain) {

        val requestTokenHeader = request.getHeader("Authorization")

        var username: String? = null
        var jwtToken: String? = null

        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7)
            try {
                username = jwtTokenUtil.getUsernameFromToken(jwtToken)
            } catch (e: IllegalArgumentException) {
                println("Unable to get JWT Token")
            } catch (e: ExpiredJwtException) {
                println("JWT Token has expired")
            }
        } else {
            logger.warn("JWT Token does not begin with Bearer String")
        }

        // Once we get the token validate it.

        // Once we get the token validate it.
        if (username != null && SecurityContextHolder.getContext().authentication == null) {
            val userDetails: UserDetails = this.userService.loadUserByUsername(username)

            // if token is valid configure Spring Security to manually set
            // authentication
            if (jwtTokenUtil.validateToken(jwtToken!!, userDetails)) {
                val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
                    userDetails, null, userDetails.authorities
                )
                usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
                // After setting the Authentication in the context, we specify
                // that the current user is authenticated. So it passes the
                // Spring Security Configurations successfully.
                SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
            }
        }
        chain.doFilter(request, response)


//        try {
//            val jwt = jwtTokenUtil.getJWt(request.getHeader(AUTHORIZATION_HEADER_NAME))
//            var username = jwtTokenUtil.getUsernameFromToken(jwt)
//            if (username != null && SecurityContextHolder.getContext().authentication == null) {
//                val userDetails: UserDetails = this.myUserDetailService.loadUserByUsername(username)
//                if (jwtTokenUtil.validateToken(jwt, userDetails)) {
//                    val usernamePasswordAuthenticationToken = UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.authorities
//                    )
//                    usernamePasswordAuthenticationToken.details = WebAuthenticationDetailsSource().buildDetails(request)
//                    SecurityContextHolder.getContext().authentication = usernamePasswordAuthenticationToken
//                }
//            }
//            chain.doFilter(request, response)
//        } catch (ex: Exception) {
//        }
    }

}


//    override fun doFilterInternal(
//        request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain
//    ) {
//
//        try {
//            val jwt = jwtTokenUtil.getJWt(request.getHeader(AUTHORIZATION_HEADER_NAME))
//            processUserJWt(jwt, request.getHeader(Configurations.bffCacheInitialLoadHeaderKey))
//            filterChain.doFilter(request, response)
//        } catch (ex: Exception) {
//            exceptionResolver.resolveException(request, response, null, ex);
//
//        }
//
//    }
//
//    private fun processUserJWt(jwt: String, bffCacheClientLoader: String) {
//        val claims = jwtTokenUtil.validateAndGetJwtClaims(jwt, bffCacheClientLoader)
//        val username = jwtTokenUtil.extractUserName(claims)
//    }



