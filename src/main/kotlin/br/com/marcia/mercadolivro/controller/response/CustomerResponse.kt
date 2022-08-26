package br.com.marcia.mercadolivro.controller.response

import br.com.marcia.mercadolivro.enums.CustomerStatus

data class CustomerResponse(
    var id: Int? = null,

    var name: String,

    var email: String,

    var status: CustomerStatus
)