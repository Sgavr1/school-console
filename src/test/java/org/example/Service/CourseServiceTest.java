package org.example.Service;

import org.example.Dao.CourseDao;
import org.example.Entity.Course;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CourseServiceTest {

    @Test
    public void getCourseWhenCorrectCourseName() {
        CourseDao courseDao = Mockito.mock(CourseDao.class);

        Course correctCourse = new Course();
        correctCourse.setName("Вступ до програмування");
        correctCourse.setDescription("Основи програмування для початківців.");

        Mockito.when(courseDao.getByName("Вступ до програмування")).thenReturn(Optional.of(correctCourse));

        CourseService courseService = new CourseService(courseDao);

        Course response = courseService.getCourseByName("Вступ до програмування");

        verify(courseDao).getByName("Вступ до програмування");

        assertEquals(correctCourse.getName(), response.getName());
        assertEquals(correctCourse.getDescription(), response.getDescription());
    }
}