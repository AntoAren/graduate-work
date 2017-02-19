package by.bsu.zakharankou.restservices.repository.test;

import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Page<Test> findByCategoryAndTopicAndName(@Param("category") Category category, @Param("topic") Topic topic,
                                             @Param("name") String name, Pageable pageable);

    Page<Test> findByCategoryAndTopic(@Param("category") Category category, @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByCategoryAndName(@Param("category") Category category, @Param("name") String name, Pageable pageable);

    Page<Test> findByTopicAndName(@Param("topic") Topic topic, @Param("name") String name, Pageable pageable);

    Page<Test> findByCategory(@Param("category") Category category, Pageable pageable);

    Page<Test> findByTopic(@Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByName(@Param("name") String name, Pageable pageable);
}
