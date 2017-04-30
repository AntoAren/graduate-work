package by.bsu.zakharankou.restservices.service.serviceapi.category;

import by.bsu.zakharankou.restservices.model.category.Category;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CategoryService {

    List<Category> getCategories();
}
