package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;

import java.util.List;

public interface TeacherService {
    CourseDto createCourse(Long teacherId, CourseDto courseDto);
    CourseDto updateCourse(Long teacherId, Long courseId, CourseDto dto);
    String deleteCourse(Long teacherId, Long courseId);
    List<CourseDto> getAllCoursesByTeacher(Long teacherId);
    LessonDto addLessonToCourse(Long teacherId, Long courseId, LessonDto lessonDto);
    LessonDto updateLesson(Long teacherId, Long lessonId, LessonDto lessonDto);
    String deleteLesson(Long teacherId, Long lessonId);
}
