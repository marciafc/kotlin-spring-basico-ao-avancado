package br.com.marcia.mercadolivro.repository

import br.com.marcia.mercadolivro.model.PurchaseModel
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface PurchaseRepository : CrudRepository<PurchaseModel, Int> {

}
