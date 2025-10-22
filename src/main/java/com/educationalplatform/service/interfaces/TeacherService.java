package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;

import java.util.List;

public interface TeacherService {
    CourseDto createCourse(Long teacherId, CourseDto dto);
    CourseDto updateCourse(Long teacherId, Long courseId, CourseDto dto);
    String deleteCourse(Long teacherId, Long courseId);
    List<CourseDto> getAllCoursesByTeacher(Long teacherId);
    LessonDto addLessonToCourse(Long teacherId, Long courseId, LessonDto dto);
    LessonDto updateLesson(Long teacherId, Long lessonId, LessonDto dto);
    String deleteLesson(Long teacherId, Long lessonId);
}
