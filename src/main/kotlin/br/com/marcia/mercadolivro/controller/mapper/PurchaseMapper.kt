package br.com.marcia.mercadolivro.controller.mapper

import br.com.marcia.mercadolivro.controller.request.PostPurchaseRequest
import br.com.marcia.mercadolivro.model.PurchaseModel
import br.com.marcia.mercadolivro.service.BookService
import br.com.marcia.mercadolivro.service.CustomerService
import org.springframework.stereotype.Component

@Component
class PurchaseMapper(
    private val bookService: BookService,
    private val customerService: CustomerService
) {

    fun toModel(request: PostPurchaseRequest): PurchaseModel {

        val customer = customerService.findById(request.customerId)
        val books = bookService.findAllByIds(request.bookIds)

        return PurchaseModel(
            customer = customer,
            books = books.toMutableList(),
            price = books.sumOf { it.price } // sumOf -> acumula os valores da lista -> total de price
        )
    }

}