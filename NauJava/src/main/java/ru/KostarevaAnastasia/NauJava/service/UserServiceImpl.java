package ru.KostarevaAnastasia.NauJava.service;

import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> getUsersByUsername(String name) {
        return userRepository.findByUsername(name);
    }

    @Override
    public User addUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers()
    {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getOrCreateUser(String username) {
        final String finalUsername;
        if (username == null || username.trim().isEmpty()) {
            finalUsername = "anonymous";
        } else {
            finalUsername = username;
        }
        return userRepository.findByUsername(finalUsername)
                .orElseGet(() -> {
                    User user = new User();
                    user.setUsername(finalUsername);
                    return userRepository.save(user);
                });
    }
}
