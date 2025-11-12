package ru.KostarevaAnastasia.NauJava.businessLogic;

import ru.KostarevaAnastasia.NauJava.models.Question;

import java.util.List;

public interface QuestionServiceOld
{
    void createQuestion(Long id, String text, String theme, List<String> options);
    Question findById(Long id);
    void deleteById(Long id);
    void updateText(Long id, String newText);
    void updateTheme(Long id, String newTheme);
    }

