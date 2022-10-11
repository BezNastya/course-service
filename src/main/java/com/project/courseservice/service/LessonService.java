package com.project.courseservice.service;

import com.project.courseservice.advice.TrackExecutionTime;
import com.project.courseservice.advice.TrackParameters;
import com.project.courseservice.dto.LessonRequestDTO;
import com.project.courseservice.dto.LessonResponseDTO;
import com.project.courseservice.entity.*;
import com.project.courseservice.exceptions.LessonNotFoundException;
import com.project.courseservice.repository.GroupCourseRepository;
import com.project.courseservice.repository.LessonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@Transactional
public class LessonService {

    private final LessonRepository lessonRepository;

    private final GroupCourseRepository groupCourseRepository;

    @Autowired
    public LessonService(LessonRepository lessonRepository, GroupCourseRepository groupCourseRepository) {
        this.lessonRepository = lessonRepository;
        this.groupCourseRepository = groupCourseRepository;
    }

    public Lesson saveLesson(Lesson lesson) {
        return lessonRepository.save(lesson);
    }

    public Lesson findById(long id) {
        return lessonRepository.findById(id).orElseThrow(() -> new LessonNotFoundException(id));
    }

    @TrackParameters
    public List<Lesson> findLessonsByGroupCourse(GroupCourse groupCourse) {
        return lessonRepository.findLessonsByGroupCourse(groupCourse);
    }


    @TrackExecutionTime
    public List<Lesson> findAllLessons() {
        return lessonRepository.findAll();
    }


    public void deleteLessonsByGroupCourse_Course(Course course) {
        lessonRepository.deleteLessonsByGroupCourse_Course(course);
    }

    public void deleteById(long lessonId) {
        findById(lessonId);
        lessonRepository.deleteById(lessonId);
    }

    public List<Lesson> getAllLessonsForWeek(int week) {
        return lessonRepository.findLessonsByDate_Week(week);
    }

    public void saveAllLessons(LessonRequestDTO lessonDTO, GroupCourse groupCourse) {
        List<Lesson> allLessons = new ArrayList<>(lessonDTO.getWeekEnd()-lessonDTO.getWeekStart());
        for (int i = lessonDTO.getWeekStart(); i < lessonDTO.getWeekEnd(); i++) {
            allLessons.add(new Lesson(lessonDTO, groupCourse, i));
        }
        lessonRepository.saveAll(allLessons);
    }

    public void editAllLessons(LessonRequestDTO lessonRequestDTO, GroupCourse groupCourse) {
        Lesson lesson = findById(lessonRequestDTO.getId());
        List<Lesson> lessonsToEdit = findLessonsByGroupCourse(groupCourse);
        for (Lesson lesson1 : lessonsToEdit) {
            boolean theSameDate = lesson1.getDate().getLessonOrder().getOrder().equals(lesson.getDate().getLessonOrder().getOrder())
                    && lesson1.getDate().getDayOfTheWeek().getDay().equals(lesson.getDate().getDayOfTheWeek().getDay());
            if (theSameDate)
                lessonRepository.deleteById(lesson1.getLessonId());
        }
        for (int i = lessonRequestDTO.getWeekStart(); i <= lessonRequestDTO.getWeekEnd(); i++) {
            Lesson lesson2 = new Lesson(lessonRequestDTO, groupCourse, i);
            lessonRepository.save(lesson2);
        }
    }

    public List<LessonResponseDTO> getAllRequestsByGroupCourses(List<Long> groupCourseIds) {
        List<LessonResponseDTO> res = new ArrayList<>();
        for (Long groupId : groupCourseIds) {
            Optional<GroupCourse> groupCourse = groupCourseRepository.findById(groupId);
            if (groupCourse.isPresent()) {
                GroupCourse group = groupCourse.get();
                res.addAll(findLessonsByGroupCourse(group).stream()
                        .map(LessonResponseDTO::new)
                        .collect(Collectors.toList()));
            }
        }
        return res;
    }
}
