package br.com.marcia.mercadolivro.service

import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.enums.Errors
import br.com.marcia.mercadolivro.exception.NotFoundException
import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.model.CustomerModel
import br.com.marcia.mercadolivro.repository.BookRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class BookService(
    val bookRepository: BookRepository
) {

    fun create(book: BookModel) {
        bookRepository.save(book)
    }

    fun findAll(pageable: Pageable): Page<BookModel> {
        return bookRepository.findAll(pageable)
    }

    fun findActives(pageable: Pageable): Page<BookModel> {
        return bookRepository.findByStatus(BookStatus.ATIVO, pageable)
    }

    fun findById(id: Int): BookModel {
        // orElseThrow -> caso não encontre o id, lança exceção
        // ML -> MercadoLivro: erro específico do sistema para facilitar o tracking
        return bookRepository.findById(id).orElseThrow{ NotFoundException(Errors.ML101.message.format(id), Errors.ML101.code) }
    }

    fun delete(id: Int) {
        val book = findById(id)

        book.status = BookStatus.CANCELADO

        update(book)
    }

    fun update(book: BookModel) {
        bookRepository.save(book)
    }

    fun deleteByCustomer(customer: CustomerModel) {
        val books = bookRepository.findByCustomer(customer)
        for(book in books) {
            book.status = BookStatus.DELETADO
        }
        bookRepository.saveAll(books)
    }

}
