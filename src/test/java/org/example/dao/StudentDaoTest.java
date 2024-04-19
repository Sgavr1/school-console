package org.example.dao;

import org.example.dao.StudentDao;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        StudentDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentDaoTest {
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
    private StudentDao studentDao;
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
    public void shouldInsertStudent() {
        studentDao.save(student1);

        Student student = studentDao.getById(student1.getId());

        assertEquals(student1.getId(), student.getId());
        assertEquals(student1.getFirstName(), student.getFirstName());
        assertEquals(student1.getLastName(), student.getLastName());
    }

    @Test
    public void shouldInsertList() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        studentDao.saveAll(students);

        Student responseStudent1 = studentDao.getById(student1.getId());
        Student responseStudent2 = studentDao.getById(student2.getId());

        assertEquals(student1.getLastName(), responseStudent1.getLastName());
        assertEquals(student1.getFirstName(), responseStudent1.getFirstName());
        assertEquals(student1.getGroup().getId(), responseStudent1.getGroup().getId());
        assertEquals(student1.getId(), responseStudent1.getId());

        assertEquals(student2.getLastName(), responseStudent2.getLastName());
        assertEquals(student2.getFirstName(), responseStudent2.getFirstName());
        assertEquals(student2.getGroup().getId(), responseStudent2.getGroup().getId());
        assertEquals(student2.getId(), responseStudent2.getId());

    }

    @Test
    public void shouldGetById() {
        Student student = studentDao.getById(1);

        assertEquals(STUDENT_BY_ID_1_LAST_NAME, student.getLastName());
        assertEquals(1, student.getGroup().getId());
    }

    @Test
    public void shouldDeleteById() {
        studentDao.delete(studentDao.findById(1).get());
        assertFalse(studentDao.findById(1).isPresent());
    }

    @Test
    public void shouldDeleteFromAllCoursesByStudentId() {
        try {
            studentDao.deleteFromAllCourseByStudentId(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Student student = studentDao.getById(1);

        assertTrue(student.getCourses().isEmpty());
    }

    @Test
    public void shouldDeleteFromCourse() {
        try {
            studentDao.deleteFromCourse(1, 2);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        Student student = studentDao.findById(1).get();

        assertEquals(1, student.getCourses().size());
        assertEquals(COURSE_1_NAME, student.getCourses().get(0).getName());
    }

    @Test
    public void shouldGetAll() {
        List<Student> students = studentDao.findAll();

        assertEquals(2, students.size());

        Student responseStudent1 = students.get(0);
        Student responseStudent2 = students.get(1);

        assertEquals(1, responseStudent1.getId());
        assertEquals(1, responseStudent1.getGroup().getId());
        assertEquals(STUDENT_BY_ID_1_FIRST_NAME, responseStudent1.getFirstName());
        assertEquals(STUDENT_BY_ID_1_LAST_NAME, responseStudent1.getLastName());

        assertEquals(2, responseStudent2.getId());
        assertEquals(1, responseStudent2.getGroup().getId());
        assertEquals(STUDENT_BY_ID_2_FIRST_NAME, responseStudent2.getFirstName());
        assertEquals(STUDENT_BY_ID_2_LAST_NAME, responseStudent2.getLastName());
    }

    @Test
    public void shouldGetStudentsByCourseName() {
        try {
            List<Student> students = studentDao.findAllStudentByCourseName(COURSE_1_NAME);

            assertEquals(1, students.size());

            Student student = students.get(0);

            assertEquals(1, student.getId());
            assertEquals(1, student.getGroup().getId());
            assertEquals(STUDENT_BY_ID_1_FIRST_NAME, student.getFirstName());
            assertEquals(STUDENT_BY_ID_1_LAST_NAME, student.getLastName());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldInsertStudentToCourse() {
        try {
            studentDao.saveStudentOnCourse(2, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        Student student = studentDao.getById(2);

        assertEquals(2, student.getCourses().size());
    }
}