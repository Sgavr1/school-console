package org.example.factory;

import org.example.dao.CourseDao;
import org.example.dao.GroupDao;
import org.example.dao.StudentDao;
import org.example.map.CourseMapper;
import org.example.map.GroupMapper;
import org.example.map.StudentMapper;

public class DaoFactory {
    private ConnectionFactory connectionFactory;
    private StudentMapper studentMapper;
    private CourseMapper courseMapper;
    private GroupMapper groupMapper;

    public DaoFactory(ConnectionFactory connectionFactory, StudentMapper studentMapper, CourseMapper courseMapper, GroupMapper groupMapper) {
        this.connectionFactory = connectionFactory;
        this.studentMapper = studentMapper;
        this.courseMapper = courseMapper;
        this.groupMapper = groupMapper;
    }

    public DaoFactory(ConnectionFactory connectionFactory) {
        this.connectionFactory = connectionFactory;
        this.studentMapper = new StudentMapper();
        this.courseMapper = new CourseMapper();
        this.groupMapper = new GroupMapper();
    }

    public StudentDao getStudentDao() {
        return new StudentDao(connectionFactory, studentMapper, courseMapper);
    }

    public GroupDao getGroupDao() {
        return new GroupDao(connectionFactory, groupMapper, studentMapper);
    }

    public CourseDao getCourseDao() {
        return new CourseDao(connectionFactory, courseMapper, studentMapper);
    }
}