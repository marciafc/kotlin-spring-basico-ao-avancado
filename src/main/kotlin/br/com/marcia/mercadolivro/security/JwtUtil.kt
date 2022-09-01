package br.com.marcia.mercadolivro.security

import br.com.marcia.mercadolivro.exception.AuthenticationException
import io.jsonwebtoken.Claims
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

    fun isValidToken(token: String): Boolean {

        //Recuperar as claims do token
        val claims = getClaims(token)

        // Validando se tem o sub e se já não expirou
        if(claims.subject == null || claims.expiration == null || Date().after(claims.expiration)) {
            return false
        }
        return true
    }

    private fun getClaims(token: String): Claims {

        try {

            // Passa a secret para recuperar as claims no body
            return Jwts.parser().setSigningKey(secret!!.toByteArray()).parseClaimsJws(token).body

        } catch (ex : Exception) {
            throw AuthenticationException("Token Inválido", "999")
        }
    }

    fun getSubject(token: String): String {

        // Recuperar o subject (está no token, é o id do usuário)
        return getClaims(token).subject
    }

}