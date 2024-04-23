package org.example.repository;

import org.example.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Integer> {
    Optional<Course> findByName(String name) throws SQLException;

    @Query(value = "SELECT c FROM Course c LEFT JOIN c.students s WHERE s.id = ?1")
    List<Course> findAllCourseByStudentId(Integer studentId) throws SQLException;
}