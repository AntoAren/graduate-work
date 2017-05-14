package by.bsu.zakharankou.restservices.service.serviceapi.answer;


import by.bsu.zakharankou.restservices.model.answer.view.ExistingAnswerView;

import java.util.List;
import java.util.Map;

public interface AnswerService {

    List<ExistingAnswerView> getExistingAnswers(Long testId);

    void saveAnswers(Long testId, Map<String, Object> details);
}
