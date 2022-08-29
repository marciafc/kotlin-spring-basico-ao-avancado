package br.com.marcia.mercadolivro.exception

import br.com.marcia.mercadolivro.controller.response.ErrorResponse
import br.com.marcia.mercadolivro.controller.response.FieldErrorResponse
import br.com.marcia.mercadolivro.enums.Errors
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.security.access.AccessDeniedException

@ControllerAdvice
class ControllerAdvice {

    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                ex.message,
                ex.errorCode,
                null
        )

        return ResponseEntity(erro, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                ex.message,
                ex.errorCode,
                null
        )

        return ResponseEntity(erro, HttpStatus.BAD_REQUEST)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleMethodArgumentNotValidException(ex: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), // poderia ser BAD_REQUEST
                Errors.ML001.message,
                Errors.ML001.code,
                ex.bindingResult.fieldErrors.map { FieldErrorResponse(it.defaultMessage ?: "invalid", it.field) }
        )

        return ResponseEntity(erro, HttpStatus.UNPROCESSABLE_ENTITY) // poderia ser BAD_REQUEST
    }

    // Tratando resposta de erro quando costumer tenta acessar recurso que não é seu
    //   Ex.: GET /customers/1 mas está logado com customer_id = 16 e não é ADMIN
    //   Cuidar para importar -> import org.springframework.security.access.AccessDeniedException
    //   se não, não irá funcionar
    @ExceptionHandler(AccessDeniedException::class)
    fun handleAccessDeniedException(ex: AccessDeniedException, request: WebRequest): ResponseEntity<ErrorResponse> {
        val erro = ErrorResponse(
                HttpStatus.FORBIDDEN.value(),
                Errors.ML000.message,
                Errors.ML000.code,
                null
        )

        return ResponseEntity(erro, HttpStatus.FORBIDDEN)
    }
}