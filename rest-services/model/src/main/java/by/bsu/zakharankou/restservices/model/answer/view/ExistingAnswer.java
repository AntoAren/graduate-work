package by.bsu.zakharankou.restservices.model.answer.view;

import java.util.Set;

public class ExistingAnswer {

    private Long questionId;
    private Set<Long> answerIds;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public Set<Long> getAnswerIds() {
        return answerIds;
    }

    public void setAnswerIds(Set<Long> answerIds) {
        this.answerIds = answerIds;
    }
}
