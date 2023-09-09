package com.example.demo.services

import com.example.demo.data.UserMap
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService: UserDetailsService {

    override fun loadUserByUsername(username: String?): UserDetails {
        return UserMap.getUserById(username!!)
    }
}