package org.example.factory;

import org.example.dao.CourseDao;
import org.example.dao.GroupDao;
import org.example.dao.StudentDao;
import org.example.map.EntityMapper;

public class DaoFactory {
    private ConnectionFactory connectionFactory;
    private EntityMapper mapper;

    public DaoFactory(ConnectionFactory connectionFactory, EntityMapper mapper) {
        this.mapper = mapper;
        this.connectionFactory = connectionFactory;
    }

    public StudentDao getStudentDao() {
        return new StudentDao(connectionFactory, mapper);
    }

    public GroupDao getGroupDao() {
        return new GroupDao(connectionFactory, mapper);
    }

    public CourseDao getCourseDao() {
        return new CourseDao(connectionFactory, mapper);
    }
}