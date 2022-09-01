package br.com.marcia.mercadolivro.enums

enum class Errors(val code: String, val message: String) {

    // Boa prática: criar ranges de erros
    // Exemplo: até 99 genérica, de 100 a 199 erros relacionados a livro, 200 a 299 de customer
    ML000("ML-000", "Access Denied"),
    ML001("ML-001", "Invalid Request"),
    ML002("ML-002", "Unauthorized"),
    ML101("ML-101", "Book [%s] not exists"),
    ML102("ML-102", "Cannot update book with status [%s]"),
    ML201("ML-201", "Customer [%s] not exists")
}