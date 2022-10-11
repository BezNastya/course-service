package com.project.courseservice.controller;

import com.project.courseservice.dto.PostponeLessonRequestDTO;
import com.project.courseservice.dto.PostponeLessonResponseDTO;
import com.project.courseservice.service.PostponeLessonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/api/requests")
public class PostponeLessonController {

    @Autowired
    private PostponeLessonService postponeLessonService;

    @PostMapping
    public void addNewPostponeRequest(@RequestBody PostponeLessonRequestDTO requestDTO) {
        postponeLessonService.postponeLesson(requestDTO);
    }

    @GetMapping("/byLesson")
    public PostponeLessonResponseDTO getRequestByLessonId(@RequestParam Long lessonId) {
        return postponeLessonService.getPostponeRequestByLessonId(lessonId);
    }

    @GetMapping
    public List<PostponeLessonResponseDTO> getAllRequestsForGroupCourse(@RequestParam(required = false) List<Long> groupCourseIds) {
        if (!CollectionUtils.isEmpty(groupCourseIds)) {
            return postponeLessonService.getAllRequestsByGroupCourses(groupCourseIds);
        }
        return postponeLessonService.getAllRequestsDTOs();
    }

    @PutMapping("/approve")
    public boolean approveRequest(@RequestParam Long lessonId) {
        return postponeLessonService.approveRequest(lessonId);
    }

    @PutMapping("/decline")
    public boolean declineRequest(@RequestParam Long lessonId) {
        return postponeLessonService.declineRequest(lessonId);
    }

    @DeleteMapping("/{lessonId}")
    public boolean deleteRequest(@PathVariable Long lessonId) {
        return postponeLessonService.deleteRequest(lessonId);
    }
}
