package by.bsu.zakharankou.restservices.service.serviceimpl.answer;

import by.bsu.zakharankou.restservices.model.question.Question;
import by.bsu.zakharankou.restservices.model.record.Record;
import by.bsu.zakharankou.restservices.model.user.User;
import by.bsu.zakharankou.restservices.repository.answer.AnswerRepository;
import by.bsu.zakharankou.restservices.repository.test.TestRepository;
import by.bsu.zakharankou.restservices.service.serviceapi.answer.AnswerService;
import by.bsu.zakharankou.restservices.service.serviceapi.user.UserService;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@ReadOnlyTransactional
@Service
public class AnswerServiceImpl implements AnswerService {

    private static final String RECORD_FIELD_RECORDS = "records";
    private static final String RECORD_FIELD_QUESTION_ID = "questionId";
    private static final String RECORD_FIELD_ANSWER_IDS = "answerIds";

    private UserService userService;

    private TestRepository testRepository;

    private AnswerRepository answerRepository;

    @Autowired
    public AnswerServiceImpl(UserService userService, TestRepository testRepository, AnswerRepository answerRepository) {
        this.userService = userService;
        this.testRepository = testRepository;
        this.answerRepository = answerRepository;
    }

    public void saveAnswers(Map<String, Object> details) {
        createRecords(details);
    }

    private List<Record> createRecords(Map<String, Object> details) {
        List<Record> records = new ArrayList<>();

        User user = userService.getUser(userService.getAuthenticatedUser());
        List<Map<String, Object>> recordDetails = (List<Map<String,Object>>) details.get(RECORD_FIELD_RECORDS);

        Integer questionId;
        List<Integer> answerIds;
        for (Map<String, Object> recordDetail : recordDetails) {
            questionId = (Integer)recordDetail.get(RECORD_FIELD_QUESTION_ID);
            answerIds = (ArrayList<Integer>) recordDetail.get(RECORD_FIELD_ANSWER_IDS);
        }

        return null;
    }
}
