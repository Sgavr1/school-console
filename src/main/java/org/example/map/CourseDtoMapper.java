package org.example.map;

import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.entity.Course;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;

public class CourseDtoMapper {
    private StudentDtoMapper studentDtoMapper;

    public void setStudentDtoMapper(StudentDtoMapper studentDtoMapper) {
        this.studentDtoMapper = studentDtoMapper;
    }

    public CourseDto mapCourseToDto(Course course) {
        List<StudentDto> students = new ArrayList<>();

        if (studentDtoMapper != null) {
            students = course.getStudents().stream().map(studentDtoMapper::mapStudentToDto).toList();
        }

        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .students(students)
                .build();
    }

    public Course mapDtoToCourse(CourseDto courseDto) {
        Course course = new Course();
        course.setId(courseDto.getId());
        course.setName(course.getName());
        course.setDescription(course.getDescription());

        List<Student> students = new ArrayList<>();
        if (studentDtoMapper != null) {
            students = courseDto.getStudents().stream().map(studentDtoMapper::mapDtoToStudent).toList();
        }

        course.setStudents(students);

        return course;
    }
}