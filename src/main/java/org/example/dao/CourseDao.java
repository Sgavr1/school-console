package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.entity.Course;
import org.example.map.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CourseDao {
    private final static String QUERY_INSERT = "INSERT INTO courses(course_name, course_description) VALUES(?,?);";
    private final static String QUERY_SELECT_ALL = "SELECT * FROM courses;";
    private final static String QUERY_SELECT_BY_NAME = "SELECT * FROM courses WHERE course_name = ?;";
    private final static String QUERY_SELECT_ALL_BY_STUDENT_ID = """
            SELECT courses.course_id, course_name, description
            FROM courses
            JOIN student_course ON student_course.course_id = courses.course_id
            WHERE student_id = ?;
            """;
    private ConnectionFactory factory;
    private EntityMapper mapper;

    public CourseDao(ConnectionFactory factory, EntityMapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    public void insert(Course course) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT);) {

            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertList(List<Course> courses) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT);) {
            connection.setAutoCommit(false);
            for (Course course : courses) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getDescription());

                statement.addBatch();
            }

            statement.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL);) {
            EntityMapper mapper = new EntityMapper();
            while (resultSet.next()) {
                courses.add(mapper.map(resultSet, Course.class));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }

    public Optional<Course> getByName(String name) {
        Course course = null;

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_NAME);) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                EntityMapper mapper = new EntityMapper();
                if (resultSet.next()) {
                    course = mapper.map(resultSet, Course.class);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(course);
    }

    public List<Course> getCoursesByStudentId(int studentId) {
        List<Course> courses = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_ALL_BY_STUDENT_ID);) {

            statement.setInt(1, studentId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    courses.add(mapper.map(resultSet, Course.class));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return courses;
    }


}