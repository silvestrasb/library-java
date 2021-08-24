package library.service;

import library.entity.User;
import library.exception.EmailAlreadyExistsException;
import library.exception.ResourceNotFoundException;
import library.exception.UserAlreadyExistsException;
import library.exception.UserNotFoundException;
import library.repository.RoleRepository;
import library.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class UserService implements UserDetailsService {


    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    private final PasswordEncoder encoder;

    public UserService(RoleRepository roleRepository, UserRepository userRepository, PasswordEncoder encoder) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findUser(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public User createUser(User user) {
        if (userRepository.findUserByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException(user.getUsername());
        }
        user.setRoles(Set.of(roleRepository.findRoleByName("USER").get()));
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public User updateUser(User user){
        return userRepository.save(user);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepository.findUserByEmail(email).orElseThrow(() -> new UsernameNotFoundException(email));
    }
}
