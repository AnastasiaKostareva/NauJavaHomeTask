package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.KostarevaAnastasia.NauJava.models.Test;

/**
 * Репозиторий для сущности Test
 */
public interface TestRepository extends CrudRepository<Test, Long> {
}