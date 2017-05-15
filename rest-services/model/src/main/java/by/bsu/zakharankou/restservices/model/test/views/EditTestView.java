package by.bsu.zakharankou.restservices.model.test.views;

import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.test.Test;

import java.util.Set;

public class EditTestView {

    private Long testId;
    private Long categoryId;
    private Long topicId;
    private Long questionsNumber;
    private Boolean privateTest;
    private Boolean showCorrectAnswers;
    private Set<Question> questions;

    public EditTestView(Test test) {
        this.testId = test.getId();
        this.categoryId = test.getCategory().getId();
        this.topicId = test.getTopic().getId();
        this.questionsNumber = test.getNumberQuestions();
        this.privateTest = test.isPrivateTest();
        this.showCorrectAnswers = test.isShowCorrectAnswers();
        this.questions = test.getQuestions();
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getTopicId() {
        return topicId;
    }

    public void setTopicId(Long topicId) {
        this.topicId = topicId;
    }

    public Long getQuestionsNumber() {
        return questionsNumber;
    }

    public void setQuestionsNumber(Long questionsNumber) {
        this.questionsNumber = questionsNumber;
    }

    public Boolean getPrivateTest() {
        return privateTest;
    }

    public void setPrivateTest(Boolean privateTest) {
        this.privateTest = privateTest;
    }

    public Boolean getShowCorrectAnswers() {
        return showCorrectAnswers;
    }

    public void setShowCorrectAnswers(Boolean showCorrectAnswers) {
        this.showCorrectAnswers = showCorrectAnswers;
    }

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }
}
