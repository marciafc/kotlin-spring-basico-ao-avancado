package br.com.marcia.mercadolivro.config

import br.com.marcia.mercadolivro.enums.Role
import br.com.marcia.mercadolivro.repository.CustomerRepository
import br.com.marcia.mercadolivro.security.AuthenticationFilter
import br.com.marcia.mercadolivro.security.AuthorizationFilter
import br.com.marcia.mercadolivro.security.JwtUtil
import br.com.marcia.mercadolivro.service.UserDetailsCustomService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val customerRepository: CustomerRepository,
    private val userDetails: UserDetailsCustomService,
    private val jwtUtil: JwtUtil

) : WebSecurityConfigurerAdapter() {

    private val PUBLIC_MATCHERS = arrayOf<String>()

    private val PUBLIC_POST_MATCHERS = arrayOf(
            "/customers"
    )

    // Rotas que somente quem tem a role de ADMIN pode acessar
    private val ADMIN_MATCHERS = arrayOf(
            "/admin/**"
    )

    override fun configure(auth: AuthenticationManagerBuilder) {

        // userDetails \ loadUserByUsername \ UserCustomDetails -> verificações se pode autenticar
        // passwordEncoder -> ensinar o Spring Security como encodar a senha para validar como está no banco (encodada)
        auth.userDetailsService(userDetails).passwordEncoder(bCryptPasswordEncoder())
    }

    override fun configure(http: HttpSecurity) {

        http.cors().and().csrf().disable()

        // .anyRequest().authenticated() -> Qlq request tem que ser autenticada,
        // exceto a rota de POST /customers (criar customer) que está em PUBLIC_POST_MATCHERS
        // e as rotas que estão em PUBLIC_MATCHERS.
        // O asterisco em *PUBLIC_MATCHERS ou *PUBLIC_POST_MATCHERS transforma o array em várias Strings, separadas por vígula
        // Ex.: *PUBLIC_MATCHERS -> ["rota1", "rota2"] -> "rota1", "rota2"
        // Se quiser criar regra para SOMENTE quem tem role CUSTOMER acessar determinadas rotas:
        //   .antMatchers(*CUSTOMER_MATCHERS).hasAuthority(Role.CUSTOMER.description) // rotas acessíveis para quem tem role ADMIN
        http.authorizeRequests()
                .antMatchers(*PUBLIC_MATCHERS).permitAll()
                .antMatchers(HttpMethod.POST, *PUBLIC_POST_MATCHERS).permitAll()
                .antMatchers(*ADMIN_MATCHERS).hasAuthority(Role.ADMIN.description) // rotas acessíveis para quem tem role ADMIN
                .anyRequest().authenticated()

        // Indicar ao Spring como realizar a autenticação
        http.addFilter(AuthenticationFilter(authenticationManager(), customerRepository, jwtUtil))

        // Indicar ao Spring como realizar a autorização
        http.addFilter(AuthorizationFilter(authenticationManager(), userDetails, jwtUtil))

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
    }

    @Bean
    fun bCryptPasswordEncoder(): BCryptPasswordEncoder {
        return BCryptPasswordEncoder()
    }

}