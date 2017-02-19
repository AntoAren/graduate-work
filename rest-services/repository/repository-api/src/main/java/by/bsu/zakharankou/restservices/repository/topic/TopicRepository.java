package by.bsu.zakharankou.restservices.repository.topic;

import by.bsu.zakharankou.restservices.model.topic.Topic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TopicRepository extends JpaRepository<Topic, Long> {
}
