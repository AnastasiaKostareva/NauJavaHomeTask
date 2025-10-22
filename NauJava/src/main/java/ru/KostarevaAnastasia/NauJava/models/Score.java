package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

import java.sql.Timestamp;

@Entity
@Table(name = "scores")
public class Score {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private Long userID;
    @Column
    private Long testID;
    @Column
    private Integer score;
    @Column
    private Timestamp createdAt;

    public Score(){}

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public Long getTestID() {
        return testID;
    }

    public void setTestID(Long testID) {
        this.testID = testID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserID() {
        return userID;
    }

    public void setUserID(Long userID) {
        this.userID = userID;
    }
}
