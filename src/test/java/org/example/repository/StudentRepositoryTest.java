package org.example.repository;

import org.example.entity.Group;
import org.example.entity.Student;
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

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        StudentRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentRepositoryTest {
    private static final String INSERT_STUDENT_1_FIRST_NAME = "Юлия";
    private static final String INSERT_STUDENT_1_LAST_NAME = "Мельник";
    private static final String INSERT_STUDENT_2_FIRST_NAME = "Артем";
    private static final String INSERT_STUDENT_2_LAST_NAME = "Литвиненко";
    private static final String STUDENT_BY_ID_1_FIRST_NAME = "Людмила";
    private static final String STUDENT_BY_ID_1_LAST_NAME = "Козлов";
    private static final String STUDENT_BY_ID_2_FIRST_NAME = "Виктория";
    private static final String STUDENT_BY_ID_2_LAST_NAME = "Мельник";
    private static final String COURSE_1_NAME = "Вступ до програмування";
    private static final String COURSE_2_NAME = "Математика для інформатики";
    @Autowired
    private StudentRepository studentRepository;
    private Student student1;
    private Student student2;

    @BeforeAll
    public void init() {
        student1 = new Student(3);
        student1.setGroup(new Group(1));
        student1.setFirstName(INSERT_STUDENT_1_FIRST_NAME);
        student1.setLastName(INSERT_STUDENT_1_LAST_NAME);

        student2 = new Student(4);
        student2.setGroup(new Group(1));
        student2.setFirstName(INSERT_STUDENT_2_FIRST_NAME);
        student2.setLastName(INSERT_STUDENT_2_LAST_NAME);
    }

    @Test
    public void shouldDeleteFromAllCoursesByStudentId() throws SQLException {
        studentRepository.deleteFromAllCourseByStudentId(1);

        Student student = studentRepository.getById(1);

        assertTrue(student.getCourses().isEmpty());
    }

    @Test
    public void shouldDeleteFromCourse() throws SQLException {
        studentRepository.deleteFromCourse(1, 2);

        Student student = studentRepository.findById(1).get();

        assertEquals(1, student.getCourses().size());
        assertEquals(COURSE_1_NAME, student.getCourses().get(0).getName());
    }

    @Test
    public void shouldGetStudentsByCourseName() throws SQLException {
        List<Student> students = studentRepository.findAllStudentByCourseName(COURSE_1_NAME);

        assertEquals(1, students.size());

        Student student = students.get(0);

        assertEquals(1, student.getId());
        assertEquals(1, student.getGroup().getId());
        assertEquals(STUDENT_BY_ID_1_FIRST_NAME, student.getFirstName());
        assertEquals(STUDENT_BY_ID_1_LAST_NAME, student.getLastName());
    }

    @Test
    public void shouldInsertStudentToCourse() throws SQLException {
        studentRepository.saveStudentOnCourse(2, 1);

        Student student = studentRepository.getById(2);

        assertEquals(2, student.getCourses().size());
    }
}