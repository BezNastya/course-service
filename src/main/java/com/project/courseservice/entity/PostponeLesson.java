package com.project.courseservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.sql.Date;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PostponeLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long lessonId;

    //Коментарі
    private String description;

    //Предмет, що перенесено
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CANCELED_LESSON", nullable = false)
    @JsonIgnore
    @ToString.Exclude
    private Lesson canceledLesson;

    private String oldDate;

    @Valid
    @NotNull
    @Embedded
    private ScheduleDate newDate;

    @Enumerated(EnumType.STRING)
    private PostponeStatus status = PostponeStatus.PENDING;

    @CreationTimestamp
    private Date created;

    private String teachers;

    public PostponeLesson(Lesson lesson, ScheduleDate newDate){
        this.canceledLesson = lesson;
        this.newDate = newDate;
        this.oldDate = canceledLesson.getDate().toString();
    }

    public PostponeLesson(Lesson lesson, ScheduleDate newDate, String description){
        this.canceledLesson = lesson;
        this.newDate = newDate;
        this.description = description;
        this.oldDate = canceledLesson.getDate().toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PostponeLesson that = (PostponeLesson) o;
        return canceledLesson.getLessonId() == that.canceledLesson.getLessonId();
    }

    @Override
    public int hashCode() {
        return Long.hashCode(canceledLesson.getLessonId());
    }
}
