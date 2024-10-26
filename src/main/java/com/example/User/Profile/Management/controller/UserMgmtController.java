package com.example.User.Profile.Management.controller;

import com.example.User.Profile.Management.service.UserMgmtServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("/userMgmt")
public class UserMgmtController {

    @Autowired
    private UserMgmtServiceImpl userMgmtService;

    @GetMapping("/features/{userid}")
    public ResponseEntity<?> getUsersFeatures(@PathVariable("userid") int userid){
        return userMgmtService.getUsersFeatures(userid);
    }
}
