package ru.KostarevaAnastasia.NauJava.dto;

import ru.KostarevaAnastasia.NauJava.models.QuestionType;

public record QuestionInSessionDto(
        Long id,
        String text,
        QuestionType type,
        java.util.List<OptionInSessionDto> options
) {}
