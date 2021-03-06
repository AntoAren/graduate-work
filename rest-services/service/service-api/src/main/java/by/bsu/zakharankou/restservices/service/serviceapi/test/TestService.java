package by.bsu.zakharankou.restservices.service.serviceapi.test;

import by.bsu.zakharankou.restservices.model.result.views.ResultView;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.test.views.*;

import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface TestService {

    List<AllTestsView> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable);

    List<AssignedToMeView> getTestsForAssignedToMePage(Long categoryId, Long topicId, String search, Pageable pageable);

    List<CreatedByMeView> getTestsForCreatedByMePage(Long categoryId, Long topicId, String search, Pageable pageable);

    List<MyResultsView> getTestsForMyResultsPage(Long categoryId, Long topicId, String search, Pageable pageable);

    TestForPassingView getTestForPassing(Long testId);

    Test createTest(Map<String, Object> details);

    AllTestsPreviewView getPreviewInfoForAllTestsPage(Long testId);

    AssignedToMePreviewView getPreviewInfoForAssignedToMePage(Long testId);

    MyResultsPreviewView getPreviewInfoForMyResultsPage(Long testId);

    CreatedByMePreviewView getPreviewInfoForCreatedByMePage(Long testId);

    void assignTestToMe(Long testId);

    void completeTest(Long testId, Map<String, Object> details);

    EditTestView getTestForEditing(Long testId);

    Test editTest(Long testId, Map<String, Object> details);

    List<ResultView> getTestResults(Long testId);

    void deleteTest(Long testId);
}
