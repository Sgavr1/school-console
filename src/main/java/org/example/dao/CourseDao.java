package org.example.dao;

import org.example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseDao extends JpaRepository<Course,Integer> {
    static final String QUERY_SELECT_ALL_BY_STUDENT_ID = """
            SELECT c
            FROM Course c
            LEFT JOIN c.students s
            WHERE s.id = ?1
            """;

    Optional<Course> findByName(String name) throws SQLException;
    @Query(value = QUERY_SELECT_ALL_BY_STUDENT_ID)
    List<Course> findAllCourseByStudentId(Integer studentId) throws  SQLException;
}