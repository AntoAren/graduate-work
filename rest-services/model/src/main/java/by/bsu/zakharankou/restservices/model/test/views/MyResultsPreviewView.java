package by.bsu.zakharankou.restservices.model.test.views;

import by.bsu.zakharankou.restservices.model.JsonDateSerializer;
import by.bsu.zakharankou.restservices.model.test.Test;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class MyResultsPreviewView {

    private Long id;
    private String name;
    private String category;
    private String topic;
    private Date completionDate;
    private Long score;
    private String createdBy;
    private Long averageScore;

    public MyResultsPreviewView(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.category = test.getCategory().getName();
        this.topic = test.getTopic().getName();
        this.createdBy = test.getAuthor().getDisplayName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public Long getAverageScore() {
        return averageScore;
    }

    public void setAverageScore(Long averageScore) {
        this.averageScore = averageScore;
    }
}
