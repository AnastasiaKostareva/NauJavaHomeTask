package ru.KostarevaAnastasia.NauJava.businessLogic;

import ru.KostarevaAnastasia.NauJava.models.Question;

import java.util.List;

public interface QuestionService
{
    void createQuestion(Long id, String text, String theme, List<String> options);
    Question findById(Long id);
    void deleteById(Long id);
    void updateText(Long id, String newText);
    void updateTheme(Long id, String newTheme);
    void addOption(Long id, String newOption);
    void deleteOption(Long id, String option);
}

