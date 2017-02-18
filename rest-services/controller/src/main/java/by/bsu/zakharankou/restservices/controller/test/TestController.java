package by.bsu.zakharankou.restservices.controller.test;

import by.bsu.zakharankou.restservices.controller.test.views.AllTestsView;
import by.bsu.zakharankou.restservices.controller.util.Paging;
import by.bsu.zakharankou.restservices.controller.util.SortBuilder;
import by.bsu.zakharankou.restservices.controller.view.ListView;
import by.bsu.zakharankou.restservices.controller.view.ViewBuilder;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.util.StringUtil;
import by.bsu.zakharankou.restservices.service.serviceapi.test.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/tests")
public class TestController {

    @Autowired
    private TestService testService;

    @RequestMapping(value = "/", params = "view=allTests", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
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

        Page<Test> page = testService.getTestsForAllTestsPage(categoryId, topicId, search, pageable);
        List<AllTestsView> content = ViewBuilder.build(page.getContent(), AllTestsView.class, Test.class);

        return new ListView<>(content, page.getTotalElements(), page.getSort());
    }
}
