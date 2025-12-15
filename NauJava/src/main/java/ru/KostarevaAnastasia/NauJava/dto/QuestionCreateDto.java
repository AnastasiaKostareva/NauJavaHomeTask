package ru.KostarevaAnastasia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;

public record QuestionCreateDto(
        @NotBlank String textQuestion,
        @NotBlank String theme,
        @NotNull QuestionType questionType
) {}
