package com.educationalplatform.service.impl;

import com.educationalplatform.dto.CourseDto;
import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.entity.Course;
import com.educationalplatform.entity.Lesson;
import com.educationalplatform.entity.User;
import com.educationalplatform.repositories.CourseRepository;
import com.educationalplatform.repositories.LessonRepository;
import com.educationalplatform.repositories.UserRepository;
import com.educationalplatform.service.interfaces.TeacherService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {

    private final UserRepository userRepository;
    private final CourseRepository courseRepository;
    private final LessonRepository lessonRepository;
    private final ModelMapper modelMapper;

    public TeacherServiceImpl(UserRepository userRepository, CourseRepository courseRepository, LessonRepository lessonRepository, ModelMapper modelMapper) {
        this.userRepository = userRepository;
        this.courseRepository = courseRepository;
        this.lessonRepository = lessonRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public CourseDto createCourse(Long teacherId, CourseDto coursedto) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(()->new RuntimeException("Teacher not found"));

        Course course = modelMapper.map(coursedto,Course.class);
        course.setTeacher(teacher);

        Course savedCourse = courseRepository.save(course);

        CourseDto response = modelMapper.map(savedCourse, CourseDto.class);
        response.setTeacherId(teacher.getId());

        return response;
    }

    @Override
    public CourseDto updateCourse(Long teacherId, Long courseId, CourseDto courseDto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()->new RuntimeException("Course not found"));

        User teacher = userRepository.findById(teacherId)
                .orElseThrow(()->new RuntimeException("Teacher not found"));

        if (!course.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("You are not authorized to update this course");
        }

        modelMapper.map(courseDto, course);

        Course updatedCourse = courseRepository.save(course);

        CourseDto response = modelMapper.map(updatedCourse, CourseDto.class);
        return response;

    }

    @Override
    public String deleteCourse(Long teacherId, Long courseId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        if (!course.getTeacher().getId().equals(teacherId)) {
            throw new RuntimeException("You are not authorized to delete this course");
        }

        courseRepository.delete(course);

        return "Course with ID " + courseId + " has been deleted successfully.";
    }

    @Override
    public List<CourseDto> getAllCoursesByTeacher(Long teacherId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(() -> new RuntimeException("Teacher not found"));

        List<Course> courses = courseRepository.findByTeacher(teacher);

        List<CourseDto> courseDtos = new ArrayList<>();
        for (Course course : courses) {
            CourseDto courseDto = modelMapper.map(course, CourseDto.class);
            courseDto.setTeacherId(course.getTeacher().getId());
            courseDtos.add(courseDto);
        }

        return courseDtos;
    }

    @Override
    public LessonDto addLessonToCourse(Long teacherId, Long courseId, LessonDto lessonDto) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(()->new RuntimeException("Teacher not found"));
        Course course = courseRepository.findById(courseId)
                .orElseThrow(()-> new RuntimeException("Course not found"));

        if (!course.getTeacher().getId().equals(teacherId)){
            throw new RuntimeException("You are not authorized to add this lesson");
        }
        Lesson lesson = modelMapper.map(lessonDto,Lesson.class);
        lesson.setCourse(course);
        Lesson savedLesson = lessonRepository.save(lesson);

        LessonDto response = modelMapper.map(savedLesson, LessonDto.class);
        response.setCourseId(courseId);

        return response;
    }

    @Override
    public LessonDto updateLesson(Long teacherId, Long lessonId, LessonDto lessonDto) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(()->new RuntimeException("Teacher not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()-> new RuntimeException("Lesson not found"));

        if (!lesson.getCourse().getTeacher().getId().equals(teacherId)){
            throw new RuntimeException("You are not authorized to update this lesson");
        }
        modelMapper.map(lessonDto,lesson);

        Lesson updatedLesson = lessonRepository.save(lesson);

        LessonDto response = modelMapper.map(updatedLesson, LessonDto.class);
        response.setCourseId(updatedLesson.getCourse().getId());

        return response;
    }

    @Override
    public String deleteLesson(Long teacherId, Long lessonId) {
        User teacher = userRepository.findById(teacherId)
                .orElseThrow(()->new RuntimeException("Teacher not found"));
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(()-> new RuntimeException("Lesson not found"));

        if (!lesson.getCourse().getTeacher().getId().equals(teacherId)){
            throw new RuntimeException("You are not authorized to update this lesson");
        }

        lessonRepository.delete(lesson);


        return  "Lesson with ID " + lessonId + " has been deleted successfully.";
    }
}
