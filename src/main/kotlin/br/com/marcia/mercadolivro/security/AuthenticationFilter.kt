package br.com.marcia.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import br.com.marcia.mercadolivro.controller.request.LoginRequest
import br.com.marcia.mercadolivro.exception.AuthenticationException
import br.com.marcia.mercadolivro.repository.CustomerRepository
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import java.lang.Exception
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationFilter(
    authenticationManager: AuthenticationManager,
    private val customerRepository: CustomerRepository,
    private val jwtUtil: JwtUtil

) : UsernamePasswordAuthenticationFilter(authenticationManager) {

    // Este método será executado na rota POST /login
    // attemptAuthentication -> Sobrescrever tentativa de autenticação
    // HttpServletRequest e HttpServletResponse -> sem '?' -> não podem ser nullable
    override fun attemptAuthentication(request: HttpServletRequest, response: HttpServletResponse): Authentication {

         try {

             // Recuperando os dados do request (email e password)
             val loginRequest = jacksonObjectMapper().readValue(request.inputStream, LoginRequest::class.java)

             // Buscar no banco de dados, só precisa do id, não manter outros dados como email
             val id = customerRepository.findByEmail(loginRequest.email)?.id

             // Autenticação
             val authToken = UsernamePasswordAuthenticationToken(id, loginRequest.password)
             return authenticationManager.authenticate(authToken)


         } catch (ex: Exception) {
             throw AuthenticationException("Falha ao autenticar", "999")
         }

    }

    // Autenticação com sucesso
    override fun successfulAuthentication(request: HttpServletRequest,
                                          response: HttpServletResponse,
                                          chain: FilterChain,
                                          authResult: Authentication) {

        //Parâmetro 'authResult' é o UserCustomDetails criado em UserDetailsCustomService
        val id = (authResult.principal as UserCustomDetails).id

        // Adicionando o token no header 'Authorization'
        val token = jwtUtil.generateToken(id)
        response.addHeader("Authorization", "Bearer $token")
    }

}