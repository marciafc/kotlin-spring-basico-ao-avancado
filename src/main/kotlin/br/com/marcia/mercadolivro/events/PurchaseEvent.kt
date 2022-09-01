package br.com.marcia.mercadolivro.events

import br.com.marcia.mercadolivro.model.PurchaseModel
import org.springframework.context.ApplicationEvent

class PurchaseEvent (

    // não precisa declarar com 'val' porque ApplicationEvent (que está herdando) já tem 'source' no construtor
    source: Any,

    val purchaseModel: PurchaseModel

): ApplicationEvent(source)