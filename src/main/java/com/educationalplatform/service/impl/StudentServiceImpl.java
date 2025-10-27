package com.educationalplatform.service.impl;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.EnrollmentDto;
import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.entity.Course;
import com.educationalplatform.entity.Enrollment;
import com.educationalplatform.entity.Lesson;
import com.educationalplatform.entity.User;
import com.educationalplatform.enums.Role;
import com.educationalplatform.repositories.CourseRepository;
import com.educationalplatform.repositories.EnrollmentRepository;
import com.educationalplatform.repositories.LessonRepository;
import com.educationalplatform.repositories.UserRepository;
import com.educationalplatform.service.interfaces.StudentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
public class StudentServiceImpl implements StudentService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;


    public StudentServiceImpl(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public EnrollmentDto enrollInCourse(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException("User is not a student");
        }

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (enrollmentRepository.existsByStudentAndCourse(student, course)) {
            throw new RuntimeException("Student is already enrolled in this course");
        }

        Enrollment enrollment = new Enrollment();
        enrollment.setStudent(student);
        enrollment.setCourse(course);
        enrollment.setEnrolledAt(LocalDateTime.now());

        Enrollment saved = enrollmentRepository.save(enrollment);

        EnrollmentDto response = modelMapper.map(saved, EnrollmentDto.class);
        response.setStudentId(studentId);
        response.setCourseId(courseId);

        return response;

    }

    @Override
    public List<CourseDto> getEnrolledCourses(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        if (student.getRole() != Role.STUDENT) {
            throw new RuntimeException("User is not a student");
        }
        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        List<CourseDto> response = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            Course course = enrollment.getCourse();
            CourseDto courseDto  = modelMapper.map(course, CourseDto.class);
            courseDto.setTeacherId(course.getTeacher().getId());
            response.add(courseDto);
        }

        return response;
    }

    @Override
    public List<LessonDto> getCourseLessons(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!enrollmentRepository.existsByStudentAndCourse(student,course)) {
            throw new RuntimeException("Student is not enrolled in this course");
        }

        List<Lesson> lessons = lessonRepository.findByCourse(course);
        List<LessonDto> lessonDtos = new ArrayList<>();

        return lessons.stream()
                .map(lesson -> modelMapper.map(lesson, LessonDto.class))
                .toList();
    }

    @Override
    public List<CourseDto> getAllCourses(Long studentId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        List<Enrollment> enrollments = enrollmentRepository.findByStudent(student);

        return enrollments.stream()
                .map(enrollment -> modelMapper.map(enrollment.getCourse(), CourseDto.class))
                .toList();
    }

    @Override
    public String unenrollFromCourse(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Enrollment enrollment = enrollmentRepository.findByStudentAndCourse(student, course)
                .orElseThrow(() -> new RuntimeException("Student is not enrolled in this course"));

        enrollmentRepository.delete(enrollment);

        return "Student unenrolled successfully from course: " + course.getId();    }
}
