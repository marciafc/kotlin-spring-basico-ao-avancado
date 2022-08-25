package br.com.marcia.mercadolivro.repository

import br.com.marcia.mercadolivro.model.BookModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface BookRepository : CrudRepository<BookModel, Int> {

}