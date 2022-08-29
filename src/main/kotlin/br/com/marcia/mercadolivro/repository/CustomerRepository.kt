package br.com.marcia.mercadolivro.repository

import br.com.marcia.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CustomerRepository : CrudRepository<CustomerModel, Int> {

    fun findByNameContaining(name: String): List<CustomerModel>
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): CustomerModel? // CustomerModel? -> pode ser null, caso não encontre customer

}