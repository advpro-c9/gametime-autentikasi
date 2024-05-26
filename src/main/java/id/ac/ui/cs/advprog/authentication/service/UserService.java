package id.ac.ui.cs.advprog.authentication.service;

import id.ac.ui.cs.advprog.authentication.model.User;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface UserService {
    CompletableFuture<Optional<User>> findByEmail(String email);

    CompletableFuture<Optional<User>> findByUsername(String username);

    CompletableFuture<User> updateProfile(User user, String newUsername, String newBio, String newProfilePicture, String newPassword);
}
