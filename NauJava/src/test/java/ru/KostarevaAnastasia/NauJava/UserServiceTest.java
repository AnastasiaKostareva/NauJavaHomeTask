package ru.KostarevaAnastasia.NauJava;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.KostarevaAnastasia.NauJava.models.Role;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.repositories.UserRepository;
import ru.KostarevaAnastasia.NauJava.service.UserServiceImpl;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository mockUserRepo;
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {
        mockUserRepo = Mockito.mock(UserRepository.class);
        userService = new UserServiceImpl(mockUserRepo);
    }

    @Test
    void shouldReturnEmptyWhenUserNotFoundByUsername()
    {
        String name = "alex";
        when(mockUserRepo.findByUsername(name)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), userService.getUsersByUsername(name));
    }

    @Test
    void shouldReturnUserWhenFoundByUsername()
    {
        String name = "alex";
        User user = new User();
        user.setUsername(name);
        user.setRole(Role.USER);
        when(mockUserRepo.findByUsername(name)).thenReturn(Optional.of(user));
        Assertions.assertEquals(Optional.of(user), userService.getUsersByUsername(name));
    }

    @Test
    void shouldReturnEmptyWhenUsernameIsNull()
    {
        String name = null;
        when(mockUserRepo.findByUsername(name)).thenReturn(Optional.empty());
        Assertions.assertEquals(Optional.empty(), userService.getUsersByUsername(name));
    }

    @Test
    void shouldCallRepositoryExactlyOnce() {
        String name = "alex";
        when(mockUserRepo.findByUsername(name)).thenReturn(Optional.empty());

        userService.getUsersByUsername(name);

        verify(mockUserRepo, times(1)).findByUsername(name);
    }
}
