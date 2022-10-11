package com.project.courseservice.dto;

import com.project.courseservice.entity.Lesson;
import com.project.courseservice.entity.LessonOrder;
import com.project.courseservice.entity.WeekDay;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PostponeLessonResponseDTO {
    private long lessonId;
    private int week;
    private WeekDay day;
    private LessonOrder order;
    private String description;
    private String courseName;
    private String oldDate;
    private String teachers;
    private String status;

    public PostponeLessonResponseDTO(Lesson lesson) {
        lessonId = lesson.getLessonId();
        if (Objects.nonNull(lesson.getPostponeLesson())) {
            week = lesson.getPostponeLesson().getNewDate().getWeek();
            day = lesson.getPostponeLesson().getNewDate().getDayOfTheWeek();
            order = lesson.getPostponeLesson().getNewDate().getLessonOrder();
            description = lesson.getPostponeLesson().getDescription();
            oldDate = lesson.getPostponeLesson().getOldDate();
            teachers = lesson.getPostponeLesson().getTeachers();
            status = lesson.getPostponeLesson().getStatus().toString();
        } else {
            week = lesson.getDate().getWeek();
            day = lesson.getDate().getDayOfTheWeek();
            description = "";
            order = lesson.getDate().getLessonOrder();
            oldDate = lesson.getDate().toString();
        }
        courseName = lesson.getGroupCourse().getCourse().getName();
    }
}

