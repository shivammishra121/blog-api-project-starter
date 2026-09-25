package com.embarkx.blogapi.dto;

import com.embarkx.blogapi.validation.MaxContentLength;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePostRequest(

        @NotBlank(message = "Title must not be empty")
        @Size(max = CreatePostRequest.MAX_TITLE_LENGTH,
                message = "Title must be at most " + CreatePostRequest.MAX_TITLE_LENGTH + " characters")
        String title,

        @NotBlank(message = "Content must not be empty")
        @MaxContentLength
        String content) {

    public static final int MAX_TITLE_LENGTH = 100;
}
