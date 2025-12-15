package ru.KostarevaAnastasia.NauJava.dto;

public record TestSessionDto(
        Long testId,
        String testName,
        java.util.List<QuestionInSessionDto> questions
) {}
