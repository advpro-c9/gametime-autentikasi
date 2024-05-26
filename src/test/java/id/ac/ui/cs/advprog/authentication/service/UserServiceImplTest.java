package id.ac.ui.cs.advprog.authentication.service;

import id.ac.ui.cs.advprog.authentication.model.User;
import id.ac.ui.cs.advprog.authentication.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    private final UUID id = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(id);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setBio("Bio");
        user.setProfilePicture("profile.jpg");
        user.setPassword("hashedpassword");
    }

    @Test
    void updateProfile_success() {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(any(String.class))).thenReturn("newhashedpassword");

        User updatedUser = userService.updateProfile(user, "newusername", "New bio", "newprofile.jpg", "newpassword");

        assertEquals("newusername", updatedUser.getUsername());
        assertEquals("New bio", updatedUser.getBio());
        assertEquals("newprofile.jpg", updatedUser.getProfilePicture());
        assertEquals("newhashedpassword", updatedUser.getPassword());
    }

    @Test
    void findByEmail_success() {
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findByEmail("test@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void findByUsername_success() {
        when(userRepository.findByUsername("testuser")).thenReturn(Optional.of(user));
        Optional<User> foundUser = userService.findByUsername("testuser");

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }
}
