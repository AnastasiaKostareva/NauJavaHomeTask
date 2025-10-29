package ru.KostarevaAnastasia.NauJava.models;

import java.io.Serializable;
import java.util.Objects;

public class QuestionToTestId implements Serializable {
    private Long questionId;
    private Long testId;

    public QuestionToTestId() {}

    public QuestionToTestId(Long questionId, Long testId) {
        this.questionId = questionId;
        this.testId = testId;
    }

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public Long getTestId() { return testId; }
    public void setTestId(Long testId) { this.testId = testId; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionToTestId that = (QuestionToTestId) o;
        return Objects.equals(testId, that.testId) &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testId, questionId);
    }
}
