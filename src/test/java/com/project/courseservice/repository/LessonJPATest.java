package com.project.courseservice.repository;

import com.project.courseservice.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@DataJpaTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LessonJPATest {

    @Autowired
    CourseRepository courseRepository;
    @Autowired
    GroupCourseRepository groupCourseRepository;
    @Autowired
    LessonRepository lessonRepository;

    private static final List<Course> COURSES = Collections.singletonList(
            new Course("Algebra")
    );

    private static final List<GroupCourse> GROUPS = Collections.singletonList(
            new GroupCourse(COURSES.get(0), (byte) 1)
    );

    private static final List<Lesson> LESSONS = Arrays.asList(
            new Lesson(LessonType.PRACTICE, "303", new ScheduleDate(WeekDay.MONDAY, LessonOrder.SECOND, 2), GROUPS.get(0)),
            new Lesson(LessonType.SEMINAR, "302", new ScheduleDate(WeekDay.TUESDAY, LessonOrder.THIRD, 3), GROUPS.get(0)),
            new Lesson(LessonType.LECTURE, "301", new ScheduleDate(WeekDay.WEDNESDAY, LessonOrder.FORTH, 4), GROUPS.get(0))
    );

    @BeforeAll
    void initDb() {
        courseRepository.saveAll(COURSES);
        groupCourseRepository.saveAll(GROUPS);
        lessonRepository.saveAllAndFlush(LESSONS);
    }

    @Test
    void shouldReturnEmpty_whenEmptyExistingGroup() {
        GroupCourse groupCourse = new GroupCourse(COURSES.get(0), (byte) 1);
        groupCourseRepository.save(groupCourse);
        assertEquals(Collections.EMPTY_LIST, lessonRepository.findLessonsByGroupCourse(groupCourse));
    }

    @Test
    void shouldReturnEmpty_whenWeekWithNoLessons() {
        assertEquals(Collections.EMPTY_LIST, lessonRepository.findLessonsByDate_Week(15));
    }

    @Test
    void shouldReturnEmptyOptional_whenNonExistingId(){
        Optional<Lesson> lesson = lessonRepository.findById((long) 8);
        assertFalse(lesson.isPresent());
    }

    @Test
    void shouldDeleteExistingLesson_whenExistingId(){
        List<Lesson> expected = lessonRepository.findAll();
        lessonRepository.deleteById(expected.get(0).getLessonId());
        expected.remove(0);
        List<Lesson> actual = lessonRepository.findAll();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void shouldDeleteAllLessons_whenExistingCourse(){
        lessonRepository.deleteLessonsByGroupCourse_Course(COURSES.get(0));
        assertTrue(lessonRepository.findAll().isEmpty());
    }

    @Test
    void shouldDeleteNoLessons_whenNonExistingCourse(){
        List<Lesson> expected = lessonRepository.findAll();
        Course course = new Course("C2");
        courseRepository.save(course);
        lessonRepository.deleteLessonsByGroupCourse_Course(course);
        List<Lesson> actual = lessonRepository.findAll();
        assertEquals(expected.size(), actual.size());
        assertEquals(expected, actual);
    }

    @Test
    void shouldAdd_whenValidLesson(){
        List<Lesson> expected = lessonRepository.findAll();
        Lesson lesson = new Lesson(LessonType.LAB, "300", new ScheduleDate(WeekDay.THURSDAY, LessonOrder.FIRST, 1), GROUPS.get(0));
        lessonRepository.save(lesson);
        List<Lesson> actual = lessonRepository.findAll();
        assertEquals(expected.size() + 1, actual.size());
        expected.add(lesson);
        assertEquals(expected, actual);
    }

    @Test
    void shouldReturnEmpty_whenAllGroupsDeleted() {
        lessonRepository.deleteAll();
        assertTrue(lessonRepository.findAll().isEmpty());
    }
    @Test
    void shouldReturnTrueWhenRepoIsEmpty() {
        List<Lesson> lessons = any();
        assertNull(lessons);
    }
}
