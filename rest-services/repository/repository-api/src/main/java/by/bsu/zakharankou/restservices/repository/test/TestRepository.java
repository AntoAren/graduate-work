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

    Page<Test> findByCategoryAndTopicAndNameAndPrivateTestFalseAndActiveTrue(@Param("category") Category category,
                                                                @Param("topic") Topic topic, @Param("name") String name,
                                                                Pageable pageable);

    Page<Test> findByCategoryAndTopicAndPrivateTestFalseAndActiveTrue(@Param("category") Category category,
                                                         @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByCategoryAndNameAndPrivateTestFalseAndActiveTrue(@Param("category") Category category,
                                                        @Param("name") String name, Pageable pageable);

    Page<Test> findByTopicAndNameAndPrivateTestFalseAndActiveTrue(@Param("topic") Topic topic, @Param("name") String name,
                                                     Pageable pageable);

    Page<Test> findByCategoryAndPrivateTestFalseAndActiveTrue(@Param("category") Category category, Pageable pageable);

    Page<Test> findByTopicAndPrivateTestFalseAndActiveTrue(@Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByNameAndPrivateTestFalseAndActiveTrue(@Param("name") String name, Pageable pageable);

    Page<Test> findByPrivateTestFalseAndActiveTrue(Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndTopicAndNameAndActiveTrue(@Param("author") User user, @Param("category") Category category,
                                                      @Param("topic") Topic topic, @Param("name") String name,
                                                      Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndTopicAndActiveTrue(@Param("author") User user, @Param("category") Category category,
                                               @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndNameAndActiveTrue(@Param("author") User user, @Param("category") Category category,
                                              @Param("name") String name, Pageable pageable);

    Page<Test> findByAuthorAndTopicAndNameAndActiveTrue(@Param("author") User user, @Param("topic") Topic topic,
                                           @Param("name") String name, Pageable pageable);

    Page<Test> findByAuthorAndCategoryAndActiveTrue(@Param("author") User user, @Param("category") Category category,
                                       Pageable pageable);

    Page<Test> findByAuthorAndTopicAndActiveTrue(@Param("author") User user, @Param("topic") Topic topic, Pageable pageable);

    Page<Test> findByAuthorAndNameAndActiveTrue(@Param("author") User user, @Param("name") String name, Pageable pageable);

    Page<Test> findByAuthorAndActiveTrue(@Param("author") User user, Pageable pageable);

    Page<Test> findByCategoryAndTopicAndNameAndIdInAndActiveTrue(@Param("category") Category category, @Param("topic") Topic topic,
                                                    @Param("name") String name, @Param("ids") Collection<Long> ids,
                                                    Pageable pageable);

    Page<Test> findByCategoryAndTopicAndIdInAndActiveTrue(@Param("category") Category category, @Param("topic") Topic topic,
                                             @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByCategoryAndNameAndIdInAndActiveTrue(@Param("category") Category category, @Param("name") String name,
                                            @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByTopicAndNameAndIdInAndActiveTrue(@Param("topic") Topic topic, @Param("name") String name,
                                         @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByCategoryAndIdInAndActiveTrue(@Param("category") Category category, @Param("ids") Collection<Long> ids,
                                     Pageable pageable);

    Page<Test> findByTopicAndIdInAndActiveTrue(@Param("topic") Topic topic, @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByNameAndIdInAndActiveTrue(@Param("name") String name, @Param("ids") Collection<Long> ids, Pageable pageable);

    Page<Test> findByIdInAndActiveTrue(@Param("ids") Collection<Long> ids, Pageable pageable);

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
