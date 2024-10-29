package com.example.User.Profile.Management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.User.Profile.Management.entity.FeatureChangeLog;
import org.springframework.stereotype.Repository;

@Repository
public interface FeatureChangeLogRepo extends JpaRepository<FeatureChangeLog, Long>{

}
