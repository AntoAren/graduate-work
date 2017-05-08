package by.bsu.zakharankou.restservices.service.serviceimpl.test;

import by.bsu.zakharankou.restservices.model.answer.Answer;
import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.test.views.AllTestsView;
import by.bsu.zakharankou.restservices.model.test.views.TestForPassingView;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.repository.answer.AnswerRepository;
import by.bsu.zakharankou.restservices.repository.category.CategoryRepository;
import by.bsu.zakharankou.restservices.repository.question.QuestionRepository;
import by.bsu.zakharankou.restservices.repository.test.TestRepository;
import by.bsu.zakharankou.restservices.repository.topic.TopicRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.test.TestService;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import by.bsu.zakharankou.restservices.service.serviceimpl.util.TestUtils;
import by.bsu.zakharankou.restservices.service.serviceimpl.util.ViewBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;

import static org.springframework.util.StringUtils.hasText;

@ReadOnlyTransactional
@Service
public class TestServiceImpl implements TestService {

    private static final String TEST_FIELD_CATEGORY_ID = "categoryId";
    private static final String TEST_FIELD_NAME = "name";
    private static final String TEST_FIELD_QUESTIONS_NUMBER = "questionsNumber";
    private static final String TEST_FIELD_PASSING_TIME = "passingTime";
    private static final String TEST_FIELD_PRIVATE_TEST = "private";
    private static final String TEST_FIELD_SHOW_CORRECT_ANSWERS = "showCorrectAnswers";
    private static final String TEST_FIELD_TOPIC_ID = "topicId";
    private static final String TEST_FIELD_QUESTIONS = "questions";

    private static final String TEST_FIELD_QUESTION_TEXT = "questionText";
    private static final String TEST_FIELD_ANSWERS = "answers";

    private static final String TEST_FIELD_ANSWER_TEXT = "answerText";
    private static final String TEST_FIELD_ANSWER_CORRECT = "correct";

    private CategoryRepository categoryRepository;

    private TopicRepository topicRepository;

    private TestRepository testRepository;

    private UserService userService;

    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    @Autowired
    public TestServiceImpl(CategoryRepository categoryRepository, TopicRepository topicRepository,
                           TestRepository testRepository, UserService userService,
                           QuestionRepository questionRepository, AnswerRepository answerRepository) {
        this.categoryRepository = categoryRepository;
        this.topicRepository = topicRepository;
        this.testRepository = testRepository;
        this.userService = userService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
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

    @Override
    public Test createTest(Map<String, Object> details) {
        Test test = new Test();
        User user = userService.getUser(userService.getAuthenticatedUser());
        test.setAuthor(user);
        test.setCategory(categoryRepository.findOne(Long.parseLong((String)details.get(TEST_FIELD_CATEGORY_ID))));
        test.setName((details.get(TEST_FIELD_NAME) == null) ? "Без названия" : (String)details.get(TEST_FIELD_NAME));
        test.setNumberQuestions(Long.valueOf((Integer)details.get(TEST_FIELD_QUESTIONS_NUMBER)));
        test.setPassingTime((details.get(TEST_FIELD_PASSING_TIME) == null) ? 0 : Long.parseLong((String)details.get(TEST_FIELD_PASSING_TIME)));
        test.setPrivateTest((Boolean)details.get(TEST_FIELD_PRIVATE_TEST));
        test.setShowCorrectAnswers((Boolean)details.get(TEST_FIELD_SHOW_CORRECT_ANSWERS));
        test.setTopic(topicRepository.findOne(Long.parseLong((String)details.get(TEST_FIELD_TOPIC_ID))));

        testRepository.save(test);

        test.setQuestions(createQuestions((List<Map<String,Object>>) details.get(TEST_FIELD_QUESTIONS), test.getId()));

        testRepository.save(test);

        return test;
    }

    private Set<Question> createQuestions(List<Map<String, Object>> questionDetails, Long testId) {
        Set<Question> questions = new HashSet<>();
        Question question;

        for (Map<String, Object> questionDetail : questionDetails) {
            question = new Question();
            question.setTestId(testId);
            question.setText((String)questionDetail.get(TEST_FIELD_QUESTION_TEXT));

            questionRepository.save(question);

            question.setAnswers(createAnswers((List<Map<String,Object>>) questionDetail.get(TEST_FIELD_ANSWERS), question.getId()));

            questionRepository.save(question);

            questions.add(question);
        }

        return questions;
    }

    private Set<Answer> createAnswers(List<Map<String, Object>> answerDetails, Long questionId) {
        Set<Answer> answers = new HashSet<>();
        Answer answer;

        for (Map<String, Object> answerDetail : answerDetails) {
            answer = new Answer();
            answer.setQuestionId(questionId);
            answer.setText((String)answerDetail.get(TEST_FIELD_ANSWER_TEXT));
            answer.setCorrect((Boolean)answerDetail.get(TEST_FIELD_ANSWER_CORRECT));

            answerRepository.save(answer);

            answers.add(answer);
        }
        return answers;
    }
}
