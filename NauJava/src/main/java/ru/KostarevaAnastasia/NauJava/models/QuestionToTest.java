package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "questionToTest")
@IdClass(QuestionToTestId.class)
public class QuestionToTest {
    @Id
    @Column(name = "question_id")
    private Long questionId;

    @Id
    @Column(name = "test_id")
    private Long testId;

    @Column(name = "sorting_order")
    private Integer sortingOrder;

    @Column(name = "number_points")
    private Integer numberPoints;

    public QuestionToTest(){}

    public QuestionToTest(Long idTest, Long idQuestion, Integer sorting, Integer points)
    {
        this.questionId = idQuestion;
        this.testId = idTest;
        this.numberPoints = points;
        this.sortingOrder = sorting;
    }

    public Long getQuestionId()
    {
        return questionId;
    }

    public void setQuestionId(Long questionID)
    {
        this.questionId = questionID;
    }

    public Long getTestId()
    {
        return testId;
    }

    public void setTestId(Long testId)
    {
        this.testId = testId;
    }

    public Integer getSortingOrder()
    {
        return sortingOrder;
    }

    public void setSortingOrder(Integer sortingOrder)
    {
        this.sortingOrder = sortingOrder;
    }

    public Integer getNumberPoints()
    {
        return numberPoints;
    }

    public void setNumberPoints(Integer numberPoints)
    {
        this.numberPoints = numberPoints;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionToTest that = (QuestionToTest) o;
        return Objects.equals(testId, that.testId) &&
                Objects.equals(questionId, that.questionId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(testId, questionId);
    }
}
