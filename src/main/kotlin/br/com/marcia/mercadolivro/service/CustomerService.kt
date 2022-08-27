package br.com.marcia.mercadolivro.service

import br.com.marcia.mercadolivro.enums.CustomerStatus
import br.com.marcia.mercadolivro.exception.NotFoundException
import br.com.marcia.mercadolivro.model.CustomerModel
import br.com.marcia.mercadolivro.repository.CustomerRepository
import org.springframework.stereotype.Service

@Service
class CustomerService(
        val customerRepository: CustomerRepository,
        val bookService: BookService
) {

    // val customers = mutableListOf<CustomerModel>()

    fun getAll(name: String?): List<CustomerModel> {

        // name?. -> só fará essa pesquisa se name for diferente de null
        name?.let {
            return customerRepository.findByNameContaining(it)
        }

        // findAll() retorna um Mutable, necessário usar toList()
        return customerRepository.findAll().toList()
    }

    /*fun getAll(name: String?): List<CustomerModel> {

        // name?. -> só fará essa pesquisa se name for diferente de null
        name?.let {
            return customers.filter { it.name.contains(name, true) }
        }

        // retorna toda a lista
        return customers
    }*/

    fun create(customer: CustomerModel) {
        customerRepository.save(customer)
    }
    /*fun create(customer: CustomerModel) {

        // if no Kotlin já retorna
        val id = if(customers.isEmpty()) {
            1
        } else{
            // necessário usar toInt() para incrementar, senao como é string, vai concatenar
            //customers.last().id.toInt() + 1

            // Usar !! está dizendo que "confia" que não é null
            // Necessário incluir isso, porque agora o id é opcional (?)
            customers.last().id!!.toInt() + 1
        }//.toString()

        customer.id = id

        customers.add(CustomerModel(id, customer.name, customer.email))
    }*/

    fun findById(id: Int): CustomerModel {
        return customerRepository.findById(id).orElseThrow{ NotFoundException("Customer [${id}] not exists", "ML-0002") }
    }
    /*fun getCustomer(id: Int): CustomerModel {
        // return customers.filter { it.id == id }.first()

        // Ou retorna um Optional de CustomerModel
        // Ou orElseThrow -> se nao encontrar o registro, lança exceção
        return customerRepository.findById(id).orElseThrow()
    }*/

    fun update(customer: CustomerModel) {
        /*customers.filter { it.id == customer.id }.first().let {
            it.name = customer.name
            it.email = customer.email
        }*/

        // !! -> "confia" que não é null
        if(!customerRepository.existsById(customer.id!!)){
            throw Exception()
        }
        customerRepository.save(customer)
    }

    fun delete(id: Int) {
        // customers.removeIf { it.id == id }

        /*if(!customerRepository.existsById(id)){
            throw Exception()
        }*/

        val customer = findById(id)
        bookService.deleteByCustomer(customer)

        customer.status = CustomerStatus.INATIVO

        customerRepository.save(customer)
    }

}