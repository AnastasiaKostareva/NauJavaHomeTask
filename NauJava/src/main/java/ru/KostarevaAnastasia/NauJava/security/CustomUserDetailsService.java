package ru.KostarevaAnastasia.NauJava.security;

import org.springframework.context.annotation.Profile;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.KostarevaAnastasia.NauJava.models.User;
import ru.KostarevaAnastasia.NauJava.service.UserService;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@Profile("!test")
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    public CustomUserDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("Загрузка пользователя: " + username);
        Optional<User> appUserOptional = userService.getUsersByUsername(username);
        if (appUserOptional.isPresent()) {
            User appUser = appUserOptional.get();
            return new
                    org.springframework.security.core.userdetails.User(
                    appUser.getUsername(), appUser.getPassword(),
                    mapRoles(appUser));
        }
        else
        {
            throw new UsernameNotFoundException("user not found");
        }
    }

    private Collection<GrantedAuthority> mapRoles(User appUser)
    {
        return List.of(new SimpleGrantedAuthority("ROLE_" + appUser.getRole().name()));
    }
}
