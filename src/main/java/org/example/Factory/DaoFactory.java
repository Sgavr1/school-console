package org.example.Factory;

import org.example.ConnectionFactory;
import org.example.Dao.CourseDao;
import org.example.Dao.GroupDao;
import org.example.Dao.StudentCourseDao;
import org.example.Dao.StudentDao;

public class DaoFactory {
    private ConnectionFactory connectionFactory;

    public DaoFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
    }

    public StudentDao getStudentDao() {
        return new StudentDao(connectionFactory);
    }

    public GroupDao getGroupDao() {
        return new GroupDao(connectionFactory);
    }

    public StudentCourseDao getStudentCourseDao() {
        return new StudentCourseDao(connectionFactory);
    }

    public CourseDao getCourseDao() {
        return new CourseDao(connectionFactory);
    }
}