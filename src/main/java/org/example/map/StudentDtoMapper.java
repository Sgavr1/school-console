package org.example.map;

import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.entity.Course;
import org.example.entity.Student;

import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class StudentDtoMapper {
    private CourseDtoMapper courseDtoMapper;

    public void setCourseDtoMapper(CourseDtoMapper courseDtoMapper) {
        this.courseDtoMapper = courseDtoMapper;
    }

    public StudentDto mapStudentToDto(Student student) {
        List<CourseDto> courses = new ArrayList<>();

        if (courseDtoMapper != null) {
            courses = student.getCourses().stream().map(courseDtoMapper::mapCourseToDto).collect(Collectors.toList());
        }

        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .groupId(student.getGroupId())
                .courses(courses)
                .build();
    }

    public Student mapDtoToStudent(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setGroupId(studentDto.getGroupId());

        List<Course> courses = new ArrayList<>();
        if (courseDtoMapper != null) {
            courses = studentDto.getCourses().stream()
                    .map(courseDtoMapper::mapDtoToCourse)
                    .collect(Collectors.toList());
        }

        student.setCourses(courses);

        return student;
    }
}
