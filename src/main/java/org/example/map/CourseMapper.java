package org.example.map;

import org.example.entity.Course;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseMapper {
    public StudentMapper studentMapper;

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Course map(ResultSet resultSet) throws SQLException {
        Course course = null;
        if (resultSet.next()) {
            course = fill(resultSet);
            do {
                course.getStudents().add(studentMapper.fill(resultSet));
            } while (resultSet.next());
        }

        return course;
    }

    public List<Course> mapCourses(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();
        while (resultSet.next()) {
            Course mapCourse = fill(resultSet);
            Course course = courses.stream().filter(c -> c.getId() == mapCourse.getId()).findFirst().orElseGet(() -> {
                courses.add(mapCourse);
                return mapCourse;
            });

            Student student = studentMapper.fill(resultSet);
            if (student != null) {
                course.getStudents().add(student);
            }
        }

        return courses;
    }

    public Course fill(ResultSet resultSet) throws SQLException {
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
