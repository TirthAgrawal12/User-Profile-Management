package com.example.User.Profile.Management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.User.Profile.Management.entity.UserProfile;

public interface UserProfileRepo extends JpaRepository<UserProfile,Long>{

}
