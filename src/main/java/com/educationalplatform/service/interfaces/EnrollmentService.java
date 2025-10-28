package com.educationalplatform.service.interfaces;

import com.educationalplatform.dto.EnrollmentDto;
import java.util.List;

public interface EnrollmentService {

    EnrollmentDto enrollStudent(Long studentId, Long courseId);

    void unenrollStudent(Long studentId, Long courseId);

    List<EnrollmentDto> getEnrollmentsByStudent(Long studentId);

    List<EnrollmentDto> getEnrollmentsByCourse(Long courseId);

    boolean checkEnrollment(Long studentId, Long courseId);
}
