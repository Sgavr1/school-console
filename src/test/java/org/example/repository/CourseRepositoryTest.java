package org.example.repository;

import org.example.entity.Course;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        CourseRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CourseRepositoryTest {
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
    private CourseRepository courseRepository;
    private Course course1;
    private Course course2;

    @BeforeAll
    public void init() {
        course1 = new Course();
        course1.setName(INSERT_COURSE_1_NAME);
        course1.setDescription(INSERT_COURSE_1_DESCRIPTION);

        course2 = new Course();
        course2.setName(INSERT_COURSE_2_NAME);
        course2.setDescription(INSERT_COURSE_2_DESCRIPTION);
    }

    @Test
    public void shouldGetByName() throws SQLException {
        Optional<Course> optionalCourse = courseRepository.findByName(COURSE_BY_ID_1_NAME);

        assertTrue(optionalCourse.isPresent());

        Course course = optionalCourse.get();

        assertEquals(COURSE_BY_ID_1_NAME, course.getName());
        assertEquals(COURSE_BY_ID_1_DESCRIPTION, course.getDescription());
    }

    @Test
    public void shouldGetCoursesByStudentId() throws SQLException {
        List<Course> courses = courseRepository.findAllCourseByStudentId(2);

        assertEquals(1, courses.size());

        Course course = courses.get(0);

        assertEquals(COURSE_BY_ID_2_NAME, course.getName());
        assertEquals(COURSE_BY_ID_2_DESCRIPTION, course.getDescription());
    }
}