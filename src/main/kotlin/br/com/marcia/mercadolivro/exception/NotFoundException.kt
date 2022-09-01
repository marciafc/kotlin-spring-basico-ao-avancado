package br.com.marcia.mercadolivro.exception

class NotFoundException(override val message: String, val errorCode: String) : Exception() {
}