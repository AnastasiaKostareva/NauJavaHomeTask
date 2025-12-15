package ru.KostarevaAnastasia.NauJava.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import ru.KostarevaAnastasia.NauJava.models.User;

import java.util.List;
import java.util.Optional;

/**
 *  Репозиторий для сущности User
 */
@RepositoryRestResource(path = "users")
public interface UserRepository extends CrudRepository<User, Long> {
    /**
     * Поиск пользователя по имени
     * @param name имя пользователя
     * @return Optional<User> найденный пользователь
     */
    Optional<User> findByUsername(String name);

    /**
     * Поиск всех пользователей приложения
     * @return список пользователей
     */
    List<User> findAll();
}