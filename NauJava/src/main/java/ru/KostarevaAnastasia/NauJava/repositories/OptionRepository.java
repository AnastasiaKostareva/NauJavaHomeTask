package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.Option;

import java.util.List;

@RepositoryRestResource(path = "options")
public interface OptionRepository extends CrudRepository<Option, Long> {
    List<Option> findByQuestionID(Long questionId);
}