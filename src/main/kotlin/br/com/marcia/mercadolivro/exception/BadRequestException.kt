package br.com.marcia.mercadolivro.exception

class BadRequestException(override val message: String, val errorCode: String) : Exception() {
}