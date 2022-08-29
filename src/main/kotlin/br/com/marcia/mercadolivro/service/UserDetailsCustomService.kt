package br.com.marcia.mercadolivro.service

import br.com.marcia.mercadolivro.exception.AuthenticationException
import br.com.marcia.mercadolivro.repository.CustomerRepository
import br.com.marcia.mercadolivro.security.UserCustomDetails
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsCustomService(
    private val customerRepository: CustomerRepository

): UserDetailsService {

    // Sobrescrever o método que carrega o usuário baseado em seu identificador (nessa app está usando id e não o email)
    override fun loadUserByUsername(id: String): UserDetails {

        val customer = customerRepository.findById(id.toInt())
            .orElseThrow { AuthenticationException("Usuário não encontrado", "999") }

        return UserCustomDetails(customer)
    }
}