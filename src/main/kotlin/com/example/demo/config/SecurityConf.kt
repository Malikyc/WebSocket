package com.example.demo.config

import com.example.demo.services.CustomUserDetailsService
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties.Authentication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConf(private val filter : JWTFilter) {

    @Bean
    public fun securityFilterChain(http : HttpSecurity) : SecurityFilterChain{
       return http.csrf{csrf -> csrf.disable()}
           .sessionManagement{session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)}
            .authorizeHttpRequests{requets -> requets.requestMatchers("")
                .permitAll().anyRequest().authenticated()}.
           addFilterBefore(filter,UsernamePasswordAuthenticationFilter::class.java).build()
    }
    @Bean
    public fun authenticationProvider(): AuthenticationProvider{
        val provider :DaoAuthenticationProvider = DaoAuthenticationProvider()
        provider.setUserDetailsService(CustomUserDetailsService())
        return provider
    }

    @Bean
    public  fun manager(config : AuthenticationConfiguration) : AuthenticationManager{
        return config.authenticationManager
    }



}