package com.educationalplatform.controller;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.EnrollmentDto;
import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.service.interfaces.StudentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/students")
public class StudentController {

    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping("/{studentId}/courses/{courseId}/enroll")
    public ResponseEntity<EnrollmentDto> enrollInCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return ResponseEntity.ok(studentService.enrollInCourse(studentId, courseId));
    }

    @GetMapping("/{studentId}/courses/enrolled")
    public ResponseEntity<List<CourseDto>> getEnrolledCourses(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(studentService.getEnrolledCourses(studentId));
    }

    @GetMapping("/{studentId}/courses/{courseId}/lessons")
    public ResponseEntity<List<LessonDto>> getCourseLessons(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return ResponseEntity.ok(studentService.getCourseLessons(studentId, courseId));
    }

    @DeleteMapping("/{studentId}/courses/{courseId}/unenroll")
    public ResponseEntity<String> unenrollFromCourse(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {

        return ResponseEntity.ok(studentService.unenrollFromCourse(studentId, courseId));
    }

    @GetMapping("/{studentId}/courses")
    public ResponseEntity<List<CourseDto>> getAllCourses(
            @PathVariable Long studentId) {

        return ResponseEntity.ok(studentService.getAllCourses(studentId));
    }
}
