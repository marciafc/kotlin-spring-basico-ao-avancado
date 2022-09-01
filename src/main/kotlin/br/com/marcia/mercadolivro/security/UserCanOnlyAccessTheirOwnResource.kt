package br.com.marcia.mercadolivro.security

import org.springframework.security.access.prepost.PreAuthorize

@Target(AnnotationTarget.FUNCTION) // Só será utilizado em uma função/método
@Retention(AnnotationRetention.RUNTIME)
@PreAuthorize("hasRole('ROLE_ADMIN') || #id == authentication.principal.id")
annotation class UserCanOnlyAccessTheirOwnResource
