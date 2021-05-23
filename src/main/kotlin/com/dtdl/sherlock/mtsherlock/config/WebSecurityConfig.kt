package com.dtdl.sherlock.mtsherlock.config

import com.dtdl.sherlock.mtsherlock.security.JwtAccessDeniedHandler
import com.dtdl.sherlock.mtsherlock.security.JwtAuthenticationEntryPoint
import com.dtdl.sherlock.mtsherlock.security.jwt.JWTConfigurer
import com.dtdl.sherlock.mtsherlock.security.jwt.TokenProvider
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.builders.WebSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.filter.CorsFilter


@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    val corsFilter: CorsFilter,
    val jwtAccessDeniedHandler: JwtAccessDeniedHandler,
    val tokenProvider: TokenProvider,
    val userDetailsService: UserDetailsService,
    val authenticationEntryPoint: JwtAuthenticationEntryPoint
) : WebSecurityConfigurerAdapter() {


    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()


    override fun configure(web: WebSecurity?) {
        web?.ignoring()
            ?.antMatchers(HttpMethod.OPTIONS, "/**")
            ?.antMatchers(
                "/",
                "/*.html",
                "/favicon.ico",
                "/**/*.html",
                "/**/*.css",
                "/**/*.js",
                "/h2-console/**"
            );
    }

    @Autowired
    fun configureGlobal(auth: AuthenticationManagerBuilder) {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder())
    }

    @Bean
    override fun authenticationManagerBean(): AuthenticationManager? {
        return super.authenticationManagerBean()
    }

    override fun configure(http: HttpSecurity) {
        http.csrf().disable()

            .addFilterBefore(corsFilter, UsernamePasswordAuthenticationFilter::class.java)

            .exceptionHandling()
            .authenticationEntryPoint(authenticationEntryPoint)
            .accessDeniedHandler(jwtAccessDeniedHandler)

            .and()
            .headers()
            .frameOptions()
            .sameOrigin()

            .and()
            .authorizeRequests()
            .antMatchers("/authenticate").permitAll()
            .antMatchers("/employee/delete").hasAnyAuthority("ROLE_ADMIN")
            .anyRequest().authenticated()
            .and()
            .apply(securityConfigurerAdapter())
    }


    private fun securityConfigurerAdapter(): JWTConfigurer {
        return JWTConfigurer(tokenProvider)
    }

}
