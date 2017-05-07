package by.bsu.zakharankou.restservices.repository.question;

import by.bsu.zakharankou.restservices.model.question.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

}
