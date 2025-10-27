package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.EnrollmentDto;
import com.educationalplatform.dto.LessonDto;

import java.util.List;

public interface StudentService {

    EnrollmentDto enrollInCourse(Long studentId, Long courseId);

    List<CourseDto> getEnrolledCourses(Long studentId);

    List<LessonDto> getCourseLessons(Long studentId, Long courseId);

    List<CourseDto> getAllCourses(Long studentId);

    String unenrollFromCourse(Long studentId, Long courseId);
}
