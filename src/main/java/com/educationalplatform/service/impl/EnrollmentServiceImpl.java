package com.educationalplatform.service.impl;

import com.educationalplatform.dto.EnrollmentDto;
import com.educationalplatform.entity.Course;
import com.educationalplatform.entity.Enrollment;
import com.educationalplatform.entity.User;
import com.educationalplatform.repositories.CourseRepository;
import com.educationalplatform.repositories.EnrollmentRepository;
import com.educationalplatform.repositories.UserRepository;
import com.educationalplatform.service.interfaces.EnrollmentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {

    private final EnrollmentRepository enrollmentRepository;
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository, UserRepository userRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EnrollmentDto enrollStudent(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId)) {
            throw new RuntimeException("Student already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);

        Enrollment saved = enrollmentRepository.save(enrollment);

        return modelMapper.map(saved, EnrollmentDto.class);
    }

    @Override
    public void unenrollStudent(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = enrollmentRepository
                .findByStudentAndCourse(student,course)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollmentRepository.delete(enrollment);
    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByStudent(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        return enrollmentRepository.findByStudent(student)
                .stream()
                .map(e -> modelMapper.map(e, EnrollmentDto.class))
                .collect(Collectors.toList());

    }

    @Override
    public List<EnrollmentDto> getEnrollmentsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return enrollmentRepository.findByCourseId(courseId)
                .stream()
                .map(e -> modelMapper.map(e, EnrollmentDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public boolean checkEnrollment(Long studentId, Long courseId) {
        return enrollmentRepository.existsByStudentIdAndCourseId(studentId, courseId);
    }
}
