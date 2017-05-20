package by.bsu.zakharankou.restservices.model.result.views;

import by.bsu.zakharankou.restservices.model.JsonDateSerializer;
import by.bsu.zakharankou.restservices.model.result.Result;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class ResultView {

    private String username;
    private Long score;
    private Date completionDate;

    public ResultView(Result result) {
        this.username = result.getUser().getUsername();
        this.score = result.getScore();
        this.completionDate = result.getCompletionDate();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Long getScore() {
        return score;
    }

    public void setScore(Long score) {
        this.score = score;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCompletionDate() {
        return completionDate;
    }

    public void setCompletionDate(Date completionDate) {
        this.completionDate = completionDate;
    }
}
