package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String textQuestion;
    @Column
    private String theme;
    @Enumerated(EnumType.STRING)
    @Column
    private QuestionType questionType;

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Question() {}

    public Question(Long id, String textQuestion, String theme, QuestionType  questionType) {
        this.id = id;
        this.questionType = questionType;
        this.textQuestion = textQuestion;
        this.theme = theme;
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

    public QuestionType getQuestionType()
    {
        return questionType;
    }

    public void setQuestionType(QuestionType questionType)
    {
        this.questionType = questionType;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + textQuestion + '\'' +
                ", theme='" + theme + '\'' +
                ", questionType=" + questionType +
                '}';
    }
}


