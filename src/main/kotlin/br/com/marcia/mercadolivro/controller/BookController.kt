package br.com.marcia.mercadolivro.controller

import br.com.marcia.mercadolivro.controller.request.PostBookRequest
import br.com.marcia.mercadolivro.controller.request.PutBookRequest
import br.com.marcia.mercadolivro.controller.response.BookResponse
import br.com.marcia.mercadolivro.controller.response.PageResponse
import br.com.marcia.mercadolivro.extension.toBookModel
import br.com.marcia.mercadolivro.extension.toPageResponse
import br.com.marcia.mercadolivro.extension.toResponse
import br.com.marcia.mercadolivro.service.BookService
import br.com.marcia.mercadolivro.service.CustomerService
import org.springframework.data.domain.Pageable
import org.springframework.data.web.PageableDefault
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("books")
class BookController(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody @Valid request: PostBookRequest) {
        val customer = customerService.findById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

    @GetMapping
    fun findAll(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse> {

        // .map { it.toResponse()  -> itera em todos os itens e converte em BookResponse,
        // usando o ConverterExtensionFunction.kt
        return bookService.findAll(pageable).map { it.toResponse() }.toPageResponse()
    }

    // Quando tem só uma linha,
    // não precisa usar return, usa só símbolo de igual
    @GetMapping("/active")
    fun findActives(@PageableDefault(page = 0, size = 10) pageable: Pageable): PageResponse<BookResponse> =
            bookService.findActives(pageable).map { it.toResponse() }.toPageResponse()

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Int): BookResponse {
        return bookService.findById(id).toResponse()
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