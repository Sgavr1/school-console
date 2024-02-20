package org.example.map;

import org.example.entity.Course;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper {
    public Course map(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setName(resultSet.getString("course_name"));
        if (resultSet.wasNull()) {
            return null;
        }
        course.setId(resultSet.getInt("course_id"));
        course.setDescription(resultSet.getString("course_description"));

        return course;
    }
}
