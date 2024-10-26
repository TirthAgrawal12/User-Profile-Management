package com.example.User.Profile.Management.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFeatureRepo extends JpaRepository<AccessInfoEntity, Integer> {

    public AccessInfoEntity findByUserId(int userid);

}
