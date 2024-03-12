package org.example.map.row;

import org.example.entity.Course;
import org.example.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    private RowMapper<Course> courseMapper;

    public void setCourseMapper(RowMapper<Course> courseMapper) {
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
}
