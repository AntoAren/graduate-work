package by.bsu.zakharankou.restservices.repository.record;

import by.bsu.zakharankou.restservices.model.record.Record;
import by.bsu.zakharankou.restservices.model.test.Test;
import by.bsu.zakharankou.restservices.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {

    List<Record> findByUserAndTest(User user, Test test);

    @Transactional
    long deleteByTestAndUser(Test test, User user);
}
