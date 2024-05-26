package id.ac.ui.cs.advprog.authentication.service;

import id.ac.ui.cs.advprog.authentication.model.User;

import java.util.Optional;

public interface UserService {
    public Optional<User> findByEmail(String email);

    public Optional<User> findByUsername(String username);

    public User updateProfile(User user, String newUsername, String newBio, String newProfilePicture, String newPassword);
}
