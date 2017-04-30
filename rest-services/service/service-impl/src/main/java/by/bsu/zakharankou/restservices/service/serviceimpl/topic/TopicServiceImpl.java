package by.bsu.zakharankou.restservices.service.serviceimpl.topic;

import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.repository.topic.TopicRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.topic.TopicService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@ReadOnlyTransactional
@Service
public class TopicServiceImpl implements TopicService {

    private static final Sort sortByName = new Sort(Sort.Direction.ASC, Topic.FIELD_NAME);

    @Autowired
    private TopicRepository topicRepository;

    @Override
    public List<Topic> getTopics() {
        return topicRepository.findAll(sortByName);
    }
}
