package com.example.User.Profile.Management.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.User.Profile.Management.entity.CarFeature;
import org.springframework.stereotype.Repository;

@Repository
public interface CarFeatureRepo extends JpaRepository<CarFeature, Long>{


}
