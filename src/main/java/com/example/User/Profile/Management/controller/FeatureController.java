package com.example.User.Profile.Management.controller;

import com.example.User.Profile.Management.entity.CarFeature;
import com.example.User.Profile.Management.entity.UserFeatureAccess;
import com.example.User.Profile.Management.entity.UserProfile;
import com.example.User.Profile.Management.service.FeatureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class FeatureController {

    @Autowired
    private FeatureService featureService;

    @GetMapping("/users")
    public ResponseEntity<List<UserProfile>> listAllUsers(){
        return ResponseEntity.ok(featureService.getAllUsers());
    }

    @GetMapping("/{userId}/features")
    public ResponseEntity<?> listUserFeatures(@PathVariable Long userId) {
        // Retrieve features accessible to the user
        return ResponseEntity.ok(featureService.getUserFeatures(userId));
    }

    @PatchMapping("/{userId}/features/{featureId}")
    public ResponseEntity<String> manageFeature(@PathVariable Long userId, @PathVariable Long featureId, @RequestParam String status) {
        String response = featureService.manageFeatureAccess(userId, featureId, Boolean.parseBoolean(status));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveUser")
    public ResponseEntity<?> saveUser(@RequestBody UserProfile userProfile) {
        UserProfile response = featureService.saveUserProfile(userProfile);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveCarFeature")
    public ResponseEntity<?> savefeature(@RequestBody CarFeature carFeature) {
        CarFeature response = featureService.saveCarFeature(carFeature);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/saveUserFeatureAccess")
    public ResponseEntity<?> saveUserFeatureAccess(@RequestBody UserFeatureAccess featureAccess){
        String response=featureService.saveUserFeatureAccess(featureAccess);
        return ResponseEntity.ok(response);
    }
}
