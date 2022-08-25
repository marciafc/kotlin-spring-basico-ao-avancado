package br.com.marcia.mercadolivro.repository

import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.model.CustomerModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<BookModel, Int> {

    fun findByStatus(status: BookStatus): List<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>
}