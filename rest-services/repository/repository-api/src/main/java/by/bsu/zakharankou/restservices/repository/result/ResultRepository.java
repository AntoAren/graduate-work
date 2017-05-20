package by.bsu.zakharankou.restservices.repository.result;

import by.bsu.zakharankou.restservices.model.result.Result;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResultRepository extends JpaRepository<Result, Long> {

    List<Result> findByUser(User user);

    List<Result> findByUserAndTest(User user, Test test);

    List<Result> findByTest(Test test);
}
