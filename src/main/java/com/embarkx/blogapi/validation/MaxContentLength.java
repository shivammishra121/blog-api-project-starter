package com.embarkx.blogapi.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Checks that a String is no longer than the {@code blog.post.max-content-length} property.
 */
@Target({ElementType.FIELD, ElementType.PARAMETER, ElementType.RECORD_COMPONENT})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = MaxContentLengthValidator.class)
public @interface MaxContentLength {

    String message() default "Content is too long";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
