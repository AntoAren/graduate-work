package by.bsu.zakharankou.restservices.controller.answer;

import by.bsu.zakharankou.restservices.controller.util.ObjectMapper;
import by.bsu.zakharankou.restservices.controller.view.ListView;
import by.bsu.zakharankou.restservices.model.answer.view.ExistingAnswer;
import by.bsu.zakharankou.restservices.service.serviceapi.answer.AnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping(value = "/answers")
public class AnswerController {

    private AnswerService answerService;

    @Autowired
    public AnswerController(AnswerService answerService) {
        this.answerService = answerService;
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<ExistingAnswer> getExistingAnswers(@PathVariable String testId) {
        return new ListView<>(new ArrayList<>(), 0, null);
    }

    @RequestMapping(value = "/{testId}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> saveAnswers(@PathVariable String testId, @RequestBody String body) {
        Map<String, Object> details = ObjectMapper.read(body);
        answerService.saveAnswers(details);
        return null;
    }
}
