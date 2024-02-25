package org.example.map;

import org.example.entity.Course;
import org.example.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper implements RowMapper<Course> {
    public RowMapper<Student> studentMapper;

    public void setStudentMapper(RowMapper<Student> studentMapper) {
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
}
