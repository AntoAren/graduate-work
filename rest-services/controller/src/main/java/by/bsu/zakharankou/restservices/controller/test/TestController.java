package by.bsu.zakharankou.restservices.controller.test;

import by.bsu.zakharankou.restservices.controller.util.JsonResponseEntityFactory;
import by.bsu.zakharankou.restservices.controller.util.ObjectMapper;
import by.bsu.zakharankou.restservices.model.result.views.ResultView;
import by.bsu.zakharankou.restservices.model.test.views.*;
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

    private TestService testService;

    private JsonResponseEntityFactory jsonResponseEntityFactory;

    @Autowired
    public TestController(TestService testService, JsonResponseEntityFactory jsonResponseEntityFactory) {
        this.testService = testService;
        this.jsonResponseEntityFactory = jsonResponseEntityFactory;
    }

    @RequestMapping(value = "", params = "view=allTests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

    @RequestMapping(value = "/passing/{testIdString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TestForPassingView getTestForPassing(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        return testService.getTestForPassing(testId);
    }

    @RequestMapping(value = "", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> createTest(@RequestBody String body) {
        Map<String, Object> details = ObjectMapper.read(body);
        Test test = testService.createTest(details);
        return jsonResponseEntityFactory.createMessageResponse(String.format(Messages.INFO_TEST_HAS_BEEN_ADDED, test.getName()), HttpStatus.CREATED);
    }

    @RequestMapping(value = "", params = "view=assignedToMe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<AssignedToMeView> getTestsForAssignedToMePage(@RequestParam(required = false) String category,
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

        List<AssignedToMeView> assignedToMeViewList = testService.getTestsForAssignedToMePage(categoryId, topicId, search, pageable);

        return new ListView<>(assignedToMeViewList, assignedToMeViewList.size(), sorting);
    }

    @RequestMapping(value = "", params = "view=createdByMe", method = RequestMethod.GET,  produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<CreatedByMeView> getTestsForCreatedByMePage(@RequestParam(required = false) String category,
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

        List<CreatedByMeView> createdByMeViewList = testService.getTestsForCreatedByMePage(categoryId, topicId, search, pageable);

        return new ListView<>(createdByMeViewList, createdByMeViewList.size(), sorting);
    }

    @RequestMapping(value = "", params = "view=myResults", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<MyResultsView> getTestsForMyResultsPage(@RequestParam(required = false) String category,
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

        List<MyResultsView> myResultsViewList = testService.getTestsForMyResultsPage(categoryId, topicId, search, pageable);

        return new ListView<>(myResultsViewList, myResultsViewList.size(), sorting);
    }

    @RequestMapping(value = "/preview/{testIdString}", params = "view=allTests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AllTestsPreviewView getPreviewInfoForAllTestsPage(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        return testService.getPreviewInfoForAllTestsPage(testId);
    }

    @RequestMapping(value = "/preview/{testIdString}", params = "view=assignedToMe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public AssignedToMePreviewView getPreviewInfoForAssignedToMePage(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        return testService.getPreviewInfoForAssignedToMePage(testId);
    }

    @RequestMapping(value = "/preview/{testIdString}", params = "view=myResults", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public MyResultsPreviewView getPreviewInfoForMyResultsPage(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        return testService.getPreviewInfoForMyResultsPage(testId);
    }

    @RequestMapping(value = "/preview/{testIdString}", params = "view=createdByMe", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CreatedByMePreviewView getPreviewInfoForCreatedByMePage(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        return testService.getPreviewInfoForCreatedByMePage(testId);
    }

    @RequestMapping(value = "/{testIdString}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> deleteTest(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        testService.deleteTest(testId);

        return jsonResponseEntityFactory.createMessageResponse(Messages.INFO_TEST_HAS_BEEN_DELETED, HttpStatus.OK);
    }

    @RequestMapping(value = "/add/{testIdString}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> assignTestToMe(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        testService.assignTestToMe(testId);

        return jsonResponseEntityFactory.createMessageResponse(Messages.INFO_TEST_HAS_BEEN_ASSIGNED, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/complete/{testIdString}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> completeTest(@PathVariable String testIdString, @RequestBody String body) {
        Long testId = StringUtil.getIdFromString(testIdString);
        Map<String, Object> details = ObjectMapper.read(body);

        testService.completeTest(testId, details);

        return jsonResponseEntityFactory.createMessageResponse(Messages.INFO_TEST_HAS_BEEN_COMPLETED, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/results/{testIdString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<ResultView> getTestResults(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        List<ResultView> resultViews = testService.getTestResults(testId);
        return new ListView<>(resultViews, resultViews.size());
    }

    @RequestMapping(value = "/{testIdString}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public EditTestView getTestForEditing(@PathVariable String testIdString) {
        Long testId = StringUtil.getIdFromString(testIdString);

        return testService.getTestForEditing(testId);
    }

    @RequestMapping(value = "/{testIdString}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<String> editTest(@PathVariable String testIdString, @RequestBody String body) {
        Long testId = StringUtil.getIdFromString(testIdString);
        Map<String, Object> details = ObjectMapper.read(body);

        Test test = testService.editTest(testId, details);

        return jsonResponseEntityFactory.createMessageResponse(Messages.INFO_TEST_HAS_BEEN_EDITED, HttpStatus.CREATED);
    }
}
