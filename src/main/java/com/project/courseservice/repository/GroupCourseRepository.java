package com.project.courseservice.repository;

import com.project.courseservice.entity.Course;
import com.project.courseservice.entity.GroupCourse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupCourseRepository extends JpaRepository<GroupCourse, Long> {

    @Query(value = "SELECT u FROM GroupCourse u WHERE u.course = :course")
    List<GroupCourse> findAllGroupsCourseByCourse(@Param(value = "course") Course course);

    GroupCourse findGroupCourseByCourseAndGroupNum(Course courseId, byte group);

    void deleteGroupCoursesByCourse(Course course);

}
