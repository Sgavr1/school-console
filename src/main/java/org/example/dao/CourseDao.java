package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.entity.Course;
import org.example.map.CourseMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDao {
    private static final String QUERY_INSERT = "INSERT INTO courses(course_name, course_description) VALUES(?,?);";
    private static final String QUERY_SELECT_ALL = """
            SELECT courses.*, students.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            JOIN students ON students.student_id = student_course.student_id;
            """;
    private static final String QUERY_SELECT_BY_NAME = """
            SELECT courses.*, students.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            JOIN students ON students.student_id = student_course.student_id
            WHERE courses.course_name = ?;
            """;
    private static final String QUERY_SELECT_ALL_BY_STUDENT_ID = """
            SELECT courses.*, students.*
            FROM students
            LEFT JOIN student_course ON student_course.student_id = students.student_id
            JOIN courses ON courses.course_id = student_course.course_id
            WHERE students.student_id = ?;
            """;
    private ConnectionFactory factory;
    private CourseMapper courseMapper;

    public CourseDao(ConnectionFactory factory, CourseMapper courseMapper) {
        this.factory = factory;
        this.courseMapper = courseMapper;
    }

    public void insert(Course course) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertList(List<Course> courses) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            for (Course course : courses) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getDescription());

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL)) {

            courses = courseMapper.mapCourses(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public Optional<Course> getByName(String name) {
        Course course = null;

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                course = courseMapper.map(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(course);
    }

    public List<Course> getCoursesByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_ALL_BY_STUDENT_ID)) {
            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                courses = courseMapper.mapCourses(resultSet);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }
}