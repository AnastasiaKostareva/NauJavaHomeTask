package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import ru.KostarevaAnastasia.NauJava.models.User;

/**
 *  Репозиторий для сущности User
 */
public interface UserRepository extends CrudRepository<User, Long> {
}