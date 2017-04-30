package by.bsu.zakharankou.restservices.service.serviceimpl.category;

import by.bsu.zakharankou.restservices.service.serviceapi.category.CategoryService;
import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.repository.category.CategoryRepository;
import by.bsu.zakharankou.restservices.service.serviceimpl.transaction.ReadOnlyTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import java.util.List;

@ReadOnlyTransactional
@Service
public class CategoryServiceImpl implements CategoryService {

    private static final Sort sortByName = new Sort(Direction.ASC, Category.FIELD_NAME);

    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll(sortByName);
    }
}
