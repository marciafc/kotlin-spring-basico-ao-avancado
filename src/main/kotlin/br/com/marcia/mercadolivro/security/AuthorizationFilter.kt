package br.com.marcia.mercadolivro.security

import br.com.marcia.mercadolivro.exception.AuthenticationException
import br.com.marcia.mercadolivro.service.UserDetailsCustomService
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthorizationFilter(
    authenticationManager: AuthenticationManager,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil

) : BasicAuthenticationFilter(authenticationManager) {

    //
    override fun doFilterInternal(request: HttpServletRequest,
                                  response: HttpServletResponse,
                                  chain: FilterChain) {

        // Recupera o token
        val authorization = request.getHeader("Authorization")

        // Validar o token para verificar o id do usuário e suas roles (se tem acesso a determinado recurso)
        if(authorization != null && authorization.startsWith("Bearer ")) {

            // Roles
            val auth = getAuthentication(authorization.split(" ")[1])

            // Passa para o Spring quem está tentando acessar o recurso
            SecurityContextHolder.getContext().authentication = auth
        }

        // Passar adiante para verificar se usuário pode acessar ou não
        chain.doFilter(request, response)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {

        if(!jwtUtil.isValidToken(token)) {
            throw AuthenticationException("Token inválido", "999")
        }

        // Recuperar o suject (id) e o customer
        val subject = jwtUtil.getSubject(token)
        val customer = userDetails.loadUserByUsername(subject)

        // Principal é o subject do customer
        // customer.authorities -> roles do customer
        return UsernamePasswordAuthenticationToken(customer, null, customer.authorities)
    }

}