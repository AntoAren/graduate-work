package by.bsu.zakharankou.restservices.service.serviceimpl.util;

import by.bsu.zakharankou.restservices.model.answer.Answer;
import by.bsu.zakharankou.restservices.model.question.Question;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class TestUtils {

    public static List<Question> getRandomlyQuestions(Set<Question> questions, Long questionsNumber) {
        List<Question> result = new ArrayList<>();
        List<Question> questionList = new ArrayList<>(questions);
        Random random = new Random();
        Question question;

        while (result.size() < questionsNumber) {
            question = questionList.get(random.nextInt(questionsNumber.intValue()));
            if (!result.contains(question)) {
                question.setAnswers(clearCorrectAnswers(question.getAnswers()));
                result.add(question);
            }
        }

        return result;
    }

    private static Set<Answer> clearCorrectAnswers(Set<Answer> answers) {
        for (Answer answer : answers) {
            if (answer.isCorrect()) {
                answer.setCorrect(false);
            }
        }
        return answers;
    }
}
