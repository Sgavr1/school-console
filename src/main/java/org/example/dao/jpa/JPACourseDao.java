package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.example.dao.CourseDao;
import org.example.entity.Course;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JPACourse")
public class JPACourseDao implements CourseDao {
    private final Logger logger = LoggerFactory.getLogger(JPACourseDao.class);
    private static final String QUERY_CHECK_EMPTY_TABLE = "Select count(c) From Course c";
    private static final String QUERY_SELECT_ALL = "SELECT c FROM Course c";
    private static final String QUERY_SELECT_BY_NAME = """
            SELECT c
            FROM Course c
            LEFT JOIN c.students
            WHERE c.name = ?1
            """;
    private static final String QUERY_SELECT_ALL_BY_STUDENT_ID = """
            SELECT c
            FROM Course c
            LEFT JOIN c.students s
            WHERE s.id = ?1
            """;

    @PersistenceContext
    private EntityManager em;

    @Override
    public void insert(Course course) {
        try {
            em.merge(course);
        } catch (PersistenceException e) {
            logger.error(String.format("Error insert course: name = %s description = %s", course.getName(), course.getDescription()));
            e.printStackTrace();
        }
    }

    @Override
    public void insertList(List<Course> courses) {
        try {
            for (Course course : courses) {
                em.merge(course);
            }
            em.flush();
        } catch (PersistenceException e) {
            logger.error("Error insert list courses");
            e.printStackTrace();
        }
    }

    @Override
    public List<Course> getAll() {
        return em.createQuery(QUERY_SELECT_ALL, Course.class).getResultList();
    }

    @Override
    public Optional<Course> getByName(String name) {
        try {
            Course course = em.createQuery(QUERY_SELECT_BY_NAME, Course.class).setParameter(1, name).getSingleResult();
            return Optional.ofNullable(course);
        } catch (PersistenceException e) {
            logger.warn(String.format("Not found course by name = %s", name));
            return Optional.empty();
        }
    }

    @Override
    public List<Course> getCoursesByStudentId(int studentId) {
        return em.createQuery(QUERY_SELECT_ALL_BY_STUDENT_ID, Course.class).setParameter(1, studentId).getResultList();
    }

    @Override
    public boolean isEmptyTable() {
        return em.createQuery(QUERY_CHECK_EMPTY_TABLE, Long.class).getSingleResult() == 0;
    }
}