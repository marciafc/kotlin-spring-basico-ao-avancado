package br.com.marcia.mercadolivro.security

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import br.com.marcia.mercadolivro.controller.response.ErrorResponse
import br.com.marcia.mercadolivro.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class CustomAuthenticationEntryPoint : AuthenticationEntryPoint {

    override fun commence(
            request: HttpServletRequest?,
            response: HttpServletResponse,
            authException: AuthenticationException? // Essa AuthenticationException é a do Spring, não é a do Mercado Livro
    ) {
        response.contentType = "application/json"
        response.status = HttpServletResponse.SC_UNAUTHORIZED
        val errorResponse = ErrorResponse(HttpStatus.UNAUTHORIZED.value(), Errors.ML002.message, Errors.ML002.code, null)

        // Montando json de resposta
        response.outputStream.print(jacksonObjectMapper().writeValueAsString(errorResponse))
    }
}