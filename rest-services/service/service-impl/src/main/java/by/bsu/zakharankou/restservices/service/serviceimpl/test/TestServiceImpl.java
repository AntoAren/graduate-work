package by.bsu.zakharankou.restservices.service.serviceimpl.test;

import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.test.views.AllTestsView;
import by.bsu.zakharankou.restservices.model.test.views.TestForPassingView;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.repository.category.CategoryRepository;
import by.bsu.zakharankou.restservices.repository.test.TestRepository;
import by.bsu.zakharankou.restservices.repository.topic.TopicRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.test.TestService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import by.bsu.zakharankou.restservices.service.serviceimpl.util.TestUtils;
import by.bsu.zakharankou.restservices.service.serviceimpl.util.ViewBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public List<AllTestsView> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Page<Test> page;
        Category category = categoryRepository.findOne(categoryId);
        Topic topic = topicRepository.findOne(topicId);
        String preparedSearch = null;

        if (hasText(search)) {
            preparedSearch = search.toLowerCase().trim();
        }

        if (category != null && topic != null && preparedSearch != null) {
            page = testRepository.findByCategoryAndTopicAndName(category, topic, preparedSearch, pageable);
        } else if (category != null && topic != null) {
            page = testRepository.findByCategoryAndTopic(category, topic, pageable);
        } else if (category != null && preparedSearch != null) {
            page = testRepository.findByCategoryAndName(category, preparedSearch, pageable);
        } else if (topic != null && preparedSearch != null) {
            page = testRepository.findByTopicAndName(topic, preparedSearch, pageable);
        } else if (category != null) {
            page = testRepository.findByCategory(category, pageable);
        } else if (topic != null) {
            page = testRepository.findByTopic(topic, pageable);
        } else if (preparedSearch != null) {
            page = testRepository.findByName(preparedSearch, pageable);
        } else {
            page = testRepository.findAll(pageable);
        }

        return ViewBuilder.build(page.getContent(), AllTestsView.class, Test.class);
    }

    @Override
    public TestForPassingView getTestForPassing(Long testId) {
        Test test = testRepository.getOne(testId);

        TestForPassingView testForPassingView = new TestForPassingView();
        testForPassingView.setId(test.getId());
        testForPassingView.setName(test.getName());
        testForPassingView.setQuestions(TestUtils.getRandomlyQuestions(test.getQuestions()));
        testForPassingView.setPassingTime(test.getPassingTime());

        return testForPassingView;
    }
}
