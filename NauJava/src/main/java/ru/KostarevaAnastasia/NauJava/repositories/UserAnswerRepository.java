package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.UserAnswer;
import ru.KostarevaAnastasia.NauJava.models.UserAnswerId;

import java.util.List;

/**
 * Репозиторий для сущности UserAnswer с составным ключом UserAnswerId
 */
@RepositoryRestResource(path = "answers")
public interface UserAnswerRepository extends CrudRepository<UserAnswer, UserAnswerId> {
    /**
     * Находит ответы пользователя по идентификатору пользователя и теме вопроса
     * @param userId идентификатор пользователя
     * @param theme тема вопроса
     * @return список сущностей UserAnswer
     */
    @Query("SELECT ua FROM UserAnswer ua " +
            "WHERE ua.user.id = :userId AND ua.question.theme = :theme")
    List<UserAnswer> findByUserIdAndQuestionTheme(@Param("userId") Long userId, @Param("theme") String theme);
}