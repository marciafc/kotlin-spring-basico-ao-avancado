package br.com.marcia.mercadolivro.controller

import br.com.marcia.mercadolivro.controller.request.PostBookRequest
import br.com.marcia.mercadolivro.extension.toBookModel
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
        val customer = customerService.getById(request.customerId)
        bookService.create(request.toBookModel(customer))
    }

}