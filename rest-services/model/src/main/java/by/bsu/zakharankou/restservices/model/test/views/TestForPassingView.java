package by.bsu.zakharankou.restservices.model.test.views;

import by.bsu.zakharankou.restservices.model.JsonDateSerializer;
import by.bsu.zakharankou.restservices.model.question.Question;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class TestForPassingView {

    private Long id;
    private String name;
    private List<Question> questions;
    private Date startedAt;
    private Long testDuration;

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

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    public Long getTestDuration() {
        return testDuration;
    }

    public void setTestDuration(Long testDuration) {
        this.testDuration = testDuration;
    }

    public Date getStartedAt() {
        return startedAt;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public void setStartedAt(Date startedAt) {
        this.startedAt = startedAt;
    }

}
