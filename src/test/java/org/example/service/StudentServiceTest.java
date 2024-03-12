package org.example.service;

import org.example.configuration.MapperConfiguration;
import org.example.dao.StudentDao;
import org.example.dto.StudentDto;
import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {StudentService.class, MapperConfiguration.class})
public class StudentServiceTest {
    private static final String STUDENT_1_FIRST_NAME = "Юлия";
    private static final String STUDENT_1_LAST_NAME = "Мельник";
    private static final String STUDENT_2_FIRST_NAME = "Артем";
    private static final String STUDENT_2_LAST_NAME = "Литвиненко";
    private static final String COURSE_NAME = "Вступ до програмування";
    @MockBean
    private StudentDao studentDao;
    @Autowired
    private StudentService studentService;

    @Test
    public void shouldStudentWhenCorrectStudentId() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName(STUDENT_1_FIRST_NAME);
        student.setLastName(STUDENT_1_LAST_NAME);
        student.setGroupId(1);

        Mockito.when(studentDao.getById(1)).thenReturn(Optional.of(student));

        StudentDto response = studentService.getStudentById(1);

        verify(studentDao).getById(1);

        assertEquals(1, response.getId());
        assertEquals(1, response.getGroupId());
        assertEquals(STUDENT_1_FIRST_NAME, response.getFirstName());
        assertEquals(STUDENT_1_LAST_NAME, response.getLastName());
    }

    @Test
    public void shouldGetByCourseName() {
        List<Student> students = new ArrayList<>();

        Student student1 = new Student(1);
        student1.setGroupId(4);
        student1.setFirstName(STUDENT_1_FIRST_NAME);
        student1.setLastName(STUDENT_1_LAST_NAME);

        students.add(student1);

        Student student2 = new Student(2);
        student2.setGroupId(7);
        student2.setFirstName(STUDENT_2_FIRST_NAME);
        student2.setLastName(STUDENT_2_LAST_NAME);

        students.add(student2);

        Mockito.when(studentDao.getStudentsByCourseName(COURSE_NAME)).thenReturn(students);

        List<StudentDto> responses = studentService.getStudentsByCourseName(COURSE_NAME);

        verify(studentDao).getStudentsByCourseName(COURSE_NAME);

        StudentDto responseStudent1 = responses.get(0);
        StudentDto responseStudent2 = responses.get(1);

        assertEquals(student1.getId(), responseStudent1.getId());
        assertEquals(student1.getFirstName(), responseStudent1.getFirstName());
        assertEquals(student1.getLastName(), responseStudent1.getLastName());
        assertEquals(student1.getGroupId(), responseStudent1.getGroupId());

        assertEquals(student2.getId(), responseStudent2.getId());
        assertEquals(student2.getFirstName(), responseStudent2.getFirstName());
        assertEquals(student2.getLastName(), responseStudent2.getLastName());
        assertEquals(student2.getGroupId(), responseStudent2.getGroupId());
    }

    @Test
    public void shouldAddStudent() {
        StudentDto student = new StudentDto();

        studentService.addStudent(student);

        verify(studentDao).insert(any(Student.class));
    }

    @Test
    public void shouldAddStudents() {
        List<StudentDto> students = new ArrayList<>();

        studentService.addStudents(students);

        verify(studentDao).insertList(anyList());
    }

    @Test
    public void shouldAddStudentToCourse() {
        studentService.addStudentToCourse(1, 1);

        verify(studentDao).insertStudentToCourse(1, 1);
    }

    @Test
    public void shouldAddStudentsToCourse() {
        List<StudentDto> students = new ArrayList<>();

        studentService.addStudentsToCourse(1, students);

        verify(studentDao).insertListStudentsOnCourse(anyInt(), anyList());
    }

    @Test
    public void shouldDeletedStudent() {
        StudentDto student = StudentDto.builder().id(1).build();

        studentService.delete(student);

        verify(studentDao).delete(1);
    }

    @Test
    public void shouldListAllStudents() {
        List<Student> students = new ArrayList<>();
        Student student1 = new Student(1);
        student1.setGroupId(4);
        student1.setFirstName(STUDENT_1_FIRST_NAME);
        student1.setLastName(STUDENT_2_LAST_NAME);
        students.add(student1);

        Student student2 = new Student(2);
        student2.setGroupId(7);
        student2.setFirstName(STUDENT_2_FIRST_NAME);
        student2.setLastName(STUDENT_2_LAST_NAME);
        students.add(student2);

        Mockito.when(studentDao.getAll()).thenReturn(students);

        List<StudentDto> responses = studentService.getStudents();

        verify(studentDao).getAll();

        StudentDto responseStudent1 = responses.get(0);
        StudentDto responseStudent2 = responses.get(1);

        assertEquals(student1.getId(), responseStudent1.getId());
        assertEquals(student1.getFirstName(), responseStudent1.getFirstName());
        assertEquals(student1.getLastName(), responseStudent1.getLastName());
        assertEquals(student1.getGroupId(), responseStudent1.getGroupId());

        assertEquals(student2.getId(), responseStudent2.getId());
        assertEquals(student2.getFirstName(), responseStudent2.getFirstName());
        assertEquals(student2.getLastName(), responseStudent2.getLastName());
        assertEquals(student2.getGroupId(), responseStudent2.getGroupId());

    }
}