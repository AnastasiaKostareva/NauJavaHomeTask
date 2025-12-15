package ru.KostarevaAnastasia.NauJava.service;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.Score;
import ru.KostarevaAnastasia.NauJava.models.Test;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.repositories.ScoreRepository;
import ru.KostarevaAnastasia.NauJava.repositories.TestRepository;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
public class ScoreServiceImpl implements ScoreService{
    private static final Logger log = LoggerFactory.getLogger(QuestionService.class);
    @Autowired
    private ScoreRepository scoreRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TestRepository testRepository;

    @Override
    public void saveScore(Long testId, String username, Integer scoreValue) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        Test test = testRepository.findById(testId)
                .orElseThrow(() -> new IllegalArgumentException("Test not found: " + testId));

        Score score = new Score();
        score.setUser(user);
        score.setTest(test);
        score.setScore(scoreValue);
        score.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        log.info("Сохранение результата пользователя {}: {}", score.getUser().toString(), score.getScore());
        scoreRepository.save(score);
    }

    @Override
    public List<Score> findByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("User not found: " + username));
        return scoreRepository.findByUserIdOrderByCreatedAtDesc(user.getId());
    }
}
