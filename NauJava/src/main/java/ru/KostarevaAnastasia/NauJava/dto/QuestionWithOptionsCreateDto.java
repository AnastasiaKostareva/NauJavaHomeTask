package ru.KostarevaAnastasia.NauJava.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import ru.KostarevaAnastasia.NauJava.models.QuestionType;

import java.util.List;

public record QuestionWithOptionsCreateDto(
        @NotBlank String textQuestion,
        @NotBlank String theme,
        @NotNull QuestionType questionType,
        @NotEmpty List<OptionCreateDto> options
) {}
