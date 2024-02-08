package org.example;

import org.example.Entity.Course;
import org.example.Entity.Group;
import org.example.Entity.Student;
import org.example.Entity.StudentCourse;
import org.example.Service.CourseService;
import org.example.Service.GroupService;
import org.example.Service.StudentCourseService;
import org.example.Service.StudentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DatabaseSetup {
    private FileReader reader;
    private ConnectionFactory factory;
    private StudentService studentService;
    private CourseService courseService;
    private GroupService groupService;
    private StudentCourseService studentCourseService;

    public DatabaseSetup(FileReader reader, ConnectionFactory factory, StudentService studentService, CourseService courseService, GroupService groupService, StudentCourseService studentCourseService) {
        this.reader = reader;
        this.factory = factory;

        this.studentService = studentService;
        this.courseService = courseService;
        this.studentCourseService = studentCourseService;
        this.groupService = groupService;
    }

    public void createTable() {
        dropTables();

        FileReader reader = new FileReader();
        Statement statement = null;
        try (Connection connection = factory.getConnection()) {
            statement = connection.createStatement();

            statement.execute(reader.read("src/main/resources/createTables.sql"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void dropTables() {
        Statement statement = null;
        try (Connection connection = factory.getConnection()) {
            statement = connection.createStatement();
            statement.execute("Drop Table If exists students Cascade");
            statement.execute("Drop Table If exists courses Cascade");
            statement.execute("Drop Table If exists groups Cascade");
            statement.execute("Drop Table If exists student_course Cascade");

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertRandomStudents() {
        FileReader fileReader = new FileReader();

        String[] firstNames = fileReader.read("src/main/resources/firstNames.txt").split("\n");
        String[] lastNames = fileReader.read("src/main/resources/lastNames.txt").split("\n");

        List<Student> students = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            Student student = new Student();
            student.setFirstName(firstNames[random.nextInt(0, firstNames.length)]);
            student.setLastName(lastNames[random.nextInt(0, lastNames.length)]);
            student.setGroupId(random.nextInt(1, 11));

            students.add(student);
        }

        studentService.addStudents(students);
    }

    public void insertStudentCourse() {
        List<StudentCourse> studentCourses = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            StudentCourse studentCourse = new StudentCourse();
            studentCourse.setStudentId(i + 1);
            studentCourse.setCourseId(random.nextInt(1, 11));

            studentCourses.add(studentCourse);
        }

        studentCourseService.addStudentToCourses(studentCourses);
    }

    public void insertCourses() {
        FileReader fileReader = new FileReader();

        String[] coursesString = fileReader.read("src/main/resources/Courses.txt").split("\n");

        List<Course> courses = new ArrayList<>();

        for (int i = 0; i < coursesString.length; i++) {
            Course course = new Course();
            course.setName(coursesString[i].split("_")[0]);
            course.setDescription(coursesString[i].split("_")[1]);

            courses.add(course);
        }

        courseService.addCourses(courses);
    }

    public void insertGroup() {
        String[] groupsNames = reader.read("src/main/resources/Groups.txt").split("\n");

        groupService.addGroups(Arrays.stream(groupsNames).map(str -> new Group(str)).toList());

    }
}
