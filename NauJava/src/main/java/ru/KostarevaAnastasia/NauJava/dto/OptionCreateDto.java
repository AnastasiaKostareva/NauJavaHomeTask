package ru.KostarevaAnastasia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record OptionCreateDto(
        @NotBlank String text,
        boolean correct,
        @NotNull Long questionId
) {}