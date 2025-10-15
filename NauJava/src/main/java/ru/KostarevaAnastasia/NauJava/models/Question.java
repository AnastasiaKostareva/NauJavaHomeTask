package ru.KostarevaAnastasia.NauJava.models;

import java.util.List;

public class Question {
    private Long id;
    private String textQuestion;
    private String theme;
    private List<String> options;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public String getTextQuestion()
    {
        return textQuestion;
    }

    public void setTextQuestion(String textQuestion)
    {
        this.textQuestion = textQuestion;
    }

    public String getTheme()
    {
        return theme;
    }

    public void setTheme(String theme)
    {
        this.theme = theme;
    }

    public List<String> getOptions()
    {
        return options;
    }

    public void setOptions(List<String> options)
    {
        this.options = options;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + textQuestion + '\'' +
                ", theme='" + theme + '\'' +
                ", options=" + options +
                '}';
    }
}
