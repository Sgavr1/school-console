package org.example.service;

import org.example.configuration.MapperConfiguration;
import org.example.dao.StudentDao;
import org.example.dto.StudentDto;
import org.example.entity.Group;
import org.example.entity.Student;
import org.example.mapper.StudentMapper;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {MapperConfiguration.class})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceTest {
    private static final String STUDENT_1_FIRST_NAME = "Юлия";
    private static final String STUDENT_1_LAST_NAME = "Мельник";
    private static final String STUDENT_2_FIRST_NAME = "Артем";
    private static final String STUDENT_2_LAST_NAME = "Литвиненко";
    private static final String COURSE_NAME = "Вступ до програмування";
    @MockBean
    private StudentDao studentDao;
    @Autowired
    private StudentMapper mapper;
    private StudentService studentService;

    @BeforeAll
    public void init() {
        studentService = new StudentService(studentDao, mapper);
    }

    @Test
    public void shouldReturnStudentWhenCorrectStudentId() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName(STUDENT_1_FIRST_NAME);
        student.setLastName(STUDENT_1_LAST_NAME);
        student.setGroup(new Group(1));

        Mockito.when(studentDao.findById(1)).thenReturn(Optional.of(student));

        StudentDto response = studentService.getStudentById(1);

        verify(studentDao).findById(1);

        assertEquals(1, response.getId());
        assertEquals(1, response.getGroupId());
        assertEquals(STUDENT_1_FIRST_NAME, response.getFirstName());
        assertEquals(STUDENT_1_LAST_NAME, response.getLastName());
    }

    @Test
    public void shouldReturnByCourseName() {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student(1);
        student1.setGroup(new Group(4));
        student1.setFirstName(STUDENT_1_FIRST_NAME);
        student1.setLastName(STUDENT_1_LAST_NAME);

        students.add(student1);

        Student student2 = new Student(2);
        student2.setGroup(new Group(7));
        student2.setFirstName(STUDENT_2_FIRST_NAME);
        student2.setLastName(STUDENT_2_LAST_NAME);

        students.add(student2);

        try {
            Mockito.when(studentDao.findAllStudentByCourseName(COURSE_NAME)).thenReturn(students);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<StudentDto> responses = studentService.getStudentsByCourseName(COURSE_NAME);

        try {
            verify(studentDao).findAllStudentByCourseName(COURSE_NAME);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        StudentDto responseStudent1 = responses.get(0);
        StudentDto responseStudent2 = responses.get(1);

        assertEquals(student1.getId(), responseStudent1.getId());
        assertEquals(student1.getFirstName(), responseStudent1.getFirstName());
        assertEquals(student1.getLastName(), responseStudent1.getLastName());
        assertEquals(student1.getGroup().getId(), responseStudent1.getGroupId());

        assertEquals(student2.getId(), responseStudent2.getId());
        assertEquals(student2.getFirstName(), responseStudent2.getFirstName());
        assertEquals(student2.getLastName(), responseStudent2.getLastName());
        assertEquals(student2.getGroup().getId(), responseStudent2.getGroupId());
    }

    @Test
    public void shouldAddStudent() {
        StudentDto student = new StudentDto();

        studentService.addStudent(student);

        verify(studentDao).save(any(Student.class));
    }

    @Test
    public void shouldAddStudents() {
        List<StudentDto> students = new ArrayList<>();

        studentService.addStudents(students);

        verify(studentDao).saveAll(anyList());
    }

    @Test
    public void shouldAddStudentToCourse() {
        studentService.addStudentToCourse(1, 1);

        try {
            verify(studentDao).saveStudentOnCourse(1, 1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void shouldAddStudentsToCourse() {
        List<StudentDto> students = new ArrayList<>();
        students.add(StudentDto.builder().build());

        studentService.addStudentsToCourse(1, students);

        try {
            verify(studentDao).saveStudentOnCourse(anyInt(), anyInt());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void shouldDeletedStudent() {
        StudentDto student = StudentDto.builder().id(1).build();

        studentService.delete(student);

        verify(studentDao).deleteById(any());
    }

    @Test
    public void shouldReturnListAllStudents() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1);
        student1.setGroup(new Group(4));
        student1.setFirstName(STUDENT_1_FIRST_NAME);
        student1.setLastName(STUDENT_2_LAST_NAME);
        students.add(student1);

        Student student2 = new Student(2);
        student2.setGroup(new Group(7));
        student2.setFirstName(STUDENT_2_FIRST_NAME);
        student2.setLastName(STUDENT_2_LAST_NAME);
        students.add(student2);

        Mockito.when(studentDao.findAll()).thenReturn(students);

        List<StudentDto> responses = studentService.getStudents();

        verify(studentDao).findAll();

        StudentDto responseStudent1 = responses.get(0);
        StudentDto responseStudent2 = responses.get(1);

        assertEquals(student1.getId(), responseStudent1.getId());
        assertEquals(student1.getFirstName(), responseStudent1.getFirstName());
        assertEquals(student1.getLastName(), responseStudent1.getLastName());
        assertEquals(student1.getGroup().getId(), responseStudent1.getGroupId());

        assertEquals(student2.getId(), responseStudent2.getId());
        assertEquals(student2.getFirstName(), responseStudent2.getFirstName());
        assertEquals(student2.getLastName(), responseStudent2.getLastName());
        assertEquals(student2.getGroup().getId(), responseStudent2.getGroupId());

    }
}