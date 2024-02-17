package org.example.map;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EntityMapper {
    public Student mapStudent(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Student student = fillStudent(resultSet);

            do {
                Course course = fillCourse(resultSet);
                if (course != null) {
                    student.getCourses().add(course);
                }
            }while (resultSet.first());

            return student;
        }

        return null;
    }

    public List<Student> mapStudents(ResultSet resultSet) throws SQLException {
        List<Student> students = new ArrayList<>();

        while (resultSet.next()) {
            Student student = fillStudent(resultSet);
            Student finalStudent = student;

            student = students.stream().filter(s -> s.getId() == finalStudent.getId()).findFirst().orElseGet(() -> {
                students.add(finalStudent);
                return finalStudent;
            });

            Course course = fillCourse(resultSet);
            if (course != null) {
                student.getCourses().add(course);
            }
        }

        return students;
    }

    public Course mapCourse(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            Course course = fillCourse(resultSet);

            do {
                Student student = fillStudent(resultSet);
                if (student != null) {
                    course.getStudents().add(student);
                }
            } while (resultSet.next());

            return course;
        }

        return null;
    }

    public List<Course> mapCourses(ResultSet resultSet) throws SQLException {
        List<Course> courses = new ArrayList<>();

        while (resultSet.next()) {
            Course course = fillCourse(resultSet);
            Course finalCourse = course;

            course = courses.stream().filter(c -> c.getId() == finalCourse.getId()).findFirst().orElseGet(() -> {
                courses.add(finalCourse);
                return finalCourse;
            });

            Student student = fillStudent(resultSet);
            if (student != null) {
                course.getStudents().add(student);
            }
        }

        return courses;
    }

    public Group mapGroup(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
           Group group = fillGroup(resultSet);

            do {
                Student student = fillStudent(resultSet);
                if (student != null) {
                    group.getStudents().add(student);
                }
            } while (resultSet.next());

            return group;
        }

        return null;
    }

    public List<Group> mapGroups(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();

        while (resultSet.next()) {
            Group group = fillGroup(resultSet);
            Group finalGroup = group;

            group = groups.stream().filter(g -> g.getId() == finalGroup.getId()).findFirst().orElseGet(() -> {
                groups.add(finalGroup);
                return finalGroup;
            });

            Student student = fillStudent(resultSet);
            if (student != null) {
                group.getStudents().add(student);
            }
        }

        return groups;
    }

    private Student fillStudent(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setFirstName(resultSet.getString("first_name"));
        if (resultSet.wasNull()) {
            return null;
        }
        student.setId(resultSet.getInt("student_id"));
        student.setLastName(resultSet.getString("last_name"));
        student.setGroupId(resultSet.getInt("group_id"));

        return student;
    }

    private Course fillCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();
        course.setName(resultSet.getString("course_name"));
        if (resultSet.wasNull()) {
            return null;
        }
        course.setId(resultSet.getInt("course_id"));
        course.setDescription(resultSet.getString("course_description"));

        return course;
    }

    private Group fillGroup(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setName(resultSet.getString("group_name"));
        if(resultSet.wasNull()){
            return  null;
        }
        group.setId(resultSet.getInt("group_id"));

        return group;
    }
}