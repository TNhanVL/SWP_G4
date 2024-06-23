package com.swp_project_g4.Service.model;

import com.swp_project_g4.Model.Instruct;
import com.swp_project_g4.Model.Notification;
import com.swp_project_g4.Repository.InstructRepository;
import com.swp_project_g4.Repository.NotificationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class NotificationService {
    @Autowired
    private NotificationRepository notificationRepository;

    public List<Notification> findAllByLearnerId(int learnerId) {
        return notificationRepository.findAllByLearnerId(learnerId);
    }
}
