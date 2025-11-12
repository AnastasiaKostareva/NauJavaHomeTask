package ru.KostarevaAnastasia.NauJava.models;

import java.util.List;

public class ReportData {
    private long userCount;
    private long userTime;
    private List<?> questions;
    private long questionTime;
    private long totalTime;

    public long getUserCount() { return userCount; }
    public void setUserCount(long userCount) { this.userCount = userCount; }

    public long getUserTime() { return userTime; }
    public void setUserTime(long userTime) { this.userTime = userTime; }

    public List<?> getQuestions() { return questions; }
    public void setQuestions(List<?> questions) { this.questions = questions; }

    public long getQuestionTime() { return questionTime; }
    public void setQuestionTime(long questionTime) { this.questionTime = questionTime; }

    public long getTotalTime() { return totalTime; }
    public void setTotalTime(long totalTime) { this.totalTime = totalTime; }
}