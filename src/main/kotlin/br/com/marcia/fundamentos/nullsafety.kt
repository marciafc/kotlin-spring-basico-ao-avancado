package fundamentos

import java.lang.Exception

fun main() {

    // A lista pode conter valores nulos
    var lista : List<Int?> = listOf(1, 2, null, 3)

    // A lista pode ser nula
    var listaNullable : List<Int>? = null

    // A lista pode ser nula e conter valores nulos
    var listaNullable2 : List<Int?>? = null

    var nome : String? = null
    println(nome?.length) //imprime null

    // ?: Elvis operator
    var tamanho: Int = nome?.length ?: 0
    //var tamanho: Int = nome?.length ?: throw Exception("Deu ruim")
    println(tamanho) //imprime 0

    var nomeMarcia = "Marcia"
    var tamanhoMarcia: Int = nomeMarcia?.length ?: 0
    println(tamanhoMarcia) //imprime 6

    var nome2 : String? = "Gustavo"
    if(nome2 != null){
        println(nome2.length) //imprime 7
    }

    // !! garante que não é nulo
    val toShort : Short = nome2!!.length.toShort()
    println(toShort) //imprime 7

}
