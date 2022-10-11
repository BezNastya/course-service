package com.project.courseservice.dto;

import com.project.courseservice.entity.Lesson;
import com.project.courseservice.entity.LessonOrder;
import com.project.courseservice.entity.LessonType;
import com.project.courseservice.entity.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@AllArgsConstructor
@Getter
@Setter
public class LessonRequestDTO {
    private long id;
    private LessonType type;
    private String place;
    private LessonOrder time;
    private WeekDay day;
    private long groupId;
    private int weekStart;
    private int weekEnd;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LessonRequestDTO lesson = (LessonRequestDTO) o;
        return type.equals(lesson.type) && Objects.equals(place, lesson.place) && Objects.equals(time, lesson.time)
                && Objects.equals(day, lesson.day) && Objects.equals(groupId, lesson.groupId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, place, time, day, groupId);
    }
}
