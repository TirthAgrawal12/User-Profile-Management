package com.example.User.Profile.Management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "car_features")
@Data
public class CarFeature implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String featureName;

    private Boolean isSafetyFeature;

    @OneToMany(mappedBy = "carFeature", cascade = CascadeType.ALL)
    private List<UserFeatureAccess> userAccess;

    // Getters and setters
    // toString, equals, and hashCode methods
}

