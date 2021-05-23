package com.dtdl.sherlock.mtsherlock.security.jwt

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


class JWTFilter(var tokenProvider: TokenProvider) : GenericFilterBean() {

    private val LOG: Logger = LoggerFactory.getLogger(JWTFilter::class.java)


    private val AUTHORIZATION_HEADER = "Authorization"

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, filterChain: FilterChain?) {

        val httpServletRequest = request as HttpServletRequest
        val jwt = resolveToken(httpServletRequest)
        val requestURI = httpServletRequest.requestURI

        if (StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)) {
            val authentication: Authentication = tokenProvider.getAuthentication(jwt)
            SecurityContextHolder.getContext().authentication = authentication
            LOG.debug("set Authentication to security context for '{}', uri: {}", authentication.getName(), requestURI)
        } else {
            LOG.debug("no valid JWT token found, uri: {}", requestURI)
        }

        filterChain?.doFilter(request, response)
    }


    private fun resolveToken(request: HttpServletRequest): String? {
        val bearerToken: String? = request.getHeader(AUTHORIZATION_HEADER)
        if (StringUtils.hasText(bearerToken) && bearerToken!!.startsWith("Bearer"))
            return bearerToken.substring(7)
        return null;

    }
}
