package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Question;

import java.util.List;


/**
 * Репозиторий для сущности Question
 */
@RepositoryRestResource(path = "questions")
public interface QuestionRepository extends CrudRepository<Question, Long> {
    /**
     * Поиск списока всех вопросов
     * @return список вопросов
     */
    List<Question> findAll();

    /**
     * Поиск всех вопросов с вариантами ответов
     * @return список вопросов
     */
    @Query("SELECT q FROM Question q LEFT JOIN FETCH q.options ORDER BY q.id")
    List<Question> findAllWithOptions();
}

