package br.com.marcia.mercadolivro.repository

import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.model.CustomerModel
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : JpaRepository<BookModel, Int> {

    fun findByStatus(status: BookStatus, pageable: Pageable): Page<BookModel>
    fun findByCustomer(customer: CustomerModel): List<BookModel>

    // Forma 1 para paginar:
    // - BookRepository herdar de CrudRepository,
    //   declarar método recebendo Pageable e retornando Page
    // BookRepository : CrudRepository<BookModel, Int>
    // fun findAll(pageable: Pageable): Page<BookModel>

    // Forma 2 para paginar:
    // - BookRepository : JpaRepository<BookModel, Int>
    //   Se for método já existente do jpa como findAll, não precisa declarar
    //   Só declara métodos customizados, como o findByStatus (vide acima)
}