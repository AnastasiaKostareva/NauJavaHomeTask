package ru.KostarevaAnastasia.NauJava.models;

import java.io.Serializable;
import java.util.Objects;

public class UserAnswerId implements Serializable {
    private Long userID;
    private Long optionID;
    private Long testID;
    private Long questionID;

    public UserAnswerId() {}

    public UserAnswerId(Long userID, Long optionID, Long testID, Long questionID) {
        this.userID = userID;
        this.optionID = optionID;
        this.testID = testID;
        this.questionID = questionID;
    }

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserAnswerId that = (UserAnswerId) o;
        return Objects.equals(userID, that.userID) &&
                Objects.equals(optionID, that.optionID) &&
                Objects.equals(testID, that.testID) &&
                Objects.equals(questionID, that.questionID);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, optionID, testID, questionID);
    }
}
