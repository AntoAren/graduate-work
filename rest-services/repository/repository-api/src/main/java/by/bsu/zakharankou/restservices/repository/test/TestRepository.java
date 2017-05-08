package by.bsu.zakharankou.restservices.repository.test;

import by.bsu.zakharankou.restservices.model.category.Category;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.topic.Topic;
import by.bsu.zakharankou.restservices.model.user.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface TestRepository extends JpaRepository<Test, Long> {

    Page<Test> findByCategoryAndTopicAndNameAndPrivateTestFalse(@Param("category") Category category,
                                                                @Param("topic") Topic topic, @Param("name") String name,
                                                                Pageable pageable);

    Page<Test> findByCategoryAndTopicAndPrivateTestFalse(@Param("category") Category category,
                                                         @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByCategoryAndNameAndPrivateTestFalse(@Param("category") Category category,
                                                        @Param("name") String name, Pageable pageable);

    Page<Test> findByTopicAndNameAndPrivateTestFalse(@Param("topic") Topic topic, @Param("name") String name,
                                                     Pageable pageable);

    Page<Test> findByCategoryAndPrivateTestFalse(@Param("category") Category category, Pageable pageable);

    Page<Test> findByTopicAndPrivateTestFalse(@Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByNameAndPrivateTestFalse(@Param("name") String name, Pageable pageable);

    Page<Test> findByPrivateTestFalse(Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndTopicAndName(@Param("author") User user, @Param("category") Category category,
                                                      @Param("topic") Topic topic, @Param("name") String name,
                                                      Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndTopic(@Param("author") User user, @Param("category") Category category,
                                               @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndName(@Param("author") User user, @Param("category") Category category,
                                              @Param("name") String name, Pageable pageable);

    Page<Test> findByAuthorAndTopicAndName(@Param("author") User user, @Param("topic") Topic topic,
                                           @Param("name") String name, Pageable pageable);

    Page<Test> findByAuthorAndCategory(@Param("author") User user, @Param("category") Category category,
                                       Pageable pageable);

    Page<Test> findByAuthorAndTopic(@Param("author") User user, @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByAuthorAndName(@Param("author") User user, @Param("name") String name, Pageable pageable);

    Page<Test> findByAuthor(@Param("author") User user, Pageable pageable);

    Page<Test> findByCategoryAndTopicAndNameAndIdIn(@Param("category") Category category, @Param("topic") Topic topic,
                                                    @Param("name") String name, @Param("ids") Collection<Long> ids,
                                                    Pageable pageable);

    Page<Test> findByCategoryAndTopicAndIdIn(@Param("category") Category category, @Param("topic") Topic topic,
                                             @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByCategoryAndNameAndIdIn(@Param("category") Category category, @Param("name") String name,
                                            @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByTopicAndNameAndIdIn(@Param("topic") Topic topic, @Param("name") String name,
                                         @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByCategoryAndIdIn(@Param("category") Category category, @Param("ids") Collection<Long> ids,
                                     Pageable pageable);

    Page<Test> findByTopicAndIdIn(@Param("topic") Topic topic, @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByNameAndIdIn(@Param("name") String name, @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByIdIn(@Param("ids") Collection<Long> ids, Pageable pageable);
}
