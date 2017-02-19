package by.bsu.zakharankou.restservices.service.serviceimpl.test;

import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.repository.category.CategoryRepository;
import by.bsu.zakharankou.restservices.repository.test.TestRepository;
import by.bsu.zakharankou.restservices.repository.topic.TopicRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.test.TestService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static org.springframework.util.StringUtils.hasText;

@ReadOnlyTransactional
@Service
public class TestServiceImpl implements TestService {

    private CategoryRepository categoryRepository;

    private TopicRepository topicRepository;

    private TestRepository testRepository;

    @Autowired
    public TestServiceImpl(CategoryRepository categoryRepository, TopicRepository topicRepository,
                           TestRepository testRepository) {
        this.categoryRepository = categoryRepository;
        this.topicRepository = topicRepository;
        this.testRepository = testRepository;
    }

    @Override
    public Page<Test> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Category category = categoryRepository.findOne(categoryId);
        Topic topic = topicRepository.findOne(topicId);
        String preparedSearch = null;

        if (hasText(search)) {
            preparedSearch = search.toLowerCase().trim();
        }

        if (category != null && topic != null && preparedSearch != null) {
            return testRepository.findByCategoryAndTopicAndName(category, topic, preparedSearch, pageable);
        } else if (category != null && topic != null) {
            return testRepository.findByCategoryAndTopic(category, topic, pageable);
        } else if (category != null && preparedSearch != null) {
            return testRepository.findByCategoryAndName(category, preparedSearch, pageable);
        } else if (topic != null && preparedSearch != null) {
            return testRepository.findByTopicAndName(topic, preparedSearch, pageable);
        } else if (category != null) {
            return testRepository.findByCategory(category, pageable);
        } else if (topic != null) {
            return testRepository.findByTopic(topic, pageable);
        } else if (preparedSearch != null) {
            return testRepository.findByName(preparedSearch, pageable);
        } else {
            return testRepository.findAll(pageable);
        }
    }
}
