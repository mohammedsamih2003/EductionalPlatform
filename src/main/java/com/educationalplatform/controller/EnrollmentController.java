package com.educationalplatform.controller;

import com.educationalplatform.dto.EnrollmentDto;
import com.educationalplatform.service.interfaces.EnrollmentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/enrollments")
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping("/{studentId}/{courseId}")
    public ResponseEntity<EnrollmentDto> enrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        EnrollmentDto dto = enrollmentService.enrollStudent(studentId, courseId);
        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{studentId}/{courseId}")
    public ResponseEntity<String> unenrollStudent(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        enrollmentService.unenrollStudent(studentId, courseId);
        return ResponseEntity.ok("Unenrolled successfully");
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentsByStudent(
            @PathVariable Long studentId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByStudent(studentId));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<List<EnrollmentDto>> getEnrollmentsByCourse(
            @PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.getEnrollmentsByCourse(courseId));
    }

    @GetMapping("/check/{studentId}/{courseId}")
    public ResponseEntity<Boolean> checkEnrollment(
            @PathVariable Long studentId,
            @PathVariable Long courseId) {
        return ResponseEntity.ok(enrollmentService.checkEnrollment(studentId, courseId));
    }
}
