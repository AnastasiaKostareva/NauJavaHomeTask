package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Option;

import java.util.List;

/**
 *Репозиторий для сущности Option
 */
@RepositoryRestResource(path = "options")
public interface OptionRepository extends CrudRepository<Option, Long> {
    /**
     *Возвращает все варианты ответов, связанные с указанным вопросом.
     * @param questionId идентификатор вопроса
     * @return список вариантов ответов
     */
    List<Option> findByQuestionId(Long questionId);
}