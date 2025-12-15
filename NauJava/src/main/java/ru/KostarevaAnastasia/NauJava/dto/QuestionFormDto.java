package ru.KostarevaAnastasia.NauJava.dto;

import ru.KostarevaAnastasia.NauJava.models.QuestionType;

import java.util.List;

public record QuestionFormDto(
        String textQuestion,
        String theme,
        QuestionType questionType,
        List<String> optionText,
        List<Boolean> optionCorrect
) {}
