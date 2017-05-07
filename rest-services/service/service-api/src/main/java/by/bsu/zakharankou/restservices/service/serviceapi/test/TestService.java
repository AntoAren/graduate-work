package by.bsu.zakharankou.restservices.service.serviceapi.test;

import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.test.views.AllTestsView;
import by.bsu.zakharankou.restservices.model.test.views.TestForPassingView;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TestService {

    List<AllTestsView> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable);

    TestForPassingView getTestForPassing(Long testId);

    Test createTest(Map<String, Object> details);
}
