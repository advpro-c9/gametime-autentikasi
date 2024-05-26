package id.ac.ui.cs.advprog.authentication.controller;

import id.ac.ui.cs.advprog.authentication.model.User;
import id.ac.ui.cs.advprog.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/edit")
    public CompletableFuture<ResponseEntity<User>> editProfile(@AuthenticationPrincipal UserDetails userDetails,
                                                               @RequestParam(required = false) String username,
                                                               @RequestParam(required = false) String bio,
                                                               @RequestParam(required = false) String profilePicture,
                                                               @RequestParam(required = false) String password) {
        return userService.findByEmail(userDetails.getUsername())
                .thenCompose(userOpt -> userOpt
                        .map(user -> userService.updateProfile(user, username, bio, profilePicture, password)
                                .thenApply(ResponseEntity::ok))
                        .orElseGet(() -> CompletableFuture.completedFuture(ResponseEntity.notFound().build())));
    }
}