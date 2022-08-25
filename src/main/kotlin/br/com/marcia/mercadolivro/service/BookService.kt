package br.com.marcia.mercadolivro.service

import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.repository.BookRepository
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

}
