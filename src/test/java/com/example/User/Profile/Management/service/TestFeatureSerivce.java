package com.example.User.Profile.Management.service;

import com.example.User.Profile.Management.entity.CarFeature;
import com.example.User.Profile.Management.entity.UserFeatureAccess;
import com.example.User.Profile.Management.entity.UserProfile;
import com.example.User.Profile.Management.entity.UserType;
import com.example.User.Profile.Management.repo.CarFeatureRepo;
import com.example.User.Profile.Management.repo.FeatureChangeLogRepo;
import com.example.User.Profile.Management.repo.UserFeatureAccessRepo;
import com.example.User.Profile.Management.repo.UserProfileRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestFeatureSerivce {


    @InjectMocks
    private FeatureService featureService;

    @Mock
    private UserProfileRepo userProfileRepo;

    @Mock
    private CarFeatureRepo carFeatureRepo;

    @Mock
    private UserFeatureAccessRepo userFeatureAccessRepo;

    @Mock
    private FeatureChangeLogRepo featureChangeLogRepo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @InjectMocks
    private RedisTemplate template;

    @Test
    void testSaveUserProfile() {
        UserProfile userProfile = new UserProfile();
        userProfile.setId(1L);
        userProfile.setName("Vivek");

        template = new RedisTemplate<>();
        template.setKeySerializer(new StringRedisSerializer());
        template.setValueSerializer(new JdkSerializationRedisSerializer());

        List<UserProfile> list = new ArrayList<>();
        list.add(userProfile);
        template.opsForValue().set("Users", list, Duration.ofSeconds(30));

        when(userProfileRepo.save(any(UserProfile.class))).thenReturn(userProfile);
        when(template.opsForValue().get("Users")).thenReturn(list);

        UserProfile savedProfile = featureService.saveUserProfile(userProfile);

        assertNotNull(savedProfile);
        assertEquals("Vivek", savedProfile.getName());
    }

    @Test
    void testSaveCarFeature() {
        CarFeature carFeature = new CarFeature();
        carFeature.setId(1L);
        carFeature.setFeatureName("adas");

        when(carFeatureRepo.save(any(CarFeature.class))).thenReturn(carFeature);

        CarFeature savedFeature = featureService.saveCarFeature(carFeature);

        assertNotNull(savedFeature);
        assertEquals("adas", savedFeature.getFeatureName());

    }

    @Test
    void testSaveUserFeatureAccess() {
        UserFeatureAccess featureAccess = new UserFeatureAccess();
        featureAccess.setUserProfile(new UserProfile());
        featureAccess.setCarFeature(new CarFeature());

        when(userFeatureAccessRepo.findByUserProfileIdAndCarFeatureId(any(Long.class), any(Long.class)))
                .thenReturn(null); // Simulate no existing access

        String result = featureService.saveUserFeatureAccess(featureAccess);

        assertEquals("UserAccess added successfully", result);
    }

    @Test
    void testManageFeatureAccess() {
        Long userId = 1L;
        Long featureId = 1L;

        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        userProfile.setName("dhruv");
        userProfile.setUserRole(UserType.OWNER);

        CarFeature carFeature = new CarFeature();
        carFeature.setId(featureId);
        carFeature.setFeatureName("ADAS");
        carFeature.setIsSafetyFeature(true);

        UserFeatureAccess featureAccess = new UserFeatureAccess();
        featureAccess.setUserProfile(userProfile);
        featureAccess.setCarFeature(carFeature);
        featureAccess.setStatus("true");

        when(userProfileRepo.findById(userId)).thenReturn(Optional.of(userProfile));
        when(carFeatureRepo.findById(featureId)).thenReturn(Optional.of(carFeature));
        when(userFeatureAccessRepo.findByUserProfileIdAndCarFeatureId(userId, featureId)).thenReturn(featureAccess);

        String result = featureService.manageFeatureAccess(userId, featureId, false);

        assertEquals("Feature updated successfully", result);
    }

    @Test
    void testManageFeatureAccessUnauthorized() {
        Long userId = 1L;
        Long featureId = 1L;

        when(userFeatureAccessRepo.findByUserProfileIdAndCarFeatureId(userId, featureId)).thenReturn(null);

        String result = featureService.manageFeatureAccess(userId, featureId, false);

        assertEquals("Unauthorized access", result);
    }

    @Test
    void testManageFeatureAccessCannotDisableSafetyFeature() {
        Long userId = 1L;
        Long featureId = 1L;

        UserProfile userProfile = new UserProfile();
        userProfile.setId(userId);
        userProfile.setName("dhruv");
        userProfile.setUserRole(UserType.GUEST); // Not an owner

        CarFeature carFeature = new CarFeature();
        carFeature.setId(featureId);
        carFeature.setFeatureName("ADAS");
        carFeature.setIsSafetyFeature(true);

        UserFeatureAccess featureAccess = new UserFeatureAccess();
        featureAccess.setUserProfile(userProfile);
        featureAccess.setCarFeature(carFeature);

        when(userProfileRepo.findById(userId)).thenReturn(Optional.of(userProfile));
        when(carFeatureRepo.findById(featureId)).thenReturn(Optional.of(carFeature));
        when(userFeatureAccessRepo.findByUserProfileIdAndCarFeatureId(userId, featureId)).thenReturn(featureAccess);

        String result = featureService.manageFeatureAccess(userId, featureId, false);

        assertEquals("Cannot disable safety feature", result);
    }
}
