package id.ac.ui.cs.advprog.authentication.service;

import id.ac.ui.cs.advprog.authentication.model.User;
import id.ac.ui.cs.advprog.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    PasswordEncoder passwordEncoder;

    @Async
    public CompletableFuture<Optional<User>> findByEmail(String email) {
        return CompletableFuture.completedFuture(userRepository.findByEmail(email));
    }

    @Async
    public CompletableFuture<Optional<User>> findByUsername(String username) {
        return CompletableFuture.completedFuture(userRepository.findByUsername(username));
    }

    @Async
    public CompletableFuture<User> updateProfile(User user, String newUsername, String newBio, String newProfilePicture, String newPassword) {
        if (newUsername != null) user.setUsername(newUsername);
        if (newBio != null) user.setBio(newBio);
        if (newProfilePicture != null) user.setProfilePicture(newProfilePicture);
        if (newPassword != null) user.setPassword(passwordEncoder.encode(newPassword));
        return CompletableFuture.completedFuture(userRepository.save(user));
    }
}