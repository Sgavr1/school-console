package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.example.dao.StudentDao;
import org.example.entity.Student;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JPAStudent")
public class JPAStudentDao implements StudentDao {
    private final Logger logger = LoggerFactory.getLogger(JPAStudentDao.class);
    private static final String QUERY_CHECK_EMPTY_TABLE = "Select count(s) From Student s";
    private static final String QUERY_INSERT_STUDENT_COURSE = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?);";
    private static final String QUERY_SELECT_ALL = "SELECT s FROM Student s";
    private static final String QUERY_SELECT_ALL_BY_COURSE_NAME = """
            SELECT s
            FROM Student s
            LEFT JOIN s.courses c
            WHERE c.name = ?1
            """;
    private static final String QUERY_DELETE_BY_ID = "DELETE FROM Student s WHERE s.id = ?1";
    private static final String QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID = """
            DELETE FROM student_course
            WHERE student_id = ?;
            """;
    private static final String QUERY_DELETE_FROM_COURSE = """
            DELETE FROM student_course
            WHERE student_id = ? AND course_id = ?;
            """;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Student student) {
        try {
            em.merge(student);
        } catch (PersistenceException e) {
            logger.error(String.format("Error insert student: firstName = %s lastName = %s ", student.getFirstName(), student.getLastName()));
            e.printStackTrace();
        }
    }

    @Override
    public void insertList(List<Student> students) {
        try {
            for (Student student : students) {
                em.merge(student);
            }
            em.flush();
        } catch (PersistenceException e) {
            logger.error("Error insert list students");
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Student> getById(int id) {
        Student student = em.find(Student.class, id);
        if (student == null) {
            logger.warn(String.format("Not found student by id = %d", id));
        }
        return Optional.ofNullable(student);
    }

    @Override
    public void delete(int id) {
        try {
            em.createNativeQuery(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID).setParameter(1, id).executeUpdate();
            em.createQuery(QUERY_DELETE_BY_ID).setParameter(1, id).executeUpdate();
        } catch (PersistenceException e) {
            logger.error(String.format("Error delete student by id = %d", id));
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromAllCoursesByStudentId(int id) {
        try {
            em.createNativeQuery(QUERY_DELETE_FROM_ALL_COURSES_BY_STUDENT_ID).setParameter(1, id).executeUpdate();
        } catch (PersistenceException e) {
            logger.error(String.format("Error deleting student from all courses by id = %d", id));
            e.printStackTrace();
        }
    }

    @Override
    public void deleteFromCourse(int studentId, int courseId) {
        try {
            em.createNativeQuery(QUERY_DELETE_FROM_COURSE)
                    .setParameter(1, studentId)
                    .setParameter(2, courseId)
                    .executeUpdate();
        } catch (PersistenceException e) {
            logger.error(String.format("Error delete student from course: studentId = %d courseId = %d",
                    studentId, courseId));
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() {
        return em.createQuery(QUERY_SELECT_ALL, Student.class).getResultList();
    }

    @Override
    public List<Student> getStudentsByCourseName(String courseName) {
        return em.createQuery(QUERY_SELECT_ALL_BY_COURSE_NAME).setParameter(1, courseName).getResultList();
    }

    @Override
    public void insertStudentToCourse(int studentId, int courseId) {
        try {
            em.createNativeQuery(QUERY_INSERT_STUDENT_COURSE)
                    .setParameter(1, studentId)
                    .setParameter(2, courseId)
                    .executeUpdate();
        } catch (PersistenceException e) {
            logger.error(String.format("Error insert student on course: studentId = %d courseId = %d",
                    studentId, courseId));
            e.printStackTrace();
        }
    }

    @Override
    public void insertListStudentsOnCourse(int courseId, List<Student> students) {
        try {
            for (Student student : students) {
                em.createNativeQuery(QUERY_INSERT_STUDENT_COURSE)
                        .setParameter(1, student.getId())
                        .setParameter(2, courseId)
                        .executeUpdate();
            }
            em.flush();
        } catch (PersistenceException e) {
            logger.error("Error insert list students on courses");
            e.printStackTrace();
        }
    }

    @Override
    public boolean isEmptyTable() {
        return em.createQuery(QUERY_CHECK_EMPTY_TABLE, Long.class).getSingleResult() == 0;
    }
}