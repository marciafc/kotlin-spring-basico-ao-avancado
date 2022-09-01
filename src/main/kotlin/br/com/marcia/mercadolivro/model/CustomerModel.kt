package br.com.marcia.mercadolivro.model

import br.com.marcia.mercadolivro.enums.CustomerStatus
import br.com.marcia.mercadolivro.enums.Role
import javax.persistence.*

@Entity(name = "customer")
data class CustomerModel (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int? = null,

        @Column
        var name: String,

        @Column
        var email: String,

        @Column
        @Enumerated(EnumType.STRING)
        var status: CustomerStatus,

        @Column
        val password: String,

        // @ElementCollection ->  configurar o set de profiles
        // @CollectionTable -> indicando ao JPA para olhar o atributo em outra tabela
        @Column(name = "role")
        @Enumerated(EnumType.STRING)
        @ElementCollection(targetClass = Role::class, fetch = FetchType.EAGER)
        @CollectionTable(name = "customer_roles", joinColumns = [JoinColumn(name = "customer_id")])
        var roles: Set<Role> = setOf()
)