package com.project.courseservice.dto;

import com.project.courseservice.entity.Lesson;
import com.project.courseservice.entity.LessonOrder;
import com.project.courseservice.entity.LessonType;
import com.project.courseservice.entity.WeekDay;
import lombok.*;

import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class LessonResponseDTO {

    private long lessonId;
    private LessonType type;
    private int week;
    private String place;
    private LessonOrder time;
    private WeekDay day;
    private String courseName;
    private byte groupNum;
    private long groupId;
    private Long postponeLesson;
    private String postponeStatus;

    public LessonResponseDTO(Lesson lesson) {
        this.lessonId = lesson.getLessonId();
        this.courseName = lesson.getGroupCourse().getCourse().getName();
        this.day = lesson.getDate().getDayOfTheWeek();
        this.groupNum = lesson.getGroupCourse().getGroupNum();
        this.time = lesson.getDate().getLessonOrder();
        this.place = lesson.getPlace();
        this.type = lesson.getType();
        this.groupId = lesson.getGroupCourse().getId();
        this.week = lesson.getDate().getWeek();
        if (Objects.nonNull(lesson.getPostponeLesson())) {
            this.postponeLesson = lesson.getPostponeLesson().getLessonId();
            this.postponeStatus = lesson.getPostponeLesson().getStatus().toString();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonResponseDTO lesson = (LessonResponseDTO) o;
        return lessonId == lesson.getLessonId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(lessonId);
    }
}
