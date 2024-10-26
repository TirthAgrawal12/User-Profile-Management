package com.example.User.Profile.Management.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;

import java.util.List;

import org.hibernate.annotations.GeneratorType;

@Entity
@Table(name = "user_profiles")
@Data
public class UserProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    private String name;

    @Enumerated(EnumType.STRING)
    private UserType userRole;

    @OneToMany(mappedBy = "userProfile", cascade = CascadeType.ALL)
    private List<UserFeatureAccess> featureAccess;

    // Getters and setters
    // toString, equals, and hashCode methods
}

enum UserType {
    OWNER,
    FAMILY_MEMBER,
    GUEST
}

