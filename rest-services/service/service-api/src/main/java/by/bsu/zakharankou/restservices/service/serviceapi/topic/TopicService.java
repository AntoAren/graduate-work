package by.bsu.zakharankou.restservices.service.serviceapi.topic;

import by.bsu.zakharankou.restservices.model.topic.Topic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TopicService {

    List<Topic> getTopics();
}
