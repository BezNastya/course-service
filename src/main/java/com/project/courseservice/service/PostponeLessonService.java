package com.project.courseservice.service;

import com.project.courseservice.dto.PostponeLessonRequestDTO;
import com.project.courseservice.dto.PostponeLessonResponseDTO;
import com.project.courseservice.entity.Lesson;
import com.project.courseservice.entity.PostponeLesson;
import com.project.courseservice.entity.PostponeStatus;
import com.project.courseservice.entity.ScheduleDate;
import com.project.courseservice.repository.PostponeLessonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PostponeLessonService {

    @Autowired
    LessonService lessonService;

    @Autowired
    PostponeLessonRepository postponeLessonRepository;


    public void postponeLesson(PostponeLessonRequestDTO postponeLesson) {
        PostponeLesson postponeLessonRes = new PostponeLesson();
        Lesson lessonToPostpone = lessonService.findById(postponeLesson.getLessonId());
        postponeLessonRes.setCanceledLesson(lessonToPostpone);
        postponeLessonRes.setOldDate(lessonToPostpone.getDate().toString());
        postponeLessonRes.setTeachers(postponeLesson.getTeachers());
        postponeLessonRes.setNewDate(new ScheduleDate(postponeLesson.getDay(), postponeLesson.getOrder(), postponeLesson.getWeek()));
        postponeLessonRes.setDescription(postponeLesson.getDescription());
        postponeLessonRepository.deletePostponeLessonByCanceledLesson(lessonToPostpone);
        postponeLessonRepository.save(postponeLessonRes);
    }

    public List<PostponeLessonResponseDTO> getAllRequestsDTOs() {
        return getAllRequests().stream().map(x -> new PostponeLessonResponseDTO(x.getCanceledLesson()))
                .collect(Collectors.toList());
    }

    public List<PostponeLesson> getAllRequests() {
        return postponeLessonRepository.findAll();
    }

    public boolean approveRequest(long id) {
        Lesson lesson = lessonService.findById(id);
        PostponeLesson request = lesson.getPostponeLesson();
        if (Objects.nonNull(request)) {
            request.setStatus(PostponeStatus.APPROVED);
            postponeLessonRepository.save(request);
            Lesson canceled = request.getCanceledLesson();
            canceled.setDate(request.getNewDate());
            lessonService.saveLesson(canceled);
            return true;
        }
        return false;
    }


    public boolean declineRequest(long id) {
        Lesson lesson = lessonService.findById(id);
        PostponeLesson request = lesson.getPostponeLesson();
        if (Objects.nonNull(request)) {
            request.setStatus(PostponeStatus.DECLINED);
            postponeLessonRepository.save(request);
            Lesson canceled = request.getCanceledLesson();
            canceled.setDate(request.getNewDate());
            lessonService.saveLesson(canceled);
            return true;
        }
        return false;
    }


    public boolean deleteRequest(long id) {
        if (Objects.nonNull(lessonService.findById(id).getPostponeLesson())) {
            postponeLessonRepository.deleteById(lessonService.findById(id).getPostponeLesson().getLessonId());
            return true;
        }
        return false;
    }


    public PostponeLessonResponseDTO getPostponeRequestByLessonId(Long lessonId) {
        return new PostponeLessonResponseDTO(lessonService.findById(lessonId));
    }

    public List<PostponeLessonResponseDTO> getAllRequestsByGroupCourses(List<Long> groupCourseIds) {
        return getAllRequests().stream()
                .filter(x -> groupCourseIds.contains(x.getCanceledLesson().getGroupCourse().getId()))
                .map(x -> new PostponeLessonResponseDTO(x.getCanceledLesson()))
                .collect(Collectors.toList());
    }
}
