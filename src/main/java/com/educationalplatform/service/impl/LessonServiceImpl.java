package com.educationalplatform.service.impl;

import com.educationalplatform.dto.LessonDto;
import com.educationalplatform.entity.Course;
import com.educationalplatform.entity.Lesson;
import com.educationalplatform.repositories.CourseRepository;
import com.educationalplatform.repositories.LessonRepository;
import com.educationalplatform.service.interfaces.LessonService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LessonServiceImpl implements LessonService {

    private final LessonRepository lessonRepository;
    private final CourseRepository courseRepository;
    private final ModelMapper modelMapper;

    public LessonServiceImpl(LessonRepository lessonRepository, CourseRepository courseRepository, ModelMapper modelMapper) {
        this.lessonRepository = lessonRepository;
        this.courseRepository = courseRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public LessonDto createLesson(Long courseId, LessonDto dto) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        Lesson lesson = modelMapper.map(dto, Lesson.class);
        lesson.setCourse(course);

        Lesson savedLesson = lessonRepository.save(lesson);

        LessonDto response = modelMapper.map(savedLesson, LessonDto.class);
        response.setCourseId(courseId);
        return response;
    }

    @Override
    public LessonDto updateLesson(Long lessonId, LessonDto dto) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        modelMapper.map(dto, lesson);

        Lesson updated = lessonRepository.save(lesson);

        LessonDto lessonDto = modelMapper.map(updated, LessonDto.class);
        lessonDto.setCourseId(updated.getCourse().getId());
        return lessonDto;
    }

    @Override
    public String deleteLesson(Long lessonId) {
        Lesson lesson = lessonRepository.findById(lessonId)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        lessonRepository.delete(lesson);
        return "Lesson deleted successfully with ID = " + lessonId;
    }

    @Override
    public LessonDto getLessonById(Long id) {
        Lesson lesson = lessonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Lesson not found"));

        LessonDto lessonDto = modelMapper.map(lesson, LessonDto.class);
        lessonDto.setCourseId(lesson.getCourse().getId());
        return lessonDto;
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
}
