package by.bsu.zakharankou.restservices.model.question;

import by.bsu.zakharankou.restservices.model.answer.Answer;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Question {

    @Id
    @GeneratedValue
    private Long id;

    private Long testId;

    private String text;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "questionId")
    private Set<Answer> answers;

    public Question() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTestId() {
        return testId;
    }

    public void setTestId(Long testId) {
        this.testId = testId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Set<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(Set<Answer> answers) {
        this.answers = answers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Question question = (Question) o;

        return id.equals(question.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
