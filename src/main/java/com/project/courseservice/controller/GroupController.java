package com.project.courseservice.controller;

import com.project.courseservice.dto.GroupDTO;
import com.project.courseservice.service.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    @Autowired
    private CourseService courseService;

    @GetMapping
    public List<GroupDTO> getGroupsByCourse(@RequestParam long courseId) {
        return courseService.findAllGroupsForCourse(courseId)
                .stream().map(GroupDTO::new).collect(Collectors.toList());
    }

    @GetMapping("/byIds")
    public List<GroupDTO> getGroupsByIds(@RequestParam List<Long> ids) {
        return courseService.findAllGroupsByIds(ids);
    }
}
