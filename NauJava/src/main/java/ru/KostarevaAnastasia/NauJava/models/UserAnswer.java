package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

@Entity
@Table(name = "userAnswer")
@IdClass(UserAnswerId.class)
public class UserAnswer {
    @Id
    @Column(name = "user_ID")
    private Long userID;

    @Id
    @Column(name = "option_ID")
    private Long optionID;

    @Id
    @Column(name = "test_ID")
    private Long testID;

    @Id
    @Column(name = "question_ID")
    private Long questionID;

    public UserAnswer(){}

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }

    public Long getOptionID() {
        return optionID;
    }

    public void setOptionID(Long optionID) {
        this.optionID = optionID;
    }

    public Long getTestID() {
        return testID;
    }

    public void setTestID(Long testID) {
        this.testID = testID;
    }

    public Long getQuestionID() {
        return questionID;
    }

    public void setQuestionID(Long questionID) {
        this.questionID = questionID;
    }

    @Override
    public String toString() {
        return "UserAnswer{" +
                "userID=" + userID +
                ", optionID=" + optionID +
                ", testID=" + testID +
                ", questionID=" + questionID +
                '}';
    }
}
