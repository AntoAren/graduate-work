package by.bsu.zakharankou.restservices.service.serviceimpl.test;

import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.service.serviceapi.test.TestService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public class TestServiceImpl implements TestService {


    @Override
    public Page<Test> getTestsForAllTestsPage(Long categoryId, Long topicId, String search, Pageable pageable) {
        Category category = 
        return null;
    }
}
