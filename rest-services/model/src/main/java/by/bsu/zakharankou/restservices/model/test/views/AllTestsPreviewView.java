package by.bsu.zakharankou.restservices.model.test.views;

import by.bsu.zakharankou.restservices.model.JsonDateSerializer;
import by.bsu.zakharankou.restservices.model.test.Test;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class AllTestsPreviewView {

    private Long id;
    private String name;
    private String category;
    private String topic;
    private Date creationDate;
    private String createdBy;
    private Long averageScore;
    private Date deadline;
    private Boolean added;

    public AllTestsPreviewView(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.category = test.getCategory().getName();
        this.topic = test.getTopic().getName();
        this.creationDate = test.getCreationDate();
        this.createdBy = test.getAuthor().getDisplayName();
        this.deadline = test.getDeadline();
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
    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean isAdded() {
        return added;
    }

    public void setAdded(Boolean added) {
        this.added = added;
    }
}
