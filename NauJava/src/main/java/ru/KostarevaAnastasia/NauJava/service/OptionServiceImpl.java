package ru.KostarevaAnastasia.NauJava.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.dto.OptionCreateDto;
import ru.KostarevaAnastasia.NauJava.models.Option;
import ru.KostarevaAnastasia.NauJava.models.Question;
import ru.KostarevaAnastasia.NauJava.models.Role;
import ru.KostarevaAnastasia.NauJava.repositories.OptionRepository;
import ru.KostarevaAnastasia.NauJava.repositories.QuestionRepository;

@Service
@Transactional
public class OptionServiceImpl implements OptionService {
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    private OptionRepository optionRepository;
    @Autowired
    private QuestionRepository questionRepository;
    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('CREATOR') or hasRole('ADMIN')")
    public Option createOption(OptionCreateDto dto, String currentUsername) {
        Question question = questionRepository.findById(dto.questionId())
                .orElseThrow(() -> new EntityNotFoundException("Question not found"));

        if (!userService.hasRole(currentUsername, Role.ADMIN)) {
            if (!question.getAuthor().getUsername().equals(currentUsername)) {
                throw new AccessDeniedException("You can only edit your own questions");
            }
        }

        Option option = new Option();
        option.setText(dto.text());
        option.setCorrect(dto.correct());
        option.setQuestion(question);
        log.info("Создание варианта ответа: {}", option.getText());
        return optionRepository.save(option);
    }
}