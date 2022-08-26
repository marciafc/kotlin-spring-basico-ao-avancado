package br.com.marcia.mercadolivro.controller

import br.com.marcia.mercadolivro.extension.toCustomerModel
import br.com.marcia.mercadolivro.controller.request.PostCustomerRequest
import br.com.marcia.mercadolivro.controller.request.PutCustomerRequest
import br.com.marcia.mercadolivro.controller.response.CustomerResponse
import br.com.marcia.mercadolivro.extension.toResponse
import br.com.marcia.mercadolivro.service.CustomerService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("customers")
class CustomerController (
        // injeção de dependência
        val customerService : CustomerService
) {

    // RequestParam OU QueryParameter
    // @RequestParam name: String?  -> usar o ponto de interrogação no parâmetro (fica opcional)
    @GetMapping
    fun getAll(@RequestParam name: String?): List<CustomerResponse> {

        // .map { it.toResponse()  -> itera em todos os itens e converte em CustomerResponse,
        // usando o ConverterExtensionFunction.kt
        return customerService.getAll(name).map { it.toResponse() }
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody customer: PostCustomerRequest) {
        customerService.create(customer.toCustomerModel())
    }

    @GetMapping("/{id}")
    fun getCustomer(@PathVariable id: Int): CustomerResponse {
        return customerService.findById(id).toResponse()
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun update(@PathVariable id: Int, @RequestBody customer: PutCustomerRequest) {
        val customerSaved = customerService.findById(id)
        customerService.update(customer.toCustomerModel(customerSaved))
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Int) {
        customerService.delete(id)
    }
}

