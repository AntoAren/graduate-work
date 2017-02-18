package by.bsu.zakharankou.restservices.service.serviceapi.test;

import by.bsu.zakharankou.restservices.model.test.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TestService {

    Page<Test> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable);
}
