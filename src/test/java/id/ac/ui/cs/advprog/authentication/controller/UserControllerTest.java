package id.ac.ui.cs.advprog.authentication.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import id.ac.ui.cs.advprog.authentication.model.User;
import id.ac.ui.cs.advprog.authentication.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.Optional;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
public class UserControllerTest {
    private final UUID id = UUID.randomUUID();

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        user = new User();
        user.setId(id);
        user.setEmail("test@example.com");
        user.setUsername("testuser");
        user.setBio("Bio");
        user.setProfilePicture("profile.jpg");
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void editProfile_success() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.of(user));
        when(userService.updateProfile(any(User.class), anyString(), anyString(), anyString(), anyString())).thenAnswer(invocation -> {
            User userToUpdate = invocation.getArgument(0);
            String newUsername = invocation.getArgument(1);
            String newBio = invocation.getArgument(2);
            String newProfilePicture = invocation.getArgument(3);
            String newPassword = invocation.getArgument(4);

            if (newUsername != null) userToUpdate.setUsername(newUsername);
            if (newBio != null) userToUpdate.setBio(newBio);
            if (newProfilePicture != null) userToUpdate.setProfilePicture(newProfilePicture);
            if (newPassword != null) userToUpdate.setPassword("hashednewpassword"); // Simulate hashing
            return userToUpdate;
        });

        mockMvc.perform(MockMvcRequestBuilders.put("/api/profile/edit")
                        .param("username", "newusername")
                        .param("bio", "New bio")
                        .param("profilePicture", "newprofile.jpg")
                        .param("password", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("newusername"))
                .andExpect(jsonPath("$.bio").value("New bio"))
                .andExpect(jsonPath("$.profilePicture").value("newprofile.jpg"));
    }

    @Test
    @WithMockUser(username = "test@example.com")
    void editProfile_userNotFound() throws Exception {
        when(userService.findByEmail(anyString())).thenReturn(Optional.empty());

        mockMvc.perform(MockMvcRequestBuilders.put("/api/profile/edit")
                        .param("username", "newusername"))
                .andExpect(status().isNotFound());
    }
}
