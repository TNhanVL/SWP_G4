package com.swp_project_g4.Repository;

import com.swp_project_g4.Model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    Optional<List<Notification>> findByLearnerID(int learnerID);
}
