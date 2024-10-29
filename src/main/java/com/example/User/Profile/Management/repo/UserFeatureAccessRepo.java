package com.example.User.Profile.Management.repo;

import com.example.User.Profile.Management.entity.UserFeatureAccess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserFeatureAccessRepo extends JpaRepository<UserFeatureAccess,Long>{
    UserFeatureAccess findByUserProfileIdAndCarFeatureId(Long userProfileId, Long carFeatureId);
    List<UserFeatureAccess> findByUserProfileId(Long userProfileId);
    UserFeatureAccess save(UserFeatureAccess featureAccess);
}
