package by.bsu.zakharankou.restservices.model.test.views;

import by.bsu.zakharankou.restservices.model.question.Question;

import java.util.Set;

public class TestForPassingView {

    private Long id;
    private String name;
    private Set<Question> questions;
    private Long passingTime;

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

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Long getPassingTime() {
        return passingTime;
    }

    public void setPassingTime(Long passingTime) {
        this.passingTime = passingTime;
    }
}
