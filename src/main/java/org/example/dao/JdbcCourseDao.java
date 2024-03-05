package org.example.dao;

import org.example.entity.Course;
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
public class JdbcCourseDao implements CourseDao {
    private static final String QUERY_INSERT = "INSERT INTO courses(course_name, course_description) VALUES(?,?);";
    private static final String QUERY_SELECT_ALL = """
            SELECT courses.*, students.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            LEFT JOIN students ON students.student_id = student_course.student_id;
            """;
    private static final String QUERY_SELECT_BY_NAME = """
            SELECT courses.*, students.*
            FROM courses
            LEFT JOIN student_course ON student_course.course_id = courses.course_id
            LEFT JOIN students ON students.student_id = student_course.student_id
            WHERE courses.course_name = ?;
            """;
    private static final String QUERY_SELECT_ALL_BY_STUDENT_ID = """
            SELECT courses.*, students.*
            FROM students
            LEFT JOIN student_course ON student_course.student_id = students.student_id
            LEFT JOIN courses ON courses.course_id = student_course.course_id
            WHERE students.student_id = ?;
            """;

    private final JdbcTemplate template;
    private final RowMapper<Course> courseMapper;

    public JdbcCourseDao(JdbcTemplate template, RowMapper<Course> courseMapper) {
        this.template = template;
        this.courseMapper = courseMapper;
    }

    @Override
    public void insert(Course course) {
        try {
            template.update(QUERY_INSERT, course.getName(), course.getDescription());
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertList(List<Course> courses) {
        try {
            template.batchUpdate(QUERY_INSERT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    Course course = courses.get(i);
                    ps.setString(1, course.getName());
                    ps.setString(2, course.getDescription());
                }

                @Override
                public int getBatchSize() {
                    return courses.size();
                }
            });

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {
        try {
            return buildUniqueList(template.query(QUERY_SELECT_ALL, courseMapper));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Course> getByName(String name) {
        try {
            List<Course> courses = buildUniqueList(template.query(QUERY_SELECT_BY_NAME, courseMapper, name));
            if (courses.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(courses.get(0));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getCoursesByStudentId(int studentId) {
        try {
            return buildUniqueList(template.query(QUERY_SELECT_ALL_BY_STUDENT_ID, courseMapper, studentId));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    private List<Course> buildUniqueList(List<Course> courses) {
        List<Course> uniqueList = new ArrayList<>();
        courses.stream().forEach(course ->
                uniqueList.stream().filter(c -> c.getId() == course.getId()).findFirst().ifPresentOrElse(
                        c -> Optional.of(course.getStudents().get(0)).ifPresent(student -> c.getStudents().add(student)),
                        () -> uniqueList.add(course)
                ));

        return uniqueList;
    }
}