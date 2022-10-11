package com.project.courseservice.dto;

import com.project.courseservice.entity.GroupCourse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class GroupDTO {
    private long id;
    private byte groupNum;
    private String courseName;

    public GroupDTO(GroupCourse groupCourse) {
        this.groupNum = groupCourse.getGroupNum();
        this.id = groupCourse.getId();
        this.courseName = groupCourse.getCourse().getName();
    }
}
