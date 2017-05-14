package by.bsu.zakharankou.restservices.controller.answer;

import by.bsu.zakharankou.restservices.controller.util.JsonResponseEntityFactory;
import by.bsu.zakharankou.restservices.controller.util.ObjectMapper;
import by.bsu.zakharankou.restservices.controller.view.ListView;
import by.bsu.zakharankou.restservices.model.answer.view.ExistingAnswerView;
import by.bsu.zakharankou.restservices.model.util.StringUtil;
import by.bsu.zakharankou.restservices.service.serviceapi.Messages;
import by.bsu.zakharankou.restservices.service.serviceapi.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/answers")
public class AnswerController {

    private AnswerService answerService;

    private JsonResponseEntityFactory jsonResponseEntityFactory;

    @Autowired
    public AnswerController(AnswerService answerService, JsonResponseEntityFactory jsonResponseEntityFactory) {
        this.answerService = answerService;
        this.jsonResponseEntityFactory = jsonResponseEntityFactory;
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<ExistingAnswerView> getExistingAnswers(@PathVariable String testId) {
        Long passingTestId = StringUtil.getIdFromString(testId);
        List<ExistingAnswerView> existingAnswerViews = answerService.getExistingAnswers(passingTestId);
        return new ListView<>(existingAnswerViews, existingAnswerViews.size());
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveAnswers(@PathVariable String testId, @RequestBody String body) {
        Long passingTestId = StringUtil.getIdFromString(testId);
        Map<String, Object> details = ObjectMapper.read(body);
        answerService.saveAnswers(passingTestId, details);
        return jsonResponseEntityFactory.createMessageResponse(Messages.INFO_ANSWERS_HAVE_BEEN_SAVED, HttpStatus.CREATED);
    }
}
