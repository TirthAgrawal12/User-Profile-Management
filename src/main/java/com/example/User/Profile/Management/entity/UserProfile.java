package com.example.User.Profile.Management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

import org.hibernate.annotations.GeneratorType;
import org.springframework.data.redis.core.RedisHash;

@Entity
@Table(name = "user_profiles")
@Data
public class UserProfile implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserType userRole;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<UserFeatureAccess> featureAccess;


    // Getters and setters
    // toString, equals, and hashCode methods
}

