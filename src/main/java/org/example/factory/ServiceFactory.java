package org.example.factory;

import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.StudentService;

public class ServiceFactory {
    private DaoFactory daoFactory;

    public ServiceFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public CourseService getCourseService() {
        return new CourseService(daoFactory.getCourseDao(), daoFactory.getStudentDao());
    }

    public GroupService getGroupService() {
        return new GroupService(daoFactory.getGroupDao());
    }

    public StudentService getStudentService() {
        return new StudentService(daoFactory.getStudentDao(), daoFactory.getCourseDao());
    }
}
