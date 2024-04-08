package org.example.dao.jpa;

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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        JPAStudentDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JPAStudentDaoTest {
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
        studentDao.insert(student1);

        Optional<Student> optionalStudent = studentDao.getById(student1.getId());

        assertTrue(optionalStudent.isPresent());

        Student insertStudent = optionalStudent.get();

        assertEquals(student1.getId(), insertStudent.getId());
        assertEquals(student1.getFirstName(), insertStudent.getFirstName());
        assertEquals(student1.getLastName(), insertStudent.getLastName());
    }

    @Test
    public void shouldInsertList() {
        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);

        studentDao.insertList(students);

        Optional<Student> optionalStudent1 = studentDao.getById(student1.getId());
        Optional<Student> optionalStudent2 = studentDao.getById(student2.getId());

        assertTrue(optionalStudent1.isPresent());
        assertTrue(optionalStudent2.isPresent());

        Student responseStudent1 = optionalStudent1.get();
        Student responseStudent2 = optionalStudent2.get();

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
        Optional<Student> optionalStudent = studentDao.getById(1);

        assertTrue(optionalStudent.isPresent());

        Student responseStudent1 = optionalStudent.get();

        assertEquals(STUDENT_BY_ID_1_LAST_NAME, responseStudent1.getLastName());
        assertEquals(STUDENT_BY_ID_1_FIRST_NAME, responseStudent1.getFirstName());
        assertEquals(1, responseStudent1.getGroup().getId());
        assertEquals(1, responseStudent1.getId());
    }

    @Test
    public void shouldDeleteById() {
        studentDao.delete(1);
        Optional<Student> optionalStudent = studentDao.getById(1);
        assertFalse(optionalStudent.isPresent());
    }

    @Test
    public void shouldDeleteFromAllCoursesByStudentId() {
        studentDao.deleteFromAllCoursesByStudentId(1);

        Optional<Student> optionalStudent = studentDao.getById(1);

        assertTrue(optionalStudent.isPresent());

        Student responseStudent = optionalStudent.get();

        assertTrue(responseStudent.getCourses().isEmpty());
    }

    @Test
    public void shouldDeleteFromCourse() {
        studentDao.deleteFromCourse(1, 2);

        Optional<Student> optionalStudent = studentDao.getById(1);

        assertTrue(optionalStudent.isPresent());

        Student responseStudent = optionalStudent.get();

        assertEquals(1, responseStudent.getCourses().size());
        assertEquals(COURSE_1_NAME, responseStudent.getCourses().get(0).getName());
    }

    @Test
    public void shouldGetAll() {
        List<Student> students = studentDao.getAll();

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
        List<Student> students = studentDao.getStudentsByCourseName(COURSE_1_NAME);

        assertEquals(1, students.size());

        Student student = students.get(0);

        assertEquals(1, student.getId());
        assertEquals(1, student.getGroup().getId());
        assertEquals(STUDENT_BY_ID_1_FIRST_NAME, student.getFirstName());
        assertEquals(STUDENT_BY_ID_1_LAST_NAME, student.getLastName());
    }

    @Test
    public void shouldInsertStudentToCourse() {
        studentDao.insertStudentToCourse(2, 1);

        Optional<Student> optionalStudent = studentDao.getById(2);

        assertTrue(optionalStudent.isPresent());

        Student responseStudent = optionalStudent.get();

        assertEquals(2, responseStudent.getCourses().size());
    }

    @Test
    public void shouldInsertListStudentsOnCourse() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1);
        students.add(student1);

        Student student2 = new Student(2);
        students.add(student2);

        studentDao.insertListStudentsOnCourse(3, students);

        List<Student> responseStudents = studentDao.getStudentsByCourseName(COURSE_2_NAME);

        assertEquals(2, responseStudents.size());

        boolean presentStudent1 = students.stream().filter(s -> s.getId() == student1.getId()).findFirst().isPresent();
        boolean presentStudent2 = students.stream().filter(s -> s.getId() == student2.getId()).findFirst().isPresent();

        assertTrue(presentStudent1);
        assertTrue(presentStudent2);
    }
}