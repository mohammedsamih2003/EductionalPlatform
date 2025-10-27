package com.educationalplatform.service.impl;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.entity.Course;
import com.educationalplatform.entity.Lesson;
import com.educationalplatform.entity.User;
import com.educationalplatform.repositories.CourseRepository;
import com.educationalplatform.repositories.EnrollmentRepository;
import com.educationalplatform.repositories.LessonRepository;
import com.educationalplatform.repositories.UserRepository;
import com.educationalplatform.service.interfaces.CourseService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseServiceImpl implements CourseService {
    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    public CourseServiceImpl(UserRepository userRepository, CourseRepository courseRepository, EnrollmentRepository enrollmentRepository, LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CourseDto> getAllCourses() {

        List<Course> courses = courseRepository.findAll();
        List<CourseDto> courseDtos = new ArrayList<>();

        for (Course course : courses) {
            CourseDto courseDto = modelMapper.map(course, CourseDto.class);
            courseDto.setTeacherId(course.getTeacher().getId());
            courseDtos.add(courseDto);
        }

        return courseDtos;
    }
    @Override
    public CourseDto getCourseById(Long id) {
        Course course = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        CourseDto courseDto = modelMapper.map(course, CourseDto.class);

        courseDto.setTeacherId(course.getTeacher().getId());
        return courseDto;
    }

    @Override
    public List<LessonDto> getLessonsByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        List<Lesson> lessons = lessonRepository.findByCourse(course);
        List<LessonDto> lessonDtos = new ArrayList<>();

        for (Lesson lesson : lessons) {
            LessonDto dto = modelMapper.map(lesson, LessonDto.class);
            dto.setCourseId(courseId);
            lessonDtos.add(dto);
        }

        return lessonDtos;
    }

    @Override
    public boolean isStudentEnrolled(Long studentId, Long courseId) {
        User student = userRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Student not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        return enrollmentRepository.existsByStudentAndCourse(student, course);
    }

    @Override
    public CourseDto assignTeacherToCourse(Long teacherId, Long courseId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        course.setTeacher(teacher);
        Course updatedCourse = courseRepository.save(course);

        CourseDto courseDto = modelMapper.map(updatedCourse, CourseDto.class);
        courseDto.setTeacherId(teacherId);

        return courseDto;
    }
}
