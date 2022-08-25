package br.com.marcia.mercadolivro.extension

import br.com.marcia.mercadolivro.controller.request.PostBookRequest
import br.com.marcia.mercadolivro.controller.request.PostCustomerRequest
import br.com.marcia.mercadolivro.controller.request.PutBookRequest
import br.com.marcia.mercadolivro.controller.request.PutCustomerRequest
import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.model.BookModel
import br.com.marcia.mercadolivro.model.CustomerModel

fun PostCustomerRequest.toCustomerModel(): CustomerModel {
    return CustomerModel(name = this.name, email = this.email)
}

fun PutCustomerRequest.toCustomerModel(id: Int): CustomerModel {
    return CustomerModel(id = id, name = this.name, email = this.email)
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
