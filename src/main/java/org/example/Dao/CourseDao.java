package org.example.Dao;

import org.example.ConnectionFactory;
import org.example.Entity.Course;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.OptionalDouble;

public class CourseDao {
    private ConnectionFactory factory;

    public CourseDao(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void insert(Course course) {
        PreparedStatement statement = null;

        String query = "INSERT INTO courses(course_name, course_description) VALUES(?,?);";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);

            statement.setString(1, course.getName());
            statement.setString(2, course.getDescription());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertList(List<Course> courses) {
        PreparedStatement statement = null;

        String query = "INSERT INTO courses(course_name, course_description) VALUES(?,?);";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);

            for (Course course : courses) {
                statement.setString(1, course.getName());
                statement.setString(2, course.getDescription());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Course> getAll() {
        List<Course> courses = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM courses;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Course course = new Course();
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("course_name"));
                course.setDescription(resultSet.getString("course_description"));

                courses.add(course);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return courses;
    }

    public Optional<Course> getByName(String name) {
        Course course = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM courses WHERE course_name = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                course = new Course();
                course.setId(resultSet.getInt("course_id"));
                course.setName(resultSet.getString("course_name"));
                course.setDescription(resultSet.getString("course_description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(course);
    }
}
