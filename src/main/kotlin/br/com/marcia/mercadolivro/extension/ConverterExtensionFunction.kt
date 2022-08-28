package br.com.marcia.mercadolivro.extension

import br.com.marcia.mercadolivro.controller.request.PostBookRequest
import br.com.marcia.mercadolivro.controller.request.PostCustomerRequest
import br.com.marcia.mercadolivro.controller.request.PutBookRequest
import br.com.marcia.mercadolivro.controller.request.PutCustomerRequest
import br.com.marcia.mercadolivro.controller.response.BookResponse
import br.com.marcia.mercadolivro.controller.response.CustomerResponse
import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.enums.CustomerStatus
import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(
            name = this.name,
            email = this.email,
            status = CustomerStatus.ATIVO,
            password = this.password
    )
}

fun PutCustomerRequest.toCustomerModel(previousValue: CustomerModel): CustomerModel {
    return CustomerModel(
            id = previousValue.id,
            name = this.name,
            email = this.email,
            status = previousValue.status,
            password = previousValue.password
    )
}

fun PostBookRequest.toBookModel(customer: CustomerModel): BookModel {
    return BookModel(
            name = this.name,
            price = this.price,
            status = BookStatus.ATIVO,
            customer = customer
    )
}

fun PutBookRequest.toBookModel(previousValue: BookModel): BookModel {
    return BookModel(
            id = previousValue.id,

            // ?: -> elvis operator, operador ternário
            name = this.name ?: previousValue.name,
            price = this.price ?: previousValue.price,

            status = previousValue.status,
            customer = previousValue.customer
    )
}

fun CustomerModel.toResponse(): CustomerResponse {
    return CustomerResponse(
            id = this.id,
            name = this.name,
            email = this.email,
            status = this.status
    )
}

fun BookModel.toResponse(): BookResponse {
    return BookResponse(
            id = this.id,
            name = this.name,
            price = this.price,
            customer = this.customer,
            status = this.status
    )
}