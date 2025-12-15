package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String textQuestion;
    @Column
    private String theme;
    @Enumerated(EnumType.STRING)
    @Column
    private QuestionType questionType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id", nullable = false)
    private User author;
    @OneToMany(mappedBy = "question", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Option> options = new ArrayList<>();

    public List<Option> getOptions() {
        return options;
    }

    public void setOptions(List<Option> options) {
        this.options = options;
    }

    public Long getId()
    {
        return id;
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Question() {}

    public Question(Long id, String textQuestion, String theme, QuestionType  questionType, User author) {
        this.id = id;
        this.questionType = questionType;
        this.textQuestion = textQuestion;
        this.theme = theme;
        this.author = author;
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

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", text='" + textQuestion + '\'' +
                ", theme='" + theme + '\'' +
                ", questionType=" + questionType +
                ", author=" + author +
                '}';
    }
}


