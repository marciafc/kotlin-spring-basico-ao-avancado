package br.com.marcia.mercadolivro.service

import br.com.marcia.mercadolivro.controller.request.PostCustomerRequest
import br.com.marcia.mercadolivro.controller.request.PutCustomerRequest
import com.mercadolivro.model.CustomerModel
import org.springframework.stereotype.Service

@Service
class CustomerService {

    val customers = mutableListOf<CustomerModel>()

    fun getAll(name: String?): List<CustomerModel> {

        // name?. -> só fará essa pesquisa se name for diferente de null
        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }
        return customers
    }

    fun create(customer: CustomerModel) {

        // if no Kotlin já retorna
        val id = if(customers.isEmpty()) {
            1
        } else{
            // necessário usar toInt() para incrementar, senao como é string, vai concatenar
            //customers.last().id.toInt() + 1

            // Usar !! está dizendo que "confia" que não é null
            // Necessário incluir isso, porque agora o id é opcional (?)
            customers.last().id!!.toInt() + 1
        }.toString()

        customer.id = id

        customers.add(CustomerModel(id, customer.name, customer.email))
    }

    fun getCustomer(id: String): CustomerModel {
        return customers.filter { it.id == id }.first()
    }

    fun update(customer: CustomerModel) {
        customers.filter { it.id == customer.id }.first().let {
            it.name = customer.name
            it.email = customer.email
        }
    }

    fun delete(id: String) {
        customers.removeIf { it.id == id }
    }

}