package ru.KostarevaAnastasia.NauJava.service;

import ru.KostarevaAnastasia.NauJava.models.User;

import java.util.List;
import java.util.Optional;

/**
 * Сервис для управления пользователями
 */
public interface UserService {
    /**
     * Находит пользователя по уникальному имени
     * @param name имя пользователя
     * @return Optional, содержащий найденного пользователя
     * или пустой Optional, если пользователь с таким именем не существует
     */
    Optional<User> getUsersByUsername(String name);

    /**
     * Регистрирует нового пользователя в системе
     * @param user объект пользователя для сохранения
     * @return сохранённый экземпляр пользователя
     */
    User addUser(User user);

    /**
     * Получает список всех пользователей приложения
     * @return список пользователей
     */
    List<User> getAllUsers();

    /**
     * Поиск пользователя по идентификатору
     * @param id идентификатор
     * @return пользователь Optional<User>
     */
    Optional<User> findById(Long id);

    /**
     * Сохранение пользователя
     * @param user пользователь
     * @return сохраненный пользователь
     */
    User save(User user);

    /**
     * Получает существующего пользователя по имени или создаёт нового, если не существует.
     * @param username имя пользователя
     * @return пользователь
     */
    User getOrCreateUser(String username);
}
