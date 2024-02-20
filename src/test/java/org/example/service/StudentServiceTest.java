package org.example.service;

import org.example.dao.StudentDao;
import org.example.entity.Student;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class StudentServiceTest {
    private static final String STUDENT_1_FIRST_NAME = "Юлия";
    private static final String STUDENT_1_LAST_NAME = "Мельник";
    private static final String STUDENT_2_FIRST_NAME = "Артем";
    private static final String STUDENT_2_LAST_NAME = "Литвиненко";
    private static final String COURSE_NAME = "Вступ до програмування";
    private StudentDao studentDao;
    private StudentService studentService;

    @BeforeAll
    public void init() {
        studentDao = Mockito.mock(StudentDao.class);
        studentService = new StudentService(studentDao);
    }

    @Test
    public void getStudentWhenCorrectStudentId() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName(STUDENT_1_FIRST_NAME);
        student.setLastName(STUDENT_1_LAST_NAME);
        student.setGroupId(1);

        Mockito.when(studentDao.getById(1)).thenReturn(Optional.of(student));

        Student response = studentService.getStudentById(1);

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

        List<Student> responses = studentService.getStudentsByCourseName(COURSE_NAME);

        verify(studentDao).getStudentsByCourseName(COURSE_NAME);

        assertEquals(responses.contains(student1), true);
        assertEquals(responses.contains(student2), true);
    }

    @Test
    public void shouldAddStudent() {
        Student student = new Student();

        studentService.addStudent(student);

        verify(studentDao).insert(student);
    }

    @Test
    public void shouldAddStudents() {
        List<Student> students = new ArrayList<>();

        studentService.addStudents(students);

        verify(studentDao).insertList(students);
    }

    @Test
    public void shouldAddStudentOnCourse() {
        studentService.addStudentToCourse(1, 1);

        verify(studentDao).insertStudentToCourse(1, 1);
    }

    @Test
    public void shouldAddStudentsOnCourse() {
        List<Student> students = new ArrayList<>();

        studentService.addStudentsOnCourse(1, students);

        verify(studentDao).insertListStudentsOnCourse(1, students);
    }

    @Test
    public void shouldDeletedStudent() {
        Student student = new Student(1);

        studentService.delete(student);

        verify(studentDao).delete(1);
    }

    @Test
    public void testListAllStudents() {
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

        List<Student> response = studentService.getStudents();

        verify(studentDao).getAll();

        assertEquals(students.contains(response.get(0)), true);
        assertEquals(students.contains(response.get(1)), true);

    }
}