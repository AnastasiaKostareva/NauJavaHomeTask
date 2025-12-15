package ru.KostarevaAnastasia.NauJava.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTestId;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;

@Service
public class QuestionToTestServiceImpl implements QuestionToTestService{
    @Autowired
    private QuestionToTestRepository questionToTestRepository;

    @Override
    public QuestionToTest save(QuestionToTest questionToTest) {
        if (questionToTest.getId() == null) {
            var id = new QuestionToTestId();
            id.setTestId(questionToTest.getTest().getId());
            id.setQuestionId(questionToTest.getQuestion().getId());
            questionToTest.setId(id);
        }
        return questionToTestRepository.save(questionToTest);
    }

    @Override
    public long countByTestId(Long testId) {
        return questionToTestRepository.countByTestId(testId);
    }

    @Override
    @Transactional
    public void deleteByTestIdAndQuestionId(Long testId, Long questionId) {
        questionToTestRepository.deleteByTestIdAndQuestionId(testId, questionId);
    }
}
