package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class UserAnswerId implements Serializable {
    private Long userId;
    private Long optionId;
    private Long testId;
    private Long questionId;

    public UserAnswerId() {}

    public UserAnswerId(Long userID, Long optionID, Long testID, Long questionID) {
        this.userId = userID;
        this.optionId = optionID;
        this.testId = testID;
        this.questionId = questionID;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userID) {
        this.userId = userID;
    }

    public Long getOptionId() {
        return optionId;
    }

    public void setOptionId(Long optionID) {
        this.optionId = optionID;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testID) {
        this.testId= testID;
    }

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionID) {
        this.questionId = questionID;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswerId that = (UserAnswerId) o;
        return Objects.equals(userId, that.userId) &&
                Objects.equals(optionId, that.optionId) &&
                Objects.equals(testId, that.testId) &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, optionId, testId, questionId);
    }
}
