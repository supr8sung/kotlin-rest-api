package com.dtdl.sherlock.mtsherlock.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.UrlBasedCorsConfigurationSource
import org.springframework.web.filter.CorsFilter
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse


@Configuration
class CorsConfig : OncePerRequestFilter() {
    @Bean
    fun corsFilter(): CorsFilter? {
        val source = UrlBasedCorsConfigurationSource()
        return CorsFilter(source)
    }

    override fun doFilterInternal(
        httpServletRequest: HttpServletRequest,
        httpServletResponse: HttpServletResponse,
        filterChain: FilterChain
    ) {

        httpServletResponse.addHeader("Access-Control-Allow-Origin", "http://localhost:4200")
        httpServletResponse.addHeader("Access-Control-Allow-Credentials", "true")
        httpServletResponse.addHeader(
            "Access-Control-Allow-Headers",
            "Origin, X-Requested-With, Content-Type, Accept, If-Modified-Since, signed-request, Authorization, redirect-url"
        )
        filterChain.doFilter(httpServletRequest, httpServletResponse)
    }


}