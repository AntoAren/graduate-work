package by.bsu.zakharankou.restservices.controller.test;

import by.bsu.zakharankou.restservices.controller.util.JsonResponseEntityFactory;
import by.bsu.zakharankou.restservices.controller.util.ObjectMapper;
import by.bsu.zakharankou.restservices.model.test.views.AllTestsView;
import by.bsu.zakharankou.restservices.model.test.views.TestForPassingView;
import by.bsu.zakharankou.restservices.controller.util.Paging;
import by.bsu.zakharankou.restservices.controller.util.SortBuilder;
import by.bsu.zakharankou.restservices.controller.view.ListView;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.util.StringUtil;
import by.bsu.zakharankou.restservices.service.serviceapi.Messages;
import by.bsu.zakharankou.restservices.service.serviceapi.test.TestService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @Autowired
    private JsonResponseEntityFactory jsonResponseEntityFactory;

    @Autowired
    public TestController(TestService testService) {
        this.testService = testService;
    }

    @RequestMapping(value = "/all-tests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<AllTestsView> getTestsForAllTestsPage(@RequestParam(required = false) String category,
                                                          @RequestParam(required = false) String topic,
                                                          @RequestParam(required = false) String search,
                                                          @RequestParam(required = false) String sort,
                                                          @RequestParam(required = false) String offset,
                                                          @RequestParam(required = false) String max,
                                                          @RequestParam(required = false) String order) {
        Sort sorting = new SortBuilder(Test.class, Test.FIELD_NAME).desc().sort(sort, order).build();
        Pageable pageable = Paging.createPageable(offset, max, sorting);
        Long categoryId = StringUtil.getIdFromString(category);
        Long topicId = StringUtil.getIdFromString(topic);

        List<AllTestsView> allTestsViewList = testService.getTestsForAllTestsPage(categoryId, topicId, search, pageable);

        return new ListView<>(allTestsViewList, allTestsViewList.size(), sorting);
    }

    @RequestMapping(value = "/{testId}/passing", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public TestForPassingView getTestForPassing(@PathVariable String testId) {
        Long passingTestId = StringUtil.getIdFromString(testId);

        return testService.getTestForPassing(passingTestId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTest(@RequestBody String body) {
        Map<String, Object> details = ObjectMapper.read(body);
        Test test = testService.createTest(details);
        return jsonResponseEntityFactory.createMessageResponse(String.format(Messages.INFO_TEST_HAS_BEEN_ADDED, test.getName()), HttpStatus.CREATED);
    }
}
