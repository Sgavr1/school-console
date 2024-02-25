package org.example.dao;

import org.example.entity.Student;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class JdbcStudentDao implements StudentDao {
    private static final String QUERY_INSERT = "INSERT INTO students(first_name, last_name, group_id) VALUES (?, ?, ?)";
    private static final String QUERY_INSERT_STUDENT_COURSE = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?);";
    private static final String QUERY_SELECT_BY_ID = """
            SELECT students.*, courses.*
            FROM students
            LEFT JOIN student_course ON student_course.student_id = students.student_id
            LEFT JOIN courses ON courses.course_id = student_course.course_id
            WHERE students.student_id = ?;
            """;
    private static final String QUERY_SELECT_ALL = """
            SELECT students.*, courses.*
            FROM students
            LEFT JOIN student_course ON student_course.student_id = students.student_id
            LEFT JOIN courses ON courses.course_id = student_course.course_id;
            """;
    private static final String QUERY_SELECT_ALL_BY_COURSE_NAME = """
            SELECT students.*, c.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            LEFT JOIN students ON students.student_id = student_course.student_id
            LEFT JOIN student_course as sc ON sc.student_id = students.student_id
            LEFT JOIN courses as c ON c.course_id = sc.course_id
            WHERE courses.course_name = ?;
            """;
    private static final String QUERY_DELETE_BY_ID = "DELETE FROM students WHERE student_id = ?;";
    private static final String QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID = """
            DELETE FROM student_course
            WHERE student_id = ?;
            """;
    private static final String QUERY_DELETE_FROM_COURSE = """
            DELETE FROM student_course
            WHERE student_id = ? AND course_id = ?;
            """;
    private final JdbcTemplate template;
    private final RowMapper<Student> studentMapper;

    public JdbcStudentDao(JdbcTemplate template, RowMapper<Student> studentMapper) {
        this.template = template;
        this.studentMapper = studentMapper;
    }

    @Override
    public void insert(Student student) {
        try {
            template.update(QUERY_INSERT, student.getFirstName(), student.getLastName(), student.getGroupId());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertList(List<Student> students) {
        try {
            template.batchUpdate(QUERY_INSERT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Student student = students.get(i);
                    ps.setString(1, student.getFirstName());
                    ps.setString(2, student.getLastName());
                    ps.setInt(3, student.getGroupId());
                }

                @Override
                public int getBatchSize() {
                    return students.size();
                }
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> getById(int id) {
        try {
            List<Student> students = buldUniqueList(template.query(QUERY_SELECT_BY_ID, studentMapper, id));
            if (students.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(students.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void delete(int id) {
        try(Connection connection = template.getDataSource().getConnection()) {
            connection.setAutoCommit(false);
            template.update(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID, id);
            template.update(QUERY_DELETE_BY_ID, id);
            connection.commit();
            connection.setAutoCommit(true);
        } catch (DataAccessException | SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromAllCoursesByStudentId(int id) {
        try {
            template.update(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID, id);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromCourse(int studentId, int courseId) {
        try {
            template.update(QUERY_DELETE_FROM_COURSE, studentId, courseId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {
        try {
            return buldUniqueList(template.query(QUERY_SELECT_ALL, studentMapper));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) {
        try {
            return buldUniqueList(template.query(QUERY_SELECT_ALL_BY_COURSE_NAME, studentMapper, courseName));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void insertStudentToCourse(int studentId, int courseId) {
        try {
            template.update(QUERY_INSERT_STUDENT_COURSE, studentId, courseId);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertListStudentsOnCourse(int courseId, List<Student> students) {
        try {
            template.batchUpdate(QUERY_INSERT_STUDENT_COURSE, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setInt(1, students.get(i).getId());
                    ps.setInt(2, courseId);
                }

                @Override
                public int getBatchSize() {
                    return students.size();
                }
            });
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    private List<Student> buldUniqueList(List<Student> students) {
        List<Student> uniqueList = new ArrayList<>();

        students.stream().forEach(student ->
                uniqueList.stream().filter(s -> s.getId() == student.getId()).findFirst().ifPresentOrElse(
                        s -> Optional.of(student.getCourses().get(0)).ifPresent(course -> s.getCourses().add(course)),
                        () -> uniqueList.add(student)
                ));

        return uniqueList;
    }
}