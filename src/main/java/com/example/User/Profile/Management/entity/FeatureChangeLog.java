package com.example.User.Profile.Management.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@Table(name = "feature_change_log")
@Data
public class FeatureChangeLog implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long logId;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserProfile userProfile;

    @ManyToOne
    @JoinColumn(name = "feature_id", nullable = false)
    private CarFeature carFeature;

    private LocalDateTime changeTimestamp;


    private String oldStatus;


    private String newStatus;

    // Getters and setters
    // toString, equals, and hashCode methods
}

