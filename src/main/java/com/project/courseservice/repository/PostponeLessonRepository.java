package com.project.courseservice.repository;

import com.project.courseservice.entity.Lesson;
import com.project.courseservice.entity.PostponeLesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface PostponeLessonRepository extends JpaRepository<PostponeLesson, Long> {

    @Transactional
    void deletePostponeLessonByCanceledLesson(final Lesson lesson);

}
