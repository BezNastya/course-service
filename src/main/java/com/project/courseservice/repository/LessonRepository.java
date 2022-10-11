package com.project.courseservice.repository;

import com.project.courseservice.entity.Course;
import com.project.courseservice.entity.GroupCourse;
import com.project.courseservice.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LessonRepository extends JpaRepository<Lesson,Long> {

    List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse);

    void deleteLessonsByGroupCourse_Course(Course course);

    List<Lesson> findLessonsByDate_Week(int week);

}
