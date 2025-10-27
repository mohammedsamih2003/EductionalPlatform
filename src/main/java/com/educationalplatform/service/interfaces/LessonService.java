package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.LessonDto;

import java.util.List;

public interface LessonService {

    LessonDto createLesson(Long courseId, LessonDto dto);

    LessonDto updateLesson(Long lessonId, LessonDto dto);

    String deleteLesson(Long lessonId);

    LessonDto getLessonById(Long id);

    List<LessonDto> getLessonsByCourse(Long courseId);
}
