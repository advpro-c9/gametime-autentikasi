package id.ac.ui.cs.advprog.authentication.controller;

import id.ac.ui.cs.advprog.authentication.model.User;
import id.ac.ui.cs.advprog.authentication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PutMapping("/edit")
    public User editProfile(@AuthenticationPrincipal UserDetails userDetails,
                            @RequestParam(required = false) String username,
                            @RequestParam(required = false) String bio,
                            @RequestParam(required = false) String profilePicture,
                            @RequestParam(required = false) String password) {
        User user = userService.findByEmail(userDetails.getUsername()).orElseThrow(() -> new RuntimeException("User not found"));
        return userService.updateProfile(user, username, bio, profilePicture, password);
    }
}