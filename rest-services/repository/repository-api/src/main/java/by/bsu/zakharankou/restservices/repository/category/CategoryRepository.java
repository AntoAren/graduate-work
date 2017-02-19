package by.bsu.zakharankou.restservices.repository.category;

import by.bsu.zakharankou.restservices.model.category.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
