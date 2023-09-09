package com.example.demo.utility

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.impl.ClaimsHolder
import com.auth0.jwt.interfaces.Claim
import com.auth0.jwt.interfaces.DecodedJWT
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component

/*
Создаем класс который будет отвечать за проверку JWT
Я его не генерирую так как он прилетает со стороннего сервиса
 */
@Component
class JWTUtil {
    @Value("\${secret}")
    private lateinit var secret: String

    public fun verifyAndRetrieveClaim(token : String): String {
        val verifier : JWTVerifier = JWT
            .require(Algorithm.HMAC256(secret))
            .build()  // Создаем верифаер который в свою очередь используем для получения расшифрованного токена

        val jwt : DecodedJWT = verifier.verify(token)//тот самый расшифрованный токен
        val claims : MutableMap<String, Claim> = jwt.claims
        val token : String = claims.get("id").toString()
        return token
    }
}