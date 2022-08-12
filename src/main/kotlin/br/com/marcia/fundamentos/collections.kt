package br.com.marcia.fundamentos

fun main() {

    //lista()
    //set()
    map()
}

fun lista() {

    println("*** Lista imutável ***")
    var listaImutavel = listOf(0, 1, 2, 3, 4)
    var pares = listaImutavel.filter { it % 2 == 0 }
    println("Pares: ${pares}")
    println("Primeiro valor: ${listaImutavel.filter { it % 2 == 0 }.first()}")
    println("forEach:")
    listaImutavel.forEach {
        println(it)
    }
    for(numero in listaImutavel) {
        println("-> ${numero}")
    }
    println("Primeiro: ${listaImutavel.first()}")
    println("Último: ${listaImutavel.last()}")


    println("*** Lista mutável ***")
    var lista = mutableListOf(1, 2, 3, 2)
    println("Lista: $lista")

    println(lista)

    lista.sort()

    println(lista)

    //embaralhar
    lista.shuffle()

    println(lista)

    var listaNomes = mutableListOf("Gustavo", "Daniel")

    println(listaNomes)

    listaNomes.sort()

    println(listaNomes)
    lista.add(8)

    lista.removeAt(0)
    lista.remove(3)

    //lista[3] = 20

    for (numero in lista){
        println(numero)
    }

    println(lista[0])
    println(lista.get(0))
    println(lista.size)
    println(lista.indexOf(6))

}

fun set() {

    // set imutável
    var numeros = setOf(1, 2, 3, 3)
    println(numeros)

    // set mutável
    var setNumeros = mutableSetOf(1, 2, 3, 2)
    println(setNumeros.contains(2))
}

fun map() {

    // Map imutável
    var mapNomeIdadeImutavel = mapOf("Marcia" to 24, "João" to 20)
    println(mapNomeIdadeImutavel)

    // Map mutável
    var mapNomeIdade = mutableMapOf("Gustavo" to 24, "Daniel" to 20)
    println(mapNomeIdade)

    // pode usar put, assim mapNomeIdade.put("Bruno", 30)
    mapNomeIdade["Bruno"] = 30

    println(mapNomeIdade)

    mapNomeIdade.remove("Bruno")

    println(mapNomeIdade)

    // putIfAbsent só faz o put caso não exista a chave
    // não subscreve o valor
    mapNomeIdade.putIfAbsent("Daniel", 100)
    mapNomeIdade.putIfAbsent("Daniel2", 99)

    println(mapNomeIdade)

}