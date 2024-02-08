package org.example.Factory;

import org.example.Service.CourseService;
import org.example.Service.GroupService;
import org.example.Service.StudentCourseService;
import org.example.Service.StudentService;

public class ServiceFactory {
    private DaoFactory daoFactory;

    public ServiceFactory(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public CourseService getCourseService() {
        return new CourseService(daoFactory.getCourseDao());
    }

    public GroupService getGroupService() {
        return new GroupService(daoFactory.getGroupDao());
    }

    public StudentCourseService getStudentCourseService() {
        return new StudentCourseService(daoFactory.getStudentCourseDao());
    }

    public StudentService getStudentService() {
        return new StudentService(daoFactory.getStudentDao(), daoFactory.getCourseDao(), daoFactory.getStudentCourseDao());
    }
}
