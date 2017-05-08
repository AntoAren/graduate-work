package by.bsu.zakharankou.restservices.model.testassignment;

import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.user.User;

import javax.persistence.*;

@Entity
public class TestAssignment {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    @JoinColumn(name = "test_id")
    private Test test;

    @ManyToOne
    @JoinColumn(name = "username")
    private User user;

    public TestAssignment () {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Test getTest() {
        return test;
    }

    public void setTest(Test test) {
        this.test = test;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
