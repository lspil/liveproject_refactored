package com.laurentiuspilca.liveproject.repositories;

import com.laurentiuspilca.liveproject.entities.HealthMetric;
import com.laurentiuspilca.liveproject.entities.HealthProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HealthMetricRepository extends JpaRepository<HealthMetric, Integer> {

  @Query("SELECT h FROM HealthMetric h WHERE h.profile.username=?#{authentication.principal.claims['user_name']}")
  List<HealthMetric> findHealthMetricHistory();

  @Query("DELETE FROM HealthMetric h WHERE h.profile=:profile")
  @Modifying
  void deleteAllForUser(HealthProfile profile);
}
