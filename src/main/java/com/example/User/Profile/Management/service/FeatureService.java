package com.example.User.Profile.Management.service;

import com.example.User.Profile.Management.entity.*;
import com.example.User.Profile.Management.repo.CarFeatureRepo;
import com.example.User.Profile.Management.repo.FeatureChangeLogRepo;
import com.example.User.Profile.Management.repo.UserFeatureAccessRepo;
import com.example.User.Profile.Management.repo.UserProfileRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class FeatureService {

    @Autowired
    private UserProfileRepo userProfileRepository;
    @Autowired
    private CarFeatureRepo carFeatureRepository;
    @Autowired
    private UserFeatureAccessRepo userFeatureAccessRepository;
    @Autowired
    private FeatureChangeLogRepo featureChangeLogRepository;
    @Autowired
    private RedisTemplate template;

    public List<CarFeature> getUserFeatures(Long userId) {

        HashMap<Long,List<CarFeature>> list;

        if (null == template.opsForValue().get("UserFeatureAccess")){
            list = new HashMap<>();
        }else{
            list = (HashMap<Long,List<CarFeature>>) template.opsForValue().get("UserFeatureAccess");
        }
        List<CarFeature> carList = list.get(userId);

        return carList;
    }

    public List<UserProfile> getAllUsers() {
        return (List<UserProfile>) template.opsForValue().get("Users");
    }

    public String manageFeatureAccess(Long userId, Long featureId, boolean status) {
        // Validate user access
        UserFeatureAccess access = userFeatureAccessRepository.findByUserProfileIdAndCarFeatureId(userId, featureId);
        if (access == null || access.getUserProfile().getName().isEmpty()) {
            return "Unauthorized access";
        }


        Optional<UserProfile> userProfile = userProfileRepository.findById(userId);

        CarFeature feature = carFeatureRepository.findById(featureId).orElseThrow();
        if (feature.getIsSafetyFeature() && !userProfile.get().getUserRole().equals(UserType.OWNER)) {
            return "Cannot disable safety feature";
        }

        UserFeatureAccess featureAccess = userFeatureAccessRepository.findByUserProfileIdAndCarFeatureId(userId, featureId);
        String oldStatus  = featureAccess.getStatus();
        featureAccess.setStatus(String.valueOf(status));
        //feature.setStatus(status);
        userFeatureAccessRepository.save(featureAccess);

        logFeatureChange(userId, featureId,oldStatus, String.valueOf(status));
        return "Feature updated successfully";
    }

    private void logFeatureChange(Long userId, Long featureId, String oldStatus, String newStatus) {
        FeatureChangeLog log = new FeatureChangeLog();
        log.setUserProfile(userProfileRepository.findById(userId).get());
        log.setCarFeature(carFeatureRepository.findById(featureId).get());
        log.setNewStatus(String.valueOf(oldStatus));
        log.setOldStatus(newStatus);
        featureChangeLogRepository.save(log);
    }

    public UserProfile saveUserProfile(UserProfile userProfile) {

        userProfileRepository.save(userProfile);

        List<UserProfile> list;
        if (null == template.opsForValue().get("Users")){
            list = new ArrayList<>();
        }else{
            list = (List<UserProfile>) template.opsForValue().get("Users");
        }
        list.add(userProfile);
        template.opsForValue().set("Users",list, Duration.ofSeconds(600));
        return userProfile;
    }

    public CarFeature saveCarFeature(CarFeature carFeature) {

        carFeatureRepository.save(carFeature);
        List<CarFeature> list;
        if (null == template.opsForValue().get("CarFeature")){
            list = new ArrayList<>();
        }else{
            list = (List<CarFeature>) template.opsForValue().get("CarFeature");
        }
        list.add(carFeature);
        template.opsForValue().set("CarFeature",list, Duration.ofSeconds(120));

        return carFeature;
    }

    public String saveUserFeatureAccess(UserFeatureAccess featureAccess) {
        UserFeatureAccess access = userFeatureAccessRepository.findByUserProfileIdAndCarFeatureId(featureAccess.getUserProfile().getId(), featureAccess.getCarFeature().getId());
        if (null != access){
            return "UserAccess already available.";
        }
        userFeatureAccessRepository.save(featureAccess);

        HashMap<Long,List<CarFeature>> list;
        List<CarFeature> carList = new ArrayList<>();
        if (null == template.opsForValue().get("UserFeatureAccess")){
            list = new HashMap<>();
        }else{

            list = (HashMap<Long, List<CarFeature>>) template.opsForValue().get("UserFeatureAccess");
        }

        if (null != list.get(featureAccess.getUserProfile().getId())) {
            carList = list.get(featureAccess.getUserProfile().getId());
        }
        carList.add(featureAccess.getCarFeature());
        list.put(featureAccess.getUserProfile().getId(), carList);
        template.opsForValue().set("UserFeatureAccess",list, Duration.ofSeconds(600));

        return "UserAccess added successfully";
    }

}
