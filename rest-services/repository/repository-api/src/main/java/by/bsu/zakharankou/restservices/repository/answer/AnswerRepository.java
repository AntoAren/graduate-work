package by.bsu.zakharankou.restservices.repository.answer;

import by.bsu.zakharankou.restservices.model.answer.Answer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerRepository extends JpaRepository<Answer, Long> {
}
