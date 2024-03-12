package org.example.service;

import org.example.configuration.MapperConfiguration;
import org.example.dao.CourseDao;
import org.example.dto.CourseDto;
import org.example.dto.StudentDto;
import org.example.entity.Course;
import org.example.entity.Student;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {CourseService.class, MapperConfiguration.class})
public class CourseServiceTest {

    private static final String COURSE_NAME = "Вступ до програмування";
    private static final String COURSE_DESCRIPTION = "Основи програмування для початківців.";
    private static final String STUDENT_1_FIRST_NAME = "Людмила";
    private static final String STUDENT_1_LAST_NAME = "Мельник";
    private static final String STUDENT_2_FIRST_NAME = "Андрей";
    private static final String STUDENT_2_LAST_NAME = "Григоренко";

    @MockBean
    private CourseDao courseDao;
    @Autowired
    private CourseService courseService;

    @Test
    public void shouldCourseWhenCorrectCourseName() {
        Course correctCourse = new Course();
        correctCourse.setName(COURSE_NAME);
        correctCourse.setDescription(COURSE_DESCRIPTION);

        when(courseDao.getByName(COURSE_NAME)).thenReturn(Optional.of(correctCourse));

        CourseDto response = courseService.getCourseByName(COURSE_NAME);

        verify(courseDao).getByName(COURSE_NAME);

        assertEquals(correctCourse.getName(), response.getName());
        assertEquals(correctCourse.getDescription(), response.getDescription());
    }

    @Test
    public void shouldAddCourse() {
        CourseDto course = new CourseDto();

        courseService.addCourse(course);

        verify(courseDao).insert(any(Course.class));
    }

    @Test
    public void shouldAddCourses() {
        List<CourseDto> courses = new ArrayList<>();

        courseService.addCourses(courses);

        verify(courseDao).insertList(anyList());
    }

    @Test
    public void shouldListAllCourses() {
        List<Course> courses = new ArrayList<>();
        Course course = new Course();
        course.setId(1);
        course.setName(COURSE_NAME);
        course.setDescription(COURSE_DESCRIPTION);
        courses.add(course);

        Student student = new Student(1);
        student.setGroupId(1);
        student.setFirstName(STUDENT_1_FIRST_NAME);
        student.setLastName(STUDENT_1_LAST_NAME);

        course.getStudents().add(student);

        student = new Student(2);
        student.setGroupId(3);
        student.setFirstName(STUDENT_2_FIRST_NAME);
        student.setLastName(STUDENT_2_LAST_NAME);

        course.getStudents().add(student);

        Mockito.when(courseDao.getAll()).thenReturn(courses);

        List<CourseDto> response = courseService.getAllCourses();

        CourseDto responseCourse = response.get(0);
        StudentDto responseStudent1 = responseCourse.getStudents().get(0);
        StudentDto responseStudent2 = responseCourse.getStudents().get(1);

        verify(courseDao).getAll();

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