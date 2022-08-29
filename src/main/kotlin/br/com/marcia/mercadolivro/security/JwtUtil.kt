package br.com.marcia.mercadolivro.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtUtil {

    @Value("\${jwt.expiration}")
    private val expiration: Long? = null

    @Value("\${jwt.secret}")
    private val secret: String? = null

    fun generateToken(id: Int): String {
        return Jwts.builder()
            .setSubject(id.toString()) // subject -> sub -> identificação do usuário
            .setExpiration(Date(System.currentTimeMillis() + expiration!!)) // validade do token (expiration é milisegundos)
            .signWith(SignatureAlgorithm.HS512, secret!!.toByteArray()) // 'secret' é assinatura, "stringão" aleatório
            .compact()
    }

}