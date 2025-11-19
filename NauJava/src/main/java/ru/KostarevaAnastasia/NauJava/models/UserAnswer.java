package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "user_answer")
public class UserAnswer {

    @EmbeddedId
    private UserAnswerId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @MapsId("optionId")
    @JoinColumn(name = "option_id")
    private Option option;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id")
    private Question question;

    public UserAnswer() {}

    public UserAnswerId getId() { return id; }
    public void setId(UserAnswerId id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Option getOption() { return option; }
    public void setOption(Option option) { this.option = option; }

    public Test getTest() { return test; }
    public void setTest(Test test) { this.test = test; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "user=" + user.getUsername() +
                ", option=" + option.getText() +
                ", test=" + test.getTitle() +
                ", question=" + question.getTextQuestion() +
                '}';
    }
}
