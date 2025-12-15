package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String title;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;
    @OneToMany(mappedBy = "test", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<QuestionToTest> questionToTests = new ArrayList<>();

    public Test(Long id, String title, User creator) {
        this.id = id;
        this.creator = creator;
        this.title = title;
    }
    public Test(){}

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public User getCreator()
    {
        return creator;
    }
    public void setCreator(User user)
    {
        this.creator = user;
    }
    public List<QuestionToTest> getQuestionToTests() {
        return questionToTests;
    }
    public void setQuestionToTests(List<QuestionToTest> questionToTests) {
        this.questionToTests = questionToTests;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creator='" + creator.getUsername() +
                '}';
    }
}