package com.project.courseservice.controller;

import com.project.courseservice.dto.CourseDTO;
import com.project.courseservice.entity.Course;
import com.project.courseservice.exceptions.CourseAlreadyExistsException;
import com.project.courseservice.exceptions.CourseNotFoundException;
import com.project.courseservice.service.CourseService;
import com.project.courseservice.service.LessonService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @Autowired
    private CourseService courseService;
    @Autowired
    private LessonService lessonService;

    @GetMapping
    public List<CourseDTO> getAllCourses() {
        return courseService.findAll().stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/notGroups")
    public List<CourseDTO> getAllCoursesNotGroups(@RequestParam List<Long> groupIds) {
        return courseService.findAllCoursesNotGroups(groupIds).stream()
                .map(CourseDTO::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public List<Long> saveCourse(@RequestBody CourseDTO courseDto) {
        if (courseService.findCourseByName(courseDto.getName()).isEmpty()) {
            Course course = new Course();
            course.setName(courseDto.getName());
            courseService.saveCourse(course);
            logger.info("Added course with name {}", courseDto.getName());
            return courseService.saveGroupsForCourse(course, courseDto.getNumberOfGroups());
        } else {
            throw new CourseAlreadyExistsException(courseDto.getName());
        }
    }

    @DeleteMapping("/{courseId}")
    public void deleteCourse(@PathVariable long courseId) {
        logger.info("Deleting course with id{}", courseId);
        Optional<Course> courseOptional = courseService.findCourseById(courseId);
        if (courseOptional.isPresent()) {
            Course course = courseOptional.get();
            lessonService.deleteLessonsByGroupCourse_Course(course);
            courseService.deleteCourseWithAll(course);
        } else {
            throw new CourseNotFoundException(courseId);
        }
    }
}
