package id.ac.ui.cs.advprog.authentication.model;

import id.ac.ui.cs.advprog.authentication.enums.Role;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Data
@Entity
@Table(name = "users")
public class User {
    @Id
    @Getter
    @Setter
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String email;

    @Getter
    @Setter
    @Column(nullable = false)
    private String password;

    @Getter
    @Setter
    @Column(nullable = false, unique = true)
    private String username;

    @Getter
    @Setter
    private String bio;

    @Getter
    @Setter
    private String profilePicture;

    @Getter
    @Setter
    private Double balance;

    @Getter
    @Setter
    @Enumerated(EnumType.STRING)
    Role userRole = Role.ROLE_PEMBELI;
}