package com.project.courseservice.controller;

import com.project.courseservice.dto.LessonRequestDTO;
import com.project.courseservice.dto.LessonResponseDTO;
import com.project.courseservice.entity.GroupCourse;
import com.project.courseservice.service.CourseService;
import com.project.courseservice.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/api/lessons")
public class LessonController {
    private final Logger logger = LoggerFactory.getLogger(LessonController.class);

    @Autowired
    private LessonService lessonService;
    @Autowired
    private CourseService courseService;

    @GetMapping("/byWeek")
    public List<LessonResponseDTO> getAllLessonsForWeek(@RequestParam int week) {
        return lessonService.getAllLessonsForWeek(week).stream()
                .map(LessonResponseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping
    public List<LessonResponseDTO> getAllRequestsForGroupCourse(@RequestParam List<Long> groupCourseIds) {
        return lessonService.getAllRequestsByGroupCourses(groupCourseIds);
    }

    @GetMapping("/{id}")
    public LessonResponseDTO getLessonById(@PathVariable long id) {
        return new LessonResponseDTO(lessonService.findById(id));
    }

    @PostMapping
    public void addLessonsForGroupCourse(@RequestBody LessonRequestDTO lessonDTO) {
        GroupCourse groupCourse = courseService.findGroupById(lessonDTO.getGroupId());
        logger.info("Adding new lessons for group {}", lessonDTO.getGroupId());
        lessonService.saveAllLessons(lessonDTO, groupCourse);
    }

    @PutMapping
    public void editLessons(@RequestBody LessonRequestDTO lessonRequestDTO) {
        GroupCourse groupCourse = courseService.findGroupById(lessonRequestDTO.getGroupId());
        lessonService.editAllLessons(lessonRequestDTO, groupCourse);
    }

    @DeleteMapping("/{lessonId}")
    public void deleteLessons(@PathVariable long lessonId) {
        logger.info("Deleting lesson with id {}", lessonId);
        lessonService.deleteById(lessonId);
    }

}
