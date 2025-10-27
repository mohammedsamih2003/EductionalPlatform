package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;

import java.util.List;

public interface CourseService {

    List<CourseDto> getAllCourses();

    CourseDto getCourseById(Long id);

    List<LessonDto> getLessonsByCourse(Long courseId);

    boolean isStudentEnrolled(Long studentId, Long courseId);

    CourseDto assignTeacherToCourse(Long teacherId, Long courseId);
}
