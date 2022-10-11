package com.project.courseservice.dto;

import com.project.courseservice.entity.LessonOrder;
import com.project.courseservice.entity.WeekDay;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PostponeLessonRequestDTO {
    private long lessonId;
    private int week;
    private WeekDay day;
    private LessonOrder order;
    private String description;
    private String teachers;
}
