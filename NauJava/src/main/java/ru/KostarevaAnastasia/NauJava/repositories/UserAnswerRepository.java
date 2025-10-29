package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.UserAnswer;
import ru.KostarevaAnastasia.NauJava.models.UserAnswerId;

import java.util.List;

@RepositoryRestResource(path = "answers")
public interface UserAnswerRepository extends CrudRepository<UserAnswer, UserAnswerId> {
    @Query("SELECT ua FROM UserAnswer ua " +
            "JOIN Question q ON ua.questionID = q.id " +
            "WHERE ua.userID = :userId AND q.theme = :theme")
    List<UserAnswer> findByUserIdAndQuestionTheme(@Param("userId") Long userId, @Param("theme") String theme);
}