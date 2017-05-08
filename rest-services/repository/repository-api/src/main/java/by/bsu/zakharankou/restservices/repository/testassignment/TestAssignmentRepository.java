package by.bsu.zakharankou.restservices.repository.testassignment;

import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.testassignment.TestAssignment;
import by.bsu.zakharankou.restservices.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TestAssignmentRepository extends JpaRepository<TestAssignment, Long> {

    List<TestAssignment> findByUser(@Param("user") User user);

    List<TestAssignment> findByUserAndTest(@Param("user") User user, @Param("test") Test test);
}
