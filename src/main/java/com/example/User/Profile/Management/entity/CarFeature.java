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

@Entity
@Table(name = "car_features")
@Data
public class CarFeature {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long featureId;

    private String featureName;

    private boolean isSafetyFeature;

    @Enumerated(EnumType.STRING)
    private FeatureStatus status;

    @OneToMany(mappedBy = "carFeature", cascade = CascadeType.ALL)
    private List<UserFeatureAccess> userAccess;

    // Getters and setters
    // toString, equals, and hashCode methods
}

enum FeatureStatus {
    ENABLED,
    DISABLED
}

