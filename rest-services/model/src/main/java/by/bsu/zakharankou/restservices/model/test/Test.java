package by.bsu.zakharankou.restservices.model.test;

import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.topic.Topic;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Test {

    public static final String FIELD_NAME = "name";

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "testId")
    private Set<Question> questions;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @ManyToOne
    @JoinColumn(name = "topic_id")
    private Topic topic;

    private Boolean privateTest;

    private Boolean showCorrectAnswers;

    private Long passingTime;

    private Long numberQuestions;

    public Test() {
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

    public Set<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(Set<Question> questions) {
        this.questions = questions;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Boolean isPrivateTest() {
        return privateTest;
    }

    public void setPrivateTest(Boolean privateTest) {
        this.privateTest = privateTest;
    }

    public Boolean isShowCorrectAnswers() {
        return showCorrectAnswers;
    }

    public void setShowCorrectAnswers(Boolean showCorrectAnswers) {
        this.showCorrectAnswers = showCorrectAnswers;
    }

    public Long getPassingTime() {
        return passingTime;
    }

    public void setPassingTime(Long passingTime) {
        this.passingTime = passingTime;
    }

    public Long getNumberQuestions() {
        return numberQuestions;
    }

    public void setNumberQuestions(Long numberQuestions) {
        this.numberQuestions = numberQuestions;
    }
}
