package com.project.courseservice.service;
import com.project.courseservice.dto.GroupDTO;
import com.project.courseservice.entity.Course;
import com.project.courseservice.entity.GroupCourse;
import com.project.courseservice.exceptions.CourseNotFoundException;
import com.project.courseservice.repository.CourseRepository;
import com.project.courseservice.repository.GroupCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class CourseService {

    private final CourseRepository courseRepository;
    private final GroupCourseRepository groupRepository;

    @Autowired
    public CourseService(CourseRepository courseRepository, GroupCourseRepository groupCourseRepository, LessonService lessonService) {
        this.courseRepository = courseRepository;
        this.groupRepository = groupCourseRepository;
    }

    
    public Course saveCourse(Course course) {
        return courseRepository.save(course);
    }

    public void deleteCourseById(Long courseId) {
        courseRepository.deleteById(courseId);
    }

    
    public void updateCourseName(String newName, Long toUpdateId) {
        Course courseToUpdate = findCourseById(toUpdateId)
                .orElseThrow(() -> new CourseNotFoundException(toUpdateId));
        courseToUpdate.setName(newName);
        courseRepository.save(courseToUpdate);
    }

    
    public Optional<Course> findCourseById(long id) {
        return courseRepository.findById(id);
    }

    
    public Optional<Course> findCourseByName(String name) {
        return courseRepository.findCourseByName(name);
    }

    
    public List<Course> findAll() {
        return courseRepository.findAll();
    }

    
    public List<GroupCourse> findAllGroupsForCourse(long courseId) {
        Course course = courseRepository.findById(courseId).orElseThrow(() -> new CourseNotFoundException(courseId));
        return groupRepository.findAllGroupsCourseByCourse(course);
    }

    
    public GroupCourse findGroupById(Long id) {
        return groupRepository.findById(id).orElse(null);
    }


    
    public List<Long> saveGroupsForCourse(Course course, byte numberOfGroups) {
        Set<GroupCourse> res = new HashSet<>();
        for (byte i = 1; i <= numberOfGroups; i++) {
            GroupCourse group = new GroupCourse(i);
            group.setCourse(course);
            res.add(group);
            groupRepository.save(group);
        }
        course.setGroups(res);
        courseRepository.save(course);
        return res.stream().map(GroupCourse::getId).collect(Collectors.toList());
    }

    
    public void deleteGroupById(Long groupId) {
        groupRepository.deleteById(groupId);
    }


    
    public void deleteGroupCoursesByCourse(Course course) {
        groupRepository.deleteGroupCoursesByCourse(course);
    }

    
    public void deleteCourseWithAll(Course course) {
        deleteGroupCoursesByCourse(course);
        deleteCourseById(course.getId());
    }

    public List<GroupCourse> sortGroupCoursesByGroupNum(List<GroupCourse> groupCourseList) {
        groupCourseList.sort(Comparator.naturalOrder());
        return groupCourseList;
    }

    public List<Course> findAllCoursesNotGroups(List<Long> groupIds) {
        List<Course> courses = groupRepository.findAll()
                .stream().filter(x -> groupIds.contains(x.getId()))
                .map(GroupCourse::getCourse)
                .collect(Collectors.toList());
        return findAll().stream().filter(x -> !courses.contains(x)).collect(Collectors.toList());
    }

    public List<GroupDTO> findAllGroupsByIds(List<Long> groupIds) {
        return groupRepository.findAll().stream()
                .filter(x -> groupIds.contains(x.getId()))
                .map(GroupDTO::new)
                .collect(Collectors.toList());
    }
}
