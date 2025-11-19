package ru.KostarevaAnastasia.NauJava.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.KostarevaAnastasia.NauJava.models.QuestionToTest;
import ru.KostarevaAnastasia.NauJava.repositories.custom.CustomQuestionToTestRepository;

import java.util.List;

@RestController
@RequestMapping("/custom/question-to-test")
public class QuestionToTestController {
    @Autowired
    private CustomQuestionToTestRepository questionToTestRepository;

    @GetMapping("/by-test-and-order-range")
    public List<QuestionToTest> findQuestionToTestByTestIDAndSortingOrderBetween(@RequestParam(required = false) Long testId,
                                                                                 @RequestParam(required = false) Integer minOrder,
                                                                                 @RequestParam(required = false) Integer maxOrder)
    {
        if (testId == null || minOrder == null || maxOrder == null) {
            throw new IllegalArgumentException("parameters must not be null");
        }
        List<QuestionToTest> answer = questionToTestRepository.findQuestionToTestByTestIDAndSortingOrderBetween(testId, minOrder, maxOrder);
        if (answer.isEmpty()) {
            throw new ResourceNotFoundException("No answers found for testId " + testId + " and orders '" + minOrder + " " + maxOrder + "'");
        }
        return answer;
    }
}
