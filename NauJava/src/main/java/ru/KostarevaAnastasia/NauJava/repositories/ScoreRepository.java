package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Score;

import java.util.List;

/**
 * Репозиторий для сущности Score
 */
@RepositoryRestResource(path = "scores")
public interface ScoreRepository extends CrudRepository<Score, Long> {
    /**
     * Поиск всех результатов пользователя с заданным идентификатором
     * @param userId идентификатор пользователя
     * @return список результатов
     */
    List<Score> findByUserIdOrderByCreatedAtDesc(Long userId);
}
