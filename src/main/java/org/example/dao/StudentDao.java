package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.entity.Student;
import org.example.map.EntityMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDao {
    private final static String QUERY_INSERT = "INSERT INTO students(first_name, last_name, group_id) VALUES (?, ?, ?)";
    private final static String QUERY_INSERT_STUDENT_COURSE = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?)";
    private final static String QUERY_SELECT_BY_ID = "SELECT * FROM students WHERE student_id = ?;";
    private final static String QUERY_SELECT_ALL = "SELECT * FROM students;";
    private final static String QUERY_SELECT_ALL_BY_COURSE_ID = """
            Select students.student_id, first_name, last_name, group_id
            FROM students
            JOIN student_course ON student_course.student_id = students.student_id
            WHERE course_id = ?;
            """;
    private final static String QUERY_DELETE_BY_ID = "DELETE FROM students WHERE student_id = ?;";
    private final static String QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID = """
            DELETE FROM student_course
            WHERE student_id = ?;
            """;
    private final static String QUERY_DELETE_FROM_COURSE = """
            DELETE FROM student_course
            WHERE student_id = ? AND course_id = ?;
            """;
    private ConnectionFactory factory;
    private EntityMapper mapper;

    public StudentDao(ConnectionFactory factory, EntityMapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    public void insert(Student student) {

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT);) {

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getGroupId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertList(List<Student> students) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT);) {

            connection.setAutoCommit(false);

            for (Student student : students) {
                statement.setString(1, student.getFirstName());
                statement.setString(2, student.getLastName());
                statement.setInt(3, student.getGroupId());

                statement.addBatch();
            }

            statement.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Student> getById(int id) {
        Student student = null;

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID);) {

            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery();) {
                if (resultSet.next()) {
                    student = mapper.map(resultSet, Student.class);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(student);
    }

    public void delete(int id) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_BY_ID);) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromAllCoursesByStudentId(int id) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID);) {

            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromCourse(int studentId, int courseId) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_FROM_COURSE);) {

            statement.setInt(1, studentId);
            statement.setInt(2, courseId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL);) {

            while (resultSet.next()) {
                students.add(mapper.map(resultSet, Student.class));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> getStudentsByCourseId(int courseId) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_ALL_BY_COURSE_ID);) {

            statement.setInt(1, courseId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    students.add(mapper.map(resultSet, Student.class));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public void insertStudentToCourse(int studentId, int courseId) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_STUDENT_COURSE);) {

            statement.setInt(1, studentId);
            statement.setInt(2, courseId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertListStudentsOnCourse(int courseId, List<Student> students) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_STUDENT_COURSE);) {

            connection.setAutoCommit(false);

            statement.setInt(2, courseId);

            for (Student student : students) {

                statement.setInt(1, student.getId());

                statement.addBatch();
            }

            statement.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}