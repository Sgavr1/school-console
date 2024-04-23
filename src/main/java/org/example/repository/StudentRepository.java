package org.example.repository;

import org.example.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Integer> {
    @Query(value = "SELECT s FROM Student s LEFT JOIN s.courses c WHERE c.name = ?1")
    List<Student> findAllStudentByCourseName(String name) throws SQLException;

    @Modifying
    @Query(value = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?);", nativeQuery = true)
    void saveStudentOnCourse(Integer studentId, Integer courseId) throws SQLException;

    @Modifying
    @Query(value = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?;", nativeQuery = true)
    void deleteFromCourse(Integer studentId, Integer courseId) throws SQLException;

    @Modifying
    @Query(value = "DELETE FROM student_course WHERE student_id = ?;", nativeQuery = true)
    void deleteFromAllCourseByStudentId(Integer studentId) throws SQLException;
}