package com.example.user.entity;
import jakarta.persistence.*;

@Entity
@tabel(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name ;
    private String email;
    private String password;
    private String role;
}
