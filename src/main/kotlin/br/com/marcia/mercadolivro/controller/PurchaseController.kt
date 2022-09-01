package br.com.marcia.mercadolivro.controller

import br.com.marcia.mercadolivro.controller.mapper.PurchaseMapper
import br.com.marcia.mercadolivro.controller.request.PostPurchaseRequest
import br.com.marcia.mercadolivro.service.PurchaseService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("purchases")
class PurchaseController(
    private val purchaseService: PurchaseService,
    private val purchaseMapper: PurchaseMapper
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun purchase(@RequestBody request: PostPurchaseRequest){
        purchaseService.create(purchaseMapper.toModel(request))
    }

}