package ru.KostarevaAnastasia.NauJava.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.dto.QuestionInSessionDto;
import ru.KostarevaAnastasia.NauJava.dto.TestSessionDto;
import ru.KostarevaAnastasia.NauJava.dto.OptionInSessionDto;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.models.Test;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionToTestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.OptionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TestSessionServiceImpl implements TestSessionService {
    @Autowired
    private QuestionToTestRepository questionToTestRepository;
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private TestRepository testRepository;

    @Override
    public TestSessionDto startTest(Long testId) {
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new EntityNotFoundException("Тест не найден"));

        List<QuestionToTest> questionToTestList = questionToTestRepository
                .findByTestIdOrderBySortingOrderAsc(testId);

        List<QuestionInSessionDto> questions = questionToTestList.stream()
                .map(qtt -> {
                    var question = qtt.getQuestion();
                    var options = optionRepository.findByQuestionId(question.getId())
                            .stream()
                            .map(opt -> new OptionInSessionDto(opt.getId(), opt.getText()))
                            .collect(Collectors.toList());
                    return new QuestionInSessionDto(
                            question.getId(),
                            question.getTextQuestion(),
                            question.getQuestionType(),
                            options
                    );
                })
                .collect(Collectors.toList());

        return new TestSessionDto(testId, test.getTitle(), questions);
    }
}