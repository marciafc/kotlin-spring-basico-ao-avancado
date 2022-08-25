package br.com.marcia.mercadolivro.controller

import br.com.marcia.mercadolivro.controller.request.PostBookRequest
import br.com.marcia.mercadolivro.controller.request.PutBookRequest
import br.com.marcia.mercadolivro.extension.toBookModel
import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.service.BookService
import br.com.marcia.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("books")
class BookController(
    val bookService: BookService,
    val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody request: PostBookRequest) {
        val customer = customerService.findById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

    @GetMapping
    fun findAll(): List<BookModel> {
        return bookService.findAll()
    }

    // Quando tem só uma linha,
    // não precisa usar return, usa só símbolo de igual
    @GetMapping("/active")
    fun findActives(): List<BookModel> =
            bookService.findActives()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookModel {
        return bookService.findById(id)
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        bookService.delete(id)
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody book: PutBookRequest) {
        val bookSaved = bookService.findById(id)
        bookService.update(book.toBookModel(bookSaved))
    }
}