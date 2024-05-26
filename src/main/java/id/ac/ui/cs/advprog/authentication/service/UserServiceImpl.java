package id.ac.ui.cs.advprog.authentication.service;

import id.ac.ui.cs.advprog.authentication.model.User;
import id.ac.ui.cs.advprog.authentication.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User updateProfile(User user, String newUsername, String newBio, String newProfilePicture, String newPassword) {
        if (newUsername != null) user.setUsername(newUsername);
        if (newBio != null) user.setBio(newBio);
        if (newProfilePicture != null) user.setProfilePicture(newProfilePicture);
        if (newPassword != null) user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }
}