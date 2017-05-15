package by.bsu.zakharankou.restservices.model.test;

import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.model.user.User;

import javax.persistence.*;
import java.util.Date;
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

    private Date creationDate = new Date();

    private Date deadline;

    @ManyToOne
    @JoinColumn(name = "author")
    private User author;

    private Boolean active;

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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
