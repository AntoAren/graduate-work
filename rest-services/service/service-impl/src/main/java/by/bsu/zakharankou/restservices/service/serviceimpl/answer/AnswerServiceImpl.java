package by.bsu.zakharankou.restservices.service.serviceimpl.answer;

import by.bsu.zakharankou.restservices.model.answer.Answer;
import by.bsu.zakharankou.restservices.model.answer.view.ExistingAnswerView;
import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.record.Record;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.repository.answer.AnswerRepository;
import by.bsu.zakharankou.restservices.repository.question.QuestionRepository;
import by.bsu.zakharankou.restservices.repository.record.RecordRepository;
import by.bsu.zakharankou.restservices.repository.test.TestRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.answer.AnswerService;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@ReadOnlyTransactional
@Service
public class AnswerServiceImpl implements AnswerService {

    private static final String RECORD_FIELD_RECORDS = "records";
    private static final String RECORD_FIELD_QUESTION_ID = "questionId";
    private static final String RECORD_FIELD_ANSWER_IDS = "answerIds";

    private UserService userService;

    private TestRepository testRepository;

    private AnswerRepository answerRepository;

    private RecordRepository recordRepository;

    private QuestionRepository questionRepository;

    @Autowired
    public AnswerServiceImpl(UserService userService, TestRepository testRepository, AnswerRepository answerRepository,
                             RecordRepository recordRepository, QuestionRepository questionRepository) {
        this.userService = userService;
        this.testRepository = testRepository;
        this.answerRepository = answerRepository;
        this.recordRepository = recordRepository;
        this.questionRepository = questionRepository;
    }

    @Override
    public List<ExistingAnswerView> getExistingAnswers(Long testId) {
        Map<Long, ExistingAnswerView> existingAnswerViews = new HashMap<>();
        User user = userService.getUser(userService.getAuthenticatedUser());
        Test test = testRepository.findOne(testId);
        List<Record> records = recordRepository.findByUserAndTest(user, test);
        ExistingAnswerView existingAnswerView;

        for (Record record : records) {
            if (!existingAnswerViews.containsKey(record.getQuestion().getId())) {
                existingAnswerView = new ExistingAnswerView();
                existingAnswerView.setQuestionId(record.getQuestion().getId());
                existingAnswerView.setAnswerIds(new HashSet<>());
                existingAnswerViews.put(existingAnswerView.getQuestionId(), existingAnswerView);
            }
            existingAnswerViews.get(record.getQuestion().getId()).getAnswerIds().add(record.getAnswer().getId());
        }
        return new ArrayList<>(existingAnswerViews.values());
    }

    @Override
    public void saveAnswers(Long testId, Map<String, Object> details) {
        Test test = testRepository.findOne(testId);
        User user = userService.getUser(userService.getAuthenticatedUser());
        List<Record> records = createRecords(test, user, details);
        recordRepository.deleteByTestAndUser(test, user);
        recordRepository.save(records);
    }

    private List<Record> createRecords(Test test, User user, Map<String, Object> details) {
        List<Record> records = new ArrayList<>();

        List<Map<String, Object>> recordDetails = (List<Map<String,Object>>) details.get(RECORD_FIELD_RECORDS);

        Integer questionId;
        List<Integer> answerIds;
        Record record;
        Answer answer;
        Question question;

        for (Map<String, Object> recordDetail : recordDetails) {
            questionId = (Integer)recordDetail.get(RECORD_FIELD_QUESTION_ID);
            answerIds = (ArrayList<Integer>) recordDetail.get(RECORD_FIELD_ANSWER_IDS);
            question = questionRepository.findOne(Long.valueOf(questionId));
            if (!answerIds.isEmpty()) {
                for (Integer answerId : answerIds) {
                    answer = answerRepository.findOne(Long.valueOf(answerId));
                    record = new Record();
                    record.setUser(user);
                    record.setTest(test);
                    record.setAnswer(answer);
                    record.setQuestion(question);
                    records.add(record);
                }
            }
        }

        return records;
    }
}
