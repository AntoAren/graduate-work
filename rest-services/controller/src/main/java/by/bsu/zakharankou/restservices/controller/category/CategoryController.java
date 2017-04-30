package by.bsu.zakharankou.restservices.controller.category;

import by.bsu.zakharankou.restservices.controller.view.ListView;
import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.service.serviceapi.category.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * Controller for managing categories.
 */
@Controller
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    /**
     * Gets list of all categories.
     *
     * @return {@link List} of {@link Category}
     */
    @RequestMapping(value = "", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ListView<Category> getCategories() {
        List<Category> categories = categoryService.getCategories();
        return new ListView<>(new ArrayList<>(categories), categories.size());
    }
}
