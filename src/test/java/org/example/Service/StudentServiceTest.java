package org.example.Service;

import org.example.Dao.CourseDao;
import org.example.Dao.StudentCourseDao;
import org.example.Dao.StudentDao;
import org.example.Entity.Course;
import org.example.Entity.Student;
import org.example.Entity.StudentCourse;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class StudentServiceTest {

    @Test
    public void getStudentWhenCorrectStudentId() {
        Student student = new Student();
        student.setId(1);
        student.setFirstName("Юлия");
        student.setLastName("Мельник");
        student.setGroupId(1);

        StudentDao studentDao = Mockito.mock(StudentDao.class);

        Mockito.when(studentDao.getById(1)).thenReturn(Optional.of(student));

        StudentService studentService = new StudentService(studentDao, null, null);

        Student response = studentService.getStudentById(1);

        verify(studentDao).getById(1);

        assertEquals(student.getId(), response.getId());
        assertEquals(student.getGroupId(), response.getGroupId());
        assertEquals(student.getFirstName(), response.getFirstName());
        assertEquals(student.getLastName(), response.getLastName());
    }

    @Test
    public void getStudentsWhenCourseName() {
        Course course = new Course();
        course.setId(1);
        course.setName("Вступ до програмування");

        CourseDao courseDao = Mockito.mock(CourseDao.class);

        Mockito.when(courseDao.getByName("Вступ до програмування")).thenReturn(Optional.of(course));

        List<StudentCourse> studentCourses = new ArrayList<>();
        StudentCourse studentCourse = new StudentCourse();
        studentCourse.setStudentId(1);
        studentCourse.setCourseId(1);
        studentCourses.add(studentCourse);

        studentCourse = new StudentCourse();
        studentCourse.setStudentId(2);
        studentCourse.setCourseId(1);
        studentCourses.add(studentCourse);

        StudentCourseDao studentCourseDao = Mockito.mock(StudentCourseDao.class);

        Mockito.when(studentCourseDao.getAllStudentByCourseId(1)).thenReturn(studentCourses);

        StudentDao studentDao = Mockito.mock(StudentDao.class);

        Student student1 = new Student();
        student1.setId(1);
        student1.setGroupId(4);
        student1.setFirstName("Артем");
        student1.setLastName("Литвиненко");

        Mockito.when(studentDao.getById(1)).thenReturn(Optional.of(student1));

        Student student2 = new Student();
        student2.setId(2);
        student2.setGroupId(7);
        student2.setFirstName("Андрей");
        student2.setLastName("Коваленко");

        Mockito.when(studentDao.getById(2)).thenReturn(Optional.of(student2));

        StudentService studentService = new StudentService(studentDao, courseDao, studentCourseDao);

        List<Student> responses = studentService.getStudentsByCourseName("Вступ до програмування");

        verify(courseDao).getByName("Вступ до програмування");
        verify(studentCourseDao).getAllStudentByCourseId(1);
        verify(studentDao).getById(1);
        verify(studentDao).getById(2);

        assertEquals(responses.contains(student1), true);
        assertEquals(responses.contains(student2), true);
    }
}