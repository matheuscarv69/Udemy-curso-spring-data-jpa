package io.github.matheus.validation;

import io.github.matheus.validation.constraintvalidation.NotEmptyListValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME) //para ser verificada em tempo de execucao
@Target(ElementType.FIELD) // para dizer que ela vai ser colocada em cima de um campo
@Constraint(validatedBy = NotEmptyListValidator.class)
public @interface NotEmptyList {

    String message() default "A lista não pode ser vazia.";

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
