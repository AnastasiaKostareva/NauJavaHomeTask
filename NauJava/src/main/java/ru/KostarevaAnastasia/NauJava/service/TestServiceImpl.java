package ru.KostarevaAnastasia.NauJava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.Test;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;

import java.util.List;

@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private TestRepository testRepository;

    @Autowired
    private QuestionToTestRepository questionToTestRepository;

    @Override
    public List<Test> findAll() {
        return testRepository.findAll();
    }

    @Override
    public Test findById(Long id) {
        return testRepository.findById(id).orElse(null);
    }

    @Override
    public Test save(Test test) {
        return testRepository.save(test);
    }

    @Override
    public Test findWithQuestions(Long testId) {
        return testRepository.findByIdWithCreatorAndQuestions(testId)
                .orElse(null);
    }

    @Override
    public long countQuestionsInTest(Long testId) {
        return questionToTestRepository.countByTestId(testId);
    }
}
