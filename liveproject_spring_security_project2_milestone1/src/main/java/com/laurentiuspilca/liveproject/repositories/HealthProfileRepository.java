package com.laurentiuspilca.liveproject.repositories;

import com.laurentiuspilca.liveproject.entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HealthProfileRepository extends JpaRepository<HealthProfile, Integer> {

  Optional<HealthProfile> findHealthProfileByUsername(String username);
}
