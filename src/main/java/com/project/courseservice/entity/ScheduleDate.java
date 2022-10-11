package com.project.courseservice.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Min;

@Embeddable
@Setter @Getter
public class ScheduleDate implements Comparable<ScheduleDate>{

    @Enumerated(EnumType.ORDINAL)
    WeekDay dayOfTheWeek;

    @Enumerated(EnumType.ORDINAL)
    LessonOrder lessonOrder;

    @Min(0)
    @JsonProperty("week")
    int week;

    public ScheduleDate(WeekDay day, LessonOrder lesson, int week) {
        this.dayOfTheWeek = day;
        this.lessonOrder = lesson;
        this.week = week;
    }

    public ScheduleDate() {}

    @Override
    public String toString() {
        return String.format("Week %d Day %s Lesson %s", week, dayOfTheWeek.getDay(), lessonOrder.getOrder());
    }

    @Override
    public int compareTo(ScheduleDate that) {
        int compareWeeks = Integer.compare(this.week,that.week);
        if (compareWeeks != 0)
            return compareWeeks;
        int compareDays = this.dayOfTheWeek.compareTo(that.dayOfTheWeek);
        if (compareDays != 0)
            return compareDays;
        return this.getLessonOrder().compareTo(that.getLessonOrder());
    }
}

