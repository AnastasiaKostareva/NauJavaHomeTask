package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Test;

import java.util.List;
import java.util.Optional;

/**
 * Репозиторий для сущности Test
 */
@RepositoryRestResource(path = "tests")
public interface TestRepository extends CrudRepository<Test, Long> {
    /**
     * Поиск всех тестов
     * @return список тестов
     */
    List<Test> findAll();

    /**
     * Поиск теста с создателем и вопросами по идентификатору
     * @param id идентификатор запрашиваемого теста
     * @return Optional<Test> тест с автором и вопросами
     */
    @Query("SELECT t FROM Test t LEFT JOIN FETCH t.questionToTests qt LEFT JOIN FETCH qt.question LEFT JOIN FETCH t.creator WHERE t.id = :id")
    Optional<Test> findByIdWithCreatorAndQuestions(@Param("id") Long id);
}