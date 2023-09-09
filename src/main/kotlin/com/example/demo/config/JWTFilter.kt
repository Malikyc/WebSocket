package com.example.demo.config

import com.example.demo.data.UserMap
import com.example.demo.services.CustomUserDetailsService
import com.example.demo.utility.JWTUtil
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter
@Component
class JWTFilter(private val util : JWTUtil,private val userDetailsService: CustomUserDetailsService) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {

        val head : String? = request.getHeader("Authorization")

        if(!head.isNullOrBlank() && head.startsWith("Bearer ")){
           val jwt : String =  head.substring(7)

            if (jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_NOT_FOUND)
            }
           try {
               val token : String = util.verifyAndRetrieveClaim(jwt)
               if(token.isNotBlank()) UserMap.addUser(token)
               val userDetails : UserDetails = UserMap.getUserById(token)
               val authToken = UsernamePasswordAuthenticationToken(userDetails,
                   null,
                   emptyList())
               authToken.details = WebAuthenticationDetailsSource().buildDetails(request)
               if( SecurityContextHolder.getContext().authentication == null){
               SecurityContextHolder.getContext().authentication = authToken
               }

           }
           catch (e:Exception){
               response.sendError(HttpServletResponse.SC_NOT_FOUND)
           }

        }
        else{
            response.sendError(HttpServletResponse.SC_NOT_FOUND)
        }
        filterChain.doFilter(request,response)

    }
}