package com.project.courseservice.service;

import com.project.courseservice.entity.PostponeLesson;
import com.project.courseservice.entity.PostponeStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PostponeCleanupService {
    Logger logger = LoggerFactory.getLogger(PostponeCleanupService.class);

    @Autowired
    private PostponeLessonService postponeLessonService;

    @Scheduled(cron = "0 41 18 ? * SUN")
//    @Scheduled(fixedDelay = 604800000)
    public void cleanPostponeRepository() {
        LocalDate now = LocalDate.now();
        List<PostponeLesson> requests = postponeLessonService.getAllRequests();
        logger.warn("Today is {}", now);
        logger.warn("Requests we have {}", requests.size());
        List<Long> idsToDelete = new ArrayList<>();
        for (PostponeLesson r : requests) {
            LocalDate create = r.getCreated().toLocalDate();
            long between = Duration.between(create.atStartOfDay(), now.atStartOfDay()).toDays();
            if (between > 7 && !r.getStatus().equals(PostponeStatus.PENDING)) {
                idsToDelete.add(r.getCanceledLesson().getLessonId());
            }
        }
        logger.warn("Requests we have to delete {}", idsToDelete.size());
        for (Long id : idsToDelete) {
            postponeLessonService.deleteRequest(id);
        }
    }
}
