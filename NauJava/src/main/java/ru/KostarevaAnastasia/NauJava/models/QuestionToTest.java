package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "question_to_test")
public class QuestionToTest {
    @EmbeddedId
    private QuestionToTestId id;

    @ManyToOne
    @MapsId("questionId")
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @ManyToOne
    @MapsId("testId")
    @JoinColumn(name = "test_id", nullable = false)
    private Test test;

    @Column(name = "sorting_order")
    private Integer sortingOrder;

    @Column(name = "number_points")
    private Integer numberPoints;

    public QuestionToTest(){}

    public QuestionToTest(Question question, Test test, Integer sortingOrder, Integer numberPoints) {
        this.question = question;
        this.test = test;
        this.sortingOrder = sortingOrder;
        this.numberPoints = numberPoints;
    }

    public QuestionToTestId getId() { return id; }
    public void setId(QuestionToTestId id) { this.id = id; }

    public Question getQuestion() { return question; }
    public void setQuestion(Question question) { this.question = question; }

    public Test getTest() { return test; }
    public void setTest(Test test) { this.test = test; }

    public Integer getSortingOrder() { return sortingOrder; }
    public void setSortingOrder(Integer sortingOrder) { this.sortingOrder = sortingOrder; }

    public Integer getNumberPoints() { return numberPoints; }
    public void setNumberPoints(Integer numberPoints) { this.numberPoints = numberPoints; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionToTest that = (QuestionToTest) o;
        return Objects.equals(test, that.test) &&
                Objects.equals(question, that.question);
    }

    @Override
    public int hashCode() {
        return Objects.hash(test, question);
    }
}
