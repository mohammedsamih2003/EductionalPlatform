package com.educationalplatform.controller;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.service.interfaces.CourseService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public ResponseEntity<List<CourseDto>> getAllCourses() {
        return ResponseEntity.ok(courseService.getAllCourses());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDto> getCourseById(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getCourseById(courseId));
    }

    @GetMapping("/{courseId}/lessons")
    public ResponseEntity<List<LessonDto>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getLessonsByCourse(courseId));
    }

    @GetMapping("/{courseId}/students/{studentId}/enrolled")
    public ResponseEntity<Boolean> isStudentEnrolled(
            @PathVariable Long studentId,
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(courseService.isStudentEnrolled(studentId, courseId));
    }

    @PutMapping("/{courseId}/teacher/{teacherId}")
    public ResponseEntity<CourseDto> assignTeacherToCourse(
            @PathVariable Long teacherId,
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(courseService.assignTeacherToCourse(teacherId, courseId));
    }
}
