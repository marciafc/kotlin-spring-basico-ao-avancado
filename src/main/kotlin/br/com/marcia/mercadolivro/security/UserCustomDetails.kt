package br.com.marcia.mercadolivro.security

import br.com.marcia.mercadolivro.enums.CustomerStatus
import br.com.marcia.mercadolivro.model.CustomerModel
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

class UserCustomDetails(val customerModel: CustomerModel): UserDetails {

    // Será utilizado no método 'successfulAuthentication' para gerar o token a ser adicionado no header
    val id: Int = customerModel.id!!

    // Descrição das roles em Role.kt
    override fun getAuthorities(): MutableCollection<out GrantedAuthority> = customerModel.roles.map { SimpleGrantedAuthority(it.description) }.toMutableList()

    // Password
    override fun getPassword(): String = customerModel.password

    // Username é o id, não é o email (isso pq não será deixado email no token)
    override fun getUsername(): String = customerModel.id.toString()

    // Não está sendo verificado
    override fun isAccountNonExpired(): Boolean = true

    // Não está sendo verificado
    override fun isAccountNonLocked(): Boolean = true

    // Controle de credencial expirada - não está sendo verificado
    override fun isCredentialsNonExpired(): Boolean = true

    // Usuário está ativo na base de dados
    override fun isEnabled(): Boolean = customerModel.status == CustomerStatus.ATIVO
}