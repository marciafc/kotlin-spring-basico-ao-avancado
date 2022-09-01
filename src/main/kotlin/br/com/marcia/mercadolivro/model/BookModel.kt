package br.com.marcia.mercadolivro.model

import br.com.marcia.mercadolivro.enums.BookStatus
import br.com.marcia.mercadolivro.enums.Errors
import br.com.marcia.mercadolivro.exception.BadRequestException
import java.math.BigDecimal
import javax.persistence.*

@Entity(name = "book")
data class BookModel(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column
    var name: String,

    @Column
    var price: BigDecimal,

    @ManyToOne
    @JoinColumn(name = "customer_id")
    var customer: CustomerModel? = null

) {


    // Como sobrescrever um método set?
    // Ao subscrever o setStatus, validamos ANTES se essa alteração é permitida
    @Column
    @Enumerated(EnumType.STRING)
    var status: BookStatus? = null

        // value -> novo valor
        // field -> valor atual do atributo
        set(value) {
            if(field == BookStatus.CANCELADO || field == BookStatus.DELETADO) {
                throw BadRequestException(Errors.ML102.message.format(field), Errors.ML102.code)
            }

            field = value
        }

    // Necessário criar um construtor contendo o 'status' -> usar 'constructor'
    // Isso porque o 'status' foi removido do construtor de cima
    // Onde tem this(id, name, price, customer) -> está invocando o construtor de cima
    constructor(id: Int? = null,
                name: String,
                price: BigDecimal,
                customer: CustomerModel? = null,
                status: BookStatus?): this(id, name, price, customer) {

        // seta 'status' -> só existe nesse construtor, não tem no construtor de cima
        this.status = status
    }

}