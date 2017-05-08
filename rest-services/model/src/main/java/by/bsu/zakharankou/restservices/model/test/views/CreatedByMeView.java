package by.bsu.zakharankou.restservices.model.test.views;

import by.bsu.zakharankou.restservices.model.JsonDateSerializer;
import by.bsu.zakharankou.restservices.model.test.Test;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class CreatedByMeView {

    private Long id;
    private String name;
    private String category;
    private String topic;
    private Date creationDate;

    public CreatedByMeView(Test test) {
        this.id = test.getId();
        this.name = test.getName();
        this.category = test.getCategory().getName();
        this.topic = test.getTopic().getName();
        this.creationDate = test.getCreationDate();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getTopic() {
        return topic;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Date getCreationDate() {
        return creationDate;
    }
}
