package com.project.courseservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
public class GroupCourse  implements Comparable<GroupCourse> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private Course course;

    private byte groupNum;

    @OneToMany(mappedBy="lessonId", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    @JsonIgnore
    @ToString.Exclude
    Set<Lesson> lessons;

    public GroupCourse(byte groupNum){
        this.course = new Course();
        this.groupNum = groupNum;
    }

    public GroupCourse(Course course, byte groupNum){
        this.course = course;
        this.groupNum = groupNum;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GroupCourse that = (GroupCourse) o;
        return Objects.equals(this.id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(course, groupNum);
    }

    @Override
    public int compareTo(GroupCourse o) {
        return Integer.compare(groupNum, o.getGroupNum());
    }
}
