package library.services;

import library.entity.Role;
import library.entity.User;
import library.exception.ResourceNotFoundException;
import library.repository.RoleRepository;
import library.repository.UserRepository;
import library.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testCreateUserWhenUsernameDoesNotExist() {
        User user = new User();
        user.setEmail("test123");
        user.setPassword("123456");

        Role role = new Role();
        role.setName("USER");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.empty());
        when(roleRepository.findRoleByName("USER")).thenReturn(Optional.of(role));
        when(passwordEncoder.encode(user.getPassword())).thenReturn("encoded");
        when(userRepository.save(user)).thenReturn(user);

        userService.createUser(user);

        verify(userRepository, times(1)).save(user);
        verify(roleRepository, times(1)).findRoleByName("USER");
        verify(passwordEncoder, times(1)).encode("123456");

        assertEquals(1, user.getRoles().size());
        assertTrue(user.getRoles().contains(role));
        assertEquals("encoded", user.getPassword());
    }

    @Test
    public void testCreateUserWhenUsernameExists() {
        User user = new User();
        user.setEmail("test123");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        // TODO: CHANGE
        assertThrows(ResourceNotFoundException.class, () -> userService.createUser(user));

        verify(userRepository, never()).save(any(User.class));
    }
}
