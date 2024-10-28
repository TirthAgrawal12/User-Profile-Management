package com.example.User.Profile.Management.service;

import com.example.User.Profile.Management.repo.UserFeatureRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class UserMgmtServiceImpl implements UserMgmtService{

    @Autowired
    private UserFeatureRepo userFeatureRepo;

    @Override
    public ResponseEntity<?> getUsersFeatures(int id) {

        userFeatureRepo.findByUserId(id);

    }
}
