package by.bsu.zakharankou.restservices.model.topic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Topic {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Topic() {
    }

    public Topic(String name) {
        this.name = name;
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

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Topic topic = (Topic) obj;
        if (name == null) {
            if (topic.name != null) {
                return false;
            }
        } else if (!name.equals(topic.name)) {
            return false;
        }
        if (id == null) {
            if (topic.id != null) {
                return false;
            }
        } else if (!id.equals(topic.id)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id != null) ? id.hashCode() : 0);
        result = prime * result + ((name != null) ? name.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Topic [id=" + id + ", name=" + name + "]";
    }
}
