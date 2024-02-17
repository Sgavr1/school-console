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
    private final static String QUERY_SELECT_BY_ID = """
            SELECT students.*, courses.*
            FROM students
            LEFT JOIN student_course ON student_course.student_id = students.student_id
            LEFT JOIN courses ON courses.course_id = student_course.course_id
            WHERE students.student_id = ?;
            """;
    private final static String QUERY_SELECT_ALL = """
            SELECT students.*, courses.*
            FROM students
            LEFT JOIN student_course ON student_course.student_id = students.student_id
            LEFT JOIN courses ON courses.course_id = student_course.course_id;
            """;
    private final static String QUERY_SELECT_ALL_BY_COURSE_ID = """
            SELECT students.*, c.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            LEFT JOIN students ON students.student_id = student_course.student_id
            LEFT JOIN student_course as sc ON sc.student_id = students.student_id
            LEFT JOIN courses as c ON c.course_id = sc.course_id
            WHERE courses.course_id = ?;
            """;
    private final static String QUERY_SELECT_ALL_BY_COURSE_NAME = """
            SELECT students.*, c.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            LEFT JOIN students ON students.student_id = student_course.student_id
            LEFT JOIN student_course as sc ON sc.student_id = students.student_id
            LEFT JOIN courses as c ON c.course_id = sc.course_id
            WHERE courses.course_name = ?;
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
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
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
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            for (Student student : students) {
                statement.setString(1, student.getFirstName());
                statement.setString(2, student.getLastName());
                statement.setInt(3, student.getGroupId());

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Optional<Student> getById(int id) {
        Student student = null;

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_ID)) {
            statement.setInt(1, id);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    student = mapper.mapStudent(resultSet);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(student);
    }

    public void delete(int id) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statementDeleteAllCourse = connection.prepareStatement(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID);
             PreparedStatement statementDelete = connection.prepareStatement(QUERY_DELETE_BY_ID)) {
            connection.setAutoCommit(false);

            statementDeleteAllCourse.setInt(1, id);
            statementDelete.setInt(1, id);

            statementDeleteAllCourse.executeUpdate();
            statementDelete.executeUpdate();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromAllCoursesByStudentId(int id) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID)) {
            statement.setInt(1, id);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromCourse(int studentId, int courseId) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_DELETE_FROM_COURSE)) {
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
             ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL)) {
            students = mapper.mapStudents(resultSet);

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public List<Student> getStudentsByCourseName(String courseName) {
        List<Student> students = new ArrayList<>();
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_ALL_BY_COURSE_ID)) {
            statement.setString(1, courseName);

            try (ResultSet resultSet = statement.executeQuery()) {
                students = mapper.mapStudents(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return students;
    }

    public void insertStudentToCourse(int studentId, int courseId) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_STUDENT_COURSE)) {
            statement.setInt(1, studentId);
            statement.setInt(2, courseId);

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertListStudentsOnCourse(int courseId, List<Student> students) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT_STUDENT_COURSE)) {
            statement.setInt(2, courseId);

            for (Student student : students) {

                statement.setInt(1, student.getId());

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}