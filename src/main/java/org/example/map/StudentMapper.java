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
import java.util.stream.Collectors;

public class StudentMapper implements RowMapper<Student> {
    private CourseMapper courseMapper;

    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        Student student = new Student();
        student.setFirstName(rs.getString("first_name"));
        if (rs.wasNull()) {
            return null;
        }
        student.setId(rs.getInt("student_id"));
        student.setLastName(rs.getString("last_name"));
        student.setGroupId(rs.getInt("group_id"));

        if (courseMapper != null) {
            Course course = courseMapper.mapRow(rs, rowNum);
            if (course != null) {
                student.getCourses().add(course);
            }
        }

        return student;
    }

    public StudentDto toDto(Student student) {
        List<CourseDto> courses = new ArrayList<>();

        if (courseMapper != null) {
            courses = student.getCourses().stream().map(courseMapper::toDto).collect(Collectors.toList());
        }

        return StudentDto.builder()
                .id(student.getId())
                .firstName(student.getFirstName())
                .lastName(student.getLastName())
                .groupId(student.getGroupId())
                .courses(courses)
                .build();
    }

    public Student toEntity(StudentDto studentDto) {
        Student student = new Student();
        student.setId(studentDto.getId());
        student.setFirstName(studentDto.getFirstName());
        student.setLastName(studentDto.getLastName());
        student.setGroupId(studentDto.getGroupId());

        List<Course> courses = new ArrayList<>();
        if (courseMapper != null) {
            courses = studentDto.getCourses().stream()
                    .map(courseMapper::toEntity)
                    .collect(Collectors.toList());
        }

        student.setCourses(courses);

        return student;
    }
}