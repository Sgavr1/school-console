package org.example.service;

import org.example.configuration.MapperConfiguration;
import org.example.repository.StudentRepository;
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
    private StudentRepository studentRepository;
    @Autowired
    private StudentMapper mapper;
    private StudentService studentService;

    @BeforeAll
    public void init() {
        studentService = new StudentService(studentRepository, mapper);
    }

    @Test
    public void shouldReturnStudentWhenCorrectStudentId() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName(STUDENT_1_FIRST_NAME);
        student.setLastName(STUDENT_1_LAST_NAME);
        student.setGroup(new Group(1));

        Mockito.when(studentRepository.findById(1)).thenReturn(Optional.of(student));

        StudentDto response = studentService.getStudentById(1);

        verify(studentRepository).findById(1);

        assertEquals(1, response.getId());
        assertEquals(1, response.getGroupId());
        assertEquals(STUDENT_1_FIRST_NAME, response.getFirstName());
        assertEquals(STUDENT_1_LAST_NAME, response.getLastName());
    }

    @Test
    public void shouldReturnByCourseName() throws SQLException {
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

        Mockito.when(studentRepository.findAllStudentByCourseName(COURSE_NAME)).thenReturn(students);

        List<StudentDto> responses = studentService.getStudentsByCourseName(COURSE_NAME);

        verify(studentRepository).findAllStudentByCourseName(COURSE_NAME);

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

        verify(studentRepository).save(any(Student.class));
    }

    @Test
    public void shouldAddStudents() {
        List<StudentDto> students = new ArrayList<>();

        studentService.addStudents(students);

        verify(studentRepository).saveAll(anyList());
    }

    @Test
    public void shouldAddStudentToCourse() throws SQLException {
        studentService.addStudentToCourse(1, 1);

        verify(studentRepository).saveStudentOnCourse(1, 1);
    }

    @Test
    public void shouldAddStudentsToCourse() throws SQLException {
        List<StudentDto> students = new ArrayList<>();
        students.add(StudentDto.builder().build());

        studentService.addStudentsToCourse(1, students);

        verify(studentRepository).saveStudentOnCourse(anyInt(), anyInt());
    }

    @Test
    public void shouldDeletedStudent() {
        StudentDto student = StudentDto.builder().id(1).build();

        studentService.delete(student);

        verify(studentRepository).deleteById(any());
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

        Mockito.when(studentRepository.findAll()).thenReturn(students);

        List<StudentDto> responses = studentService.getStudents();

        verify(studentRepository).findAll();

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