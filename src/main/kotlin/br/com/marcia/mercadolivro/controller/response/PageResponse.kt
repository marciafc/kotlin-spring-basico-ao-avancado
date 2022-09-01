package br.com.marcia.mercadolivro.controller.response

/**
 * Customizando os campos que serão retornandos do org.springframework.data.domain.Page
 */
class PageResponse<T> (
  var items: List<T>,
  var currentPage: Int,
  var totalItems: Long,
  var totalPages: Int
)