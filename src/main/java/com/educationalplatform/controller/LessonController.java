package com.educationalplatform.controller;

import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.service.interfaces.LessonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lessons")
public class LessonController {

    private final LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    @PostMapping("/course/{courseId}")
    public ResponseEntity<LessonDto> createLesson(
            @PathVariable Long courseId,
            @RequestBody LessonDto dto
    ) {
        return ResponseEntity.ok(lessonService.createLesson(courseId, dto));
    }

    @PutMapping("/{lessonId}")
    public ResponseEntity<LessonDto> updateLesson(
            @PathVariable Long lessonId,
            @RequestBody LessonDto dto
    ) {
        return ResponseEntity.ok(lessonService.updateLesson(lessonId, dto));
    }

    @DeleteMapping("/{lessonId}")
    public ResponseEntity<String> deleteLesson(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.deleteLesson(lessonId));
    }

    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonDto> getLessonById(@PathVariable Long lessonId) {
        return ResponseEntity.ok(lessonService.getLessonById(lessonId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<LessonDto>> getLessonsByCourse(@PathVariable Long courseId) {
        return ResponseEntity.ok(lessonService.getLessonsByCourse(courseId));
    }
}
