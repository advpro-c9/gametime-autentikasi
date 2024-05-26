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
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserServiceImpl();
        userService.userRepository = userRepository;
        userService.passwordEncoder = passwordEncoder;

        user = new User();
        user.setId(UUID.randomUUID());
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setBio("Bio");
        user.setProfilePicture("profile.jpg");
    }

    @Test
    void findByEmail_success() throws Exception {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        CompletableFuture<Optional<User>> foundUserFuture = userService.findByEmail("test@example.com");
        Optional<User> foundUser = foundUserFuture.get();

        assertTrue(foundUser.isPresent());
        assertEquals("testuser", foundUser.get().getUsername());
    }

    @Test
    void findByUsername_success() throws Exception {
        when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(user));

        CompletableFuture<Optional<User>> foundUserFuture = userService.findByUsername("testuser");
        Optional<User> foundUser = foundUserFuture.get();

        assertTrue(foundUser.isPresent());
        assertEquals("test@example.com", foundUser.get().getEmail());
    }

    @Test
    void updateProfile_success() throws Exception {
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(passwordEncoder.encode(anyString())).thenReturn("hashednewpassword");

        CompletableFuture<User> updatedUserFuture = userService.updateProfile(user, "newusername", "New bio", "newprofile.jpg", "newpassword");
        User updatedUser = updatedUserFuture.get();

        assertEquals("newusername", updatedUser.getUsername());
        assertEquals("New bio", updatedUser.getBio());
        assertEquals("newprofile.jpg", updatedUser.getProfilePicture());
        assertEquals("hashednewpassword", updatedUser.getPassword());
    }
}
