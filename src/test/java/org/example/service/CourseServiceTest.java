package org.example.service;

import org.checkerframework.framework.qual.DefaultQualifier;
import org.example.dao.CourseDao;
import org.example.dao.StudentDao;
import org.example.entity.Course;
import org.example.entity.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    private final static String COURSE_NAME = "Вступ до програмування";
    private final static String COURSE_DESCRIPTION = "Основи програмування для початківців.";
    private final static String STUDENT_1_FIRST_NAME = "Людмила";
    private final static String STUDENT_1_LAST_NAME = "Мельник";
    private final static String STUDENT_2_FIRST_NAME = "Андрей";
    private final static String STUDENT_2_LAST_NAME = "Григоренко";

    private CourseDao courseDao;
    private StudentDao studentDao;
    private CourseService courseService;

    @BeforeEach
    public void init() {
        courseDao = Mockito.mock(CourseDao.class);
        studentDao = Mockito.mock(StudentDao.class);
        courseService = new CourseService(courseDao, studentDao);
    }

    @Test
    public void getCourseWhenCorrectCourseName() {
        Course correctCourse = new Course();
        correctCourse.setName(COURSE_NAME);
        correctCourse.setDescription(COURSE_DESCRIPTION);

        Mockito.when(courseDao.getByName(COURSE_NAME)).thenReturn(Optional.of(correctCourse));

        Course response = courseService.getCourseByName(COURSE_NAME);

        verify(courseDao).getByName(COURSE_NAME);

        assertEquals(correctCourse.getName(), response.getName());
        assertEquals(correctCourse.getDescription(), response.getDescription());
    }

    @Test
    public void verifyCourseAddedToDatabase() {
        Course course = new Course();

        courseService.addCourse(course);

        verify(courseDao).insert(course);
    }

    @Test
    public void verifyCoursesAddedToDatabase() {
        List<Course> courses = new ArrayList<>();

        courseService.addCourses(courses);

        verify(courseDao).insertList(courses);
    }

    @Test
    public void testListAllCourses() {
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setId(1);
        course.setName(COURSE_NAME);
        course.setDescription(COURSE_DESCRIPTION);
        courses.add(course);

        List<Student> students = new ArrayList<>();

        Student student = new Student(1);
        student.setGroupId(1);
        student.setFirstName(STUDENT_1_FIRST_NAME);
        student.setLastName(STUDENT_1_LAST_NAME);

        students.add(student);

        student = new Student(2);
        student.setGroupId(3);
        student.setFirstName(STUDENT_2_FIRST_NAME);
        student.setLastName(STUDENT_2_LAST_NAME);

        students.add(student);

        Mockito.when(studentDao.getStudentsByCourseId(1)).thenReturn(students);

        Mockito.when(courseDao.getAll()).thenReturn(courses);

        List<Course> response = courseService.getAllCourses();

        Course responseCourse = response.get(0);
        Student responseStudent1 = responseCourse.getStudents().get(0);
        Student responseStudent2 = responseCourse.getStudents().get(1);

        verify(courseDao).getAll();
        verify(studentDao).getStudentsByCourseId(1);

        assertEquals(COURSE_NAME, responseCourse.getName());
        assertEquals(COURSE_DESCRIPTION, responseCourse.getDescription());

        assertEquals(1, responseStudent1.getId());
        assertEquals(1, responseStudent1.getGroupId());
        assertEquals(STUDENT_1_FIRST_NAME, responseStudent1.getFirstName());
        assertEquals(STUDENT_1_LAST_NAME, responseStudent1.getLastName());

        assertEquals(2, responseStudent2.getId());
        assertEquals(3, responseStudent2.getGroupId());
        assertEquals(STUDENT_2_FIRST_NAME, responseStudent2.getFirstName());
        assertEquals(STUDENT_2_LAST_NAME, responseStudent2.getLastName());
    }

}