package br.com.marcia.mercadolivro.exception

class AuthenticationException(override val message: String, val errorCode: String) : Exception()