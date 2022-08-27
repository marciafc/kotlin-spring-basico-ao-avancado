package br.com.marcia.mercadolivro.validation

import br.com.marcia.mercadolivro.service.CustomerService
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext

// ConstraintValidator<EmailAvailable, String>
//   - EmailAvailable é annotation
//   - String tipo do atributo validado, no caso, email String
class EmailAvailableValidator(var customerService: CustomerService): ConstraintValidator<EmailAvailable, String> {

    override fun isValid(value: String?, context: ConstraintValidatorContext?): Boolean {
        if(value.isNullOrEmpty()) {
            return false
        }
        return customerService.emailAvailable(value)
    }

}
