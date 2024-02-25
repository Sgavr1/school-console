package org.example.dao;

import org.example.entity.Course;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcCourseDaoTest {
    private static final String INSERT_COURSE_1_NAME = "Мобільна розробка";
    private static final String INSERT_COURSE_1_DESCRIPTION = "Створення мобільних додатків для Android та iOS.";
    private static final String INSERT_COURSE_2_NAME = "Алгоритми та структури даних";
    private static final String INSERT_COURSE_2_DESCRIPTION = "Вивчення класичних алгоритмів та структур даних.";
    private static final String COURSE_BY_ID_1_NAME = "Вступ до програмування";
    private static final String COURSE_BY_ID_1_DESCRIPTION = "Основи програмування для початківців.";
    private static final String COURSE_BY_ID_2_NAME = "Інтернет-маркетинг";
    private static final String COURSE_BY_ID_2_DESCRIPTION = "Основи маркетингу в Інтернеті та просування продуктів онлайн.";
    private static final String COURSE_BY_ID_3_NAME = "Математика для інформатики";
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RowMapper<Course> courseMapper;
    private CourseDao courseDao;
    private Course course1;
    private Course course2;

    @BeforeAll
    public void init() {
        courseDao = new JdbcCourseDao(template, courseMapper);

        course1 = new Course();
        course1.setName(INSERT_COURSE_1_NAME);
        course1.setDescription(INSERT_COURSE_1_DESCRIPTION);

        course2 = new Course();
        course2.setName(INSERT_COURSE_2_NAME);
        course2.setDescription(INSERT_COURSE_2_DESCRIPTION);
    }

    @Test
    public void shouldInsert() {
        courseDao.insert(course1);

        Optional<Course> optionalCourse = courseDao.getByName(INSERT_COURSE_1_NAME);

        assertEquals(true, optionalCourse.isPresent());

        Course responseCourse = optionalCourse.get();

        assertEquals(INSERT_COURSE_1_NAME, course1.getName());
        assertEquals(INSERT_COURSE_1_DESCRIPTION, course1.getDescription());
    }

    @Test
    public void shouldInsertList() {
        List<Course> courses = new ArrayList<>();
        courses.add(course1);
        courses.add(course2);

        courseDao.insertList(courses);

        List<Course> responseCourse = courseDao.getAll();

        assertEquals(true, responseCourse.stream().filter(c -> c.getName().equals(course1.getName())).findFirst().isPresent());
        assertEquals(true, responseCourse.stream().filter(c -> c.getName().equals(course2.getName())).findFirst().isPresent());
    }

    @Test
    public void shouldGetAll() {
        List<Course> responseCourse = courseDao.getAll();

        assertEquals(true, responseCourse.stream().filter(c -> c.getName().equals(COURSE_BY_ID_1_NAME)).findFirst().isPresent());
        assertEquals(true, responseCourse.stream().filter(c -> c.getName().equals(COURSE_BY_ID_2_NAME)).findFirst().isPresent());
        assertEquals(true, responseCourse.stream().filter(c -> c.getName().equals(COURSE_BY_ID_3_NAME)).findFirst().isPresent());
    }

    @Test
    public void shouldGetByName() {
        Optional<Course> optionalCourse = courseDao.getByName(COURSE_BY_ID_1_NAME);

        assertEquals(true, optionalCourse.isPresent());

        Course course = optionalCourse.get();

        assertEquals(COURSE_BY_ID_1_NAME, course.getName());
        assertEquals(COURSE_BY_ID_1_DESCRIPTION, course.getDescription());
    }

    @Test
    public void shouldGetCoursesByStudentId() {
        List<Course> courses = courseDao.getCoursesByStudentId(2);

        assertEquals(1, courses.size());

        Course course = courses.get(0);

        assertEquals(COURSE_BY_ID_2_NAME, course.getName());
        assertEquals(COURSE_BY_ID_2_DESCRIPTION, course.getDescription());
    }
}