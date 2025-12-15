package ru.KostarevaAnastasia.NauJava.dto;

import java.util.List;

public record UserAnswerDto(
        Long questionId,
        List<Long> selectedOptionIds
) {}