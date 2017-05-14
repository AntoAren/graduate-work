package by.bsu.zakharankou.restservices.service.serviceimpl.test;

import by.bsu.zakharankou.restservices.model.answer.Answer;
import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.result.Result;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.test.views.*;
import by.bsu.zakharankou.restservices.model.testassignment.TestAssignment;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.repository.answer.AnswerRepository;
import by.bsu.zakharankou.restservices.repository.category.CategoryRepository;
import by.bsu.zakharankou.restservices.repository.question.QuestionRepository;
import by.bsu.zakharankou.restservices.repository.result.ResultRepository;
import by.bsu.zakharankou.restservices.repository.test.TestRepository;
import by.bsu.zakharankou.restservices.repository.testassignment.TestAssignmentRepository;
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

    private static final Long SEVEN_DAYS_IN_MILLISECONDS = 604800000L;

    private CategoryRepository categoryRepository;

    private TopicRepository topicRepository;

    private TestRepository testRepository;

    private UserService userService;

    private QuestionRepository questionRepository;

    private AnswerRepository answerRepository;

    private TestAssignmentRepository testAssignmentRepository;

    private ResultRepository resultRepository;

    @Autowired
    public TestServiceImpl(CategoryRepository categoryRepository, TopicRepository topicRepository,
                           TestRepository testRepository, UserService userService, QuestionRepository questionRepository,
                           AnswerRepository answerRepository, TestAssignmentRepository testAssignmentRepository,
                           ResultRepository resultRepository) {
        this.categoryRepository = categoryRepository;
        this.topicRepository = topicRepository;
        this.testRepository = testRepository;
        this.userService = userService;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.testAssignmentRepository = testAssignmentRepository;
        this.resultRepository = resultRepository;
    }

    @Override
    public List<AllTestsView> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Page<Test> page;
        Category category = null;
        Topic topic = null;
        String preparedSearch = null;

        if (categoryId != null) {
            category = categoryRepository.findOne(categoryId);
        }
        if (topicId != null) {
            topic = topicRepository.findOne(topicId);
        }
        if (hasText(search)) {
            preparedSearch = search.toLowerCase().trim();
        }

        if (category != null && topic != null && preparedSearch != null) {
            page = testRepository.findByCategoryAndTopicAndNameAndPrivateTestFalse(category, topic, preparedSearch, pageable);
        } else if (category != null && topic != null) {
            page = testRepository.findByCategoryAndTopicAndPrivateTestFalse(category, topic, pageable);
        } else if (category != null && preparedSearch != null) {
            page = testRepository.findByCategoryAndNameAndPrivateTestFalse(category, preparedSearch, pageable);
        } else if (topic != null && preparedSearch != null) {
            page = testRepository.findByTopicAndNameAndPrivateTestFalse(topic, preparedSearch, pageable);
        } else if (category != null) {
            page = testRepository.findByCategoryAndPrivateTestFalse(category, pageable);
        } else if (topic != null) {
            page = testRepository.findByTopicAndPrivateTestFalse(topic, pageable);
        } else if (preparedSearch != null) {
            page = testRepository.findByNameAndPrivateTestFalse(preparedSearch, pageable);
        } else {
            page = testRepository.findByPrivateTestFalse(pageable);
        }

        return ViewBuilder.build(page.getContent(), AllTestsView.class, Test.class);
    }

    @Override
    public List<AssignedToMeView> getTestsForAssignedToMePage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Page<Test> page;
        Category category = null;
        Topic topic = null;
        String preparedSearch = null;
        User user = userService.getUser(userService.getAuthenticatedUser());

        if (categoryId != null) {
            category = categoryRepository.findOne(categoryId);
        }
        if (topicId != null) {
            topic = topicRepository.findOne(topicId);
        }
        if (hasText(search)) {
            preparedSearch = search.toLowerCase().trim();
        }

        List<Long> ids = getTestIdsFromTestAssignments(testAssignmentRepository.findByUser(user));

        page = getPageByCategoryAndTopicAndSearchAndIdIn(category, topic, preparedSearch, ids, pageable);

        return ViewBuilder.build(page.getContent(), AssignedToMeView.class, Test.class);
    }

    @Override
    public List<CreatedByMeView> getTestsForCreatedByMePage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Page<Test> page;
        Category category = null;
        Topic topic = null;
        String preparedSearch = null;
        User user = userService.getUser(userService.getAuthenticatedUser());

        if (categoryId != null) {
            category = categoryRepository.findOne(categoryId);
        }
        if (topicId != null) {
            topic = topicRepository.findOne(topicId);
        }
        if (hasText(search)) {
            preparedSearch = search.toLowerCase().trim();
        }

        if (category != null && topic != null && preparedSearch != null) {
            page = testRepository.findByAuthorAndCategoryAndTopicAndName(user, category, topic, preparedSearch, pageable);
        } else if (category != null && topic != null) {
            page = testRepository.findByAuthorAndCategoryAndTopic(user, category, topic, pageable);
        } else if (category != null && preparedSearch != null) {
            page = testRepository.findByAuthorAndCategoryAndName(user, category, preparedSearch, pageable);
        } else if (topic != null && preparedSearch != null) {
            page = testRepository.findByAuthorAndTopicAndName(user, topic, preparedSearch, pageable);
        } else if (category != null) {
            page = testRepository.findByAuthorAndCategory(user, category, pageable);
        } else if (topic != null) {
            page = testRepository.findByAuthorAndTopic(user, topic, pageable);
        } else if (preparedSearch != null) {
            page = testRepository.findByAuthorAndName(user, preparedSearch, pageable);
        } else {
            page = testRepository.findByAuthor(user, pageable);
        }

        return ViewBuilder.build(page.getContent(), CreatedByMeView.class, Test.class);
    }

    @Override
    public List<MyResultsView> getTestsForMyResultsPage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Page<Test> page;
        Category category = null;
        Topic topic = null;
        String preparedSearch = null;
        User user = userService.getUser(userService.getAuthenticatedUser());

        if (categoryId != null) {
            category = categoryRepository.findOne(categoryId);
        }
        if (topicId != null) {
            topic = topicRepository.findOne(topicId);
        }
        if (hasText(search)) {
            preparedSearch = search.toLowerCase().trim();
        }

        List<Result> results = resultRepository.findByUser(user);

        List<Long> ids = getTestIdsFromResults(results);

        page = getPageByCategoryAndTopicAndSearchAndIdIn(category, topic, preparedSearch, ids, pageable);

        return ViewBuilder.build(page.getContent(), MyResultsView.class, Test.class);
    }

    @Override
    public TestForPassingView getTestForPassing(Long testId) {
        Test test = testRepository.findOne(testId);

        TestForPassingView testForPassingView = new TestForPassingView();
        testForPassingView.setId(test.getId());
        testForPassingView.setName(test.getName());
        testForPassingView.setQuestions(TestUtils.getRandomlyQuestions(test.getQuestions(), test.getNumberQuestions()));
        testForPassingView.setTestDuration(test.getPassingTime());
        testForPassingView.setStartedAt(new Date());

        return testForPassingView;
    }

    @Override
    public AllTestsPreviewView getPreviewInfoForAllTestsPage(Long testId) {
        // TODO: check whether user has access
        Test test = testRepository.findOne(testId);
        AllTestsPreviewView allTestsPreviewView = new AllTestsPreviewView(test);

        User user = userService.getUser(userService.getAuthenticatedUser());
        List<TestAssignment> testAssignments = testAssignmentRepository.findByUserAndTest(user, test);
        if (!testAssignments.isEmpty()) {
            allTestsPreviewView.setAdded(true);
        }

        // TODO: calculate average score correctly
        allTestsPreviewView.setAverageScore(50L);

        return allTestsPreviewView;
    }

    @Override
    public AssignedToMePreviewView getPreviewInfoForAssignedToMePage(Long testId) {
        // TODO: check whether user has access
        Test test = testRepository.findOne(testId);
        AssignedToMePreviewView assignedToMePreviewView = new AssignedToMePreviewView(test);

        // TODO: calculate average score correctly
        assignedToMePreviewView.setAverageScore(50L);

        return assignedToMePreviewView;
    }

    @Override
    public MyResultsPreviewView getPreviewInfoForMyResultsPage(Long testId) {
        return null;
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
        test.setDeadline(new Date((new Date()).getTime() + SEVEN_DAYS_IN_MILLISECONDS));

        testRepository.save(test);

        test.setQuestions(createQuestions((List<Map<String,Object>>) details.get(TEST_FIELD_QUESTIONS), test.getId()));

        testRepository.save(test);

        return test;
    }

    @Override
    public void assignTestToMe(Long testId) {
        User user = userService.getUser(userService.getAuthenticatedUser());
        Test test = testRepository.findOne(testId);

        TestAssignment testAssignment = new TestAssignment();
        testAssignment.setTest(test);
        testAssignment.setUser(user);

        testAssignmentRepository.save(testAssignment);
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

    private List<Long> getTestIdsFromTestAssignments(List<TestAssignment> testAssignments) {
        List<Long> testIds = new ArrayList<>();

        for (TestAssignment testAssignment : testAssignments) {
            testIds.add(testAssignment.getTest().getId());
        }

        return testIds;
    }

    private List<Long> getTestIdsFromResults(List<Result> results) {
        List<Long> testIds = new ArrayList<>();

        for (Result result : results) {
            testIds.add(result.getTest().getId());
        }

        return testIds;
    }

    private Page<Test> getPageByCategoryAndTopicAndSearchAndIdIn(Category category, Topic topic, String preparedSearch, List<Long> ids, Pageable pageable) {
        Page<Test> page;
        if (category != null && topic != null && preparedSearch != null) {
            page = testRepository.findByCategoryAndTopicAndNameAndIdIn(category, topic, preparedSearch, ids, pageable);
        } else if (category != null && topic != null) {
            page = testRepository.findByCategoryAndTopicAndIdIn(category, topic, ids, pageable);
        } else if (category != null && preparedSearch != null) {
            page = testRepository.findByCategoryAndNameAndIdIn(category, preparedSearch, ids, pageable);
        } else if (topic != null && preparedSearch != null) {
            page = testRepository.findByTopicAndNameAndIdIn(topic, preparedSearch, ids, pageable);
        } else if (category != null) {
            page = testRepository.findByCategoryAndIdIn(category, ids, pageable);
        } else if (topic != null) {
            page = testRepository.findByTopicAndIdIn(topic, ids, pageable);
        } else if (preparedSearch != null) {
            page = testRepository.findByNameAndIdIn(preparedSearch, ids, pageable);
        } else {
            page = testRepository.findByIdIn(ids, pageable);
        }

        return page;
    }
}
