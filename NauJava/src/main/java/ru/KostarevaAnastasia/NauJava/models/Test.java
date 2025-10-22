package ru.KostarevaAnastasia.NauJava.models;

import jakarta.persistence.*;

@Entity
@Table(name = "tests")
public class Test {
    @Id
    @GeneratedValue
    private Long id;
    @Column
    private String title;
    @Column
    private Long creatorID;

    public Test(Long id, String title, Long creatorID) {
        this.id = id;
        this.creatorID = creatorID;
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
    public Long getCreatorID()
    {
        return creatorID;
    }
    public void setCreatorID(Long id)
    {
        this.creatorID = id;
    }

    @Override
    public String toString() {
        return "Test{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", creator='" + creatorID +
                '}';
    }
}