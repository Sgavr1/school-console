package org.example.dao;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface StudentDao extends JpaRepository<Student, Integer> {
    static final String QUERY_SELECT_ALL_BY_COURSE_NAME = """
            SELECT s
            FROM Student s
            LEFT JOIN s.courses c
            WHERE c.name = ?1
            """;

    static final String QUERY_INSERT_STUDENT_COURSE = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?);";
    static final String QUERY_DELETE_FROM_ALL_COURSE = """
            DELETE FROM student_course
            WHERE student_id = ?;
            """;
    static final String QUERY_DELETE_FROM_COURSE = """
            DELETE FROM student_course
            WHERE student_id = ? AND course_id = ?;
            """;

    @Query(value = QUERY_SELECT_ALL_BY_COURSE_NAME)
    List<Student> findAllStudentByCourseName(String name) throws SQLException;

    @Modifying
    @Query(value = QUERY_INSERT_STUDENT_COURSE, nativeQuery = true)
    void saveStudentOnCourse(Integer studentId, Integer courseId) throws SQLException;
    @Modifying
    @Query(value = QUERY_DELETE_FROM_COURSE, nativeQuery = true)
    void deleteFromCourse(Integer studentId, Integer courseId) throws SQLException;
    @Modifying
    @Query(value = QUERY_DELETE_FROM_ALL_COURSE, nativeQuery = true)
    void deleteFromAllCourseByStudentId(Integer studentId) throws SQLException;
}