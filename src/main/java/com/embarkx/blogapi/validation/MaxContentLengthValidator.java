package com.embarkx.blogapi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Value;

public class MaxContentLengthValidator implements ConstraintValidator<MaxContentLength, String> {

    @Value("${blog.post.max-content-length}")
    private int maxContentLength;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        // null/blank is handled by @NotBlank
        if (value == null || value.length() <= maxContentLength) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                        "Content must be at most " + maxContentLength + " characters")
                .addConstraintViolation();
        return false;
    }
}
