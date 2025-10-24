package com.educationalplatform.controller;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.service.interfaces.TeacherService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/teachers")
public class TeacherController {

    private final TeacherService teacherService;

    public TeacherController(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @PostMapping("/{teacherId}/courses")
    public ResponseEntity<CourseDto> createCourse(
            @PathVariable Long teacherId,
            @RequestBody CourseDto courseDto
    ) {
        return ResponseEntity.ok(teacherService.createCourse(teacherId, courseDto));
    }

    @PutMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<CourseDto> updateCourse(
            @PathVariable Long teacherId,
            @PathVariable Long courseId,
            @RequestBody CourseDto courseDto
    ) {
        return ResponseEntity.ok(teacherService.updateCourse(teacherId, courseId, courseDto));
    }

    @DeleteMapping("/{teacherId}/courses/{courseId}")
    public ResponseEntity<String> deleteCourse(
            @PathVariable Long teacherId,
            @PathVariable Long courseId
    ) {
        return ResponseEntity.ok(teacherService.deleteCourse(teacherId, courseId));
    }

    @GetMapping("/{teacherId}/courses")
    public ResponseEntity<List<CourseDto>> getAllCoursesByTeacher(
            @PathVariable Long teacherId
    ) {
        return ResponseEntity.ok(teacherService.getAllCoursesByTeacher(teacherId));
    }


    @PostMapping("/{teacherId}/courses/{courseId}/lessons")
    public ResponseEntity<LessonDto> addLessonToCourse(
            @PathVariable Long teacherId,
            @PathVariable Long courseId,
            @RequestBody LessonDto lessonDto
    ) {
        return ResponseEntity.ok(teacherService.addLessonToCourse(teacherId, courseId, lessonDto));
    }

    @PutMapping("/{teacherId}/lessons/{lessonId}")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable Long teacherId,
            @PathVariable Long lessonId,
            @RequestBody LessonDto lessonDto
    ) {
        return ResponseEntity.ok(teacherService.updateLesson(teacherId, lessonId, lessonDto));
    }

    @DeleteMapping("/{teacherId}/lessons/{lessonId}")
    public ResponseEntity<String> deleteLesson(
            @PathVariable Long teacherId,
            @PathVariable Long lessonId
    ) {
        return ResponseEntity.ok(teacherService.deleteLesson(teacherId, lessonId));
    }
}
