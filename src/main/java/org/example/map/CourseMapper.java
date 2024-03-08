package org.example.map;

import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.entity.Course;
import org.example.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CourseMapper implements RowMapper<Course> {
    private StudentMapper studentMapper;

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        Course course = new Course();
        course.setName(rs.getString("course_name"));
        if (rs.wasNull()) {
            return null;
        }
        course.setId(rs.getInt("course_id"));
        course.setDescription(rs.getString("course_description"));

        if (studentMapper != null) {
            Student student = studentMapper.mapRow(rs, rowNum);
            if (student != null) {
                course.getStudents().add(student);
            }
        }

        return course;
    }

    public CourseDto toDto(Course course) {
        List<StudentDto> students = new ArrayList<>();

        if (studentMapper != null) {
            students = course.getStudents().stream().map(studentMapper::toDto).toList();
        }

        return CourseDto.builder()
                .id(course.getId())
                .name(course.getName())
                .description(course.getDescription())
                .students(students)
                .build();
    }

    public Course toEntity(CourseDto courseDto) {
        Course course = new Course();
        course.setId(courseDto.getId());
        course.setName(courseDto.getName());
        course.setDescription(courseDto.getDescription());

        List<Student> students = new ArrayList<>();
        if (studentMapper != null) {
            students = courseDto.getStudents().stream().map(studentMapper::toEntity).toList();
        }

        course.setStudents(students);

        return course;
    }
}