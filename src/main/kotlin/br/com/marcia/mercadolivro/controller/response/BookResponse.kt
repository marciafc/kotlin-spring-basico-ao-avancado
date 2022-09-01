package br.com.marcia.mercadolivro.controller.response

import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.model.CustomerModel
import java.math.BigDecimal

data class BookResponse(
    var id: Int? = null,

    var name: String,

    var price: BigDecimal,

    var customer: CustomerModel? = null,

    var status: BookStatus? = null
)