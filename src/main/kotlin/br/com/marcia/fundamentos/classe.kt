package br.com.marcia.fundamentos

class Pessoa(var nome: String,  var idade: Int) {
    override fun toString(): String {
        return "Classe: Pessoa. Nome: ${nome}, Idade: ${idade}"
    }
}

data class Pessoa2(var nome: String,  var idade: Int) {
}

fun main() {
    var gustavo = Pessoa("Gustavo", 24)
    println(gustavo)

    var joao = Pessoa2("João", 32)
    println(joao)
}