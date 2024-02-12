package org.example;

import org.example.entity.Course;
import org.example.entity.Group;
import org.example.entity.Student;
import org.example.factory.ConnectionFactory;
import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.StudentService;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DatabaseSetupManager {
    private FileReader reader;
    private ConnectionFactory factory;
    private StudentService studentService;
    private CourseService courseService;
    private GroupService groupService;

    public DatabaseSetupManager(FileReader reader, ConnectionFactory factory, StudentService studentService, CourseService courseService, GroupService groupService) {
        this.reader = reader;
        this.factory = factory;

        this.studentService = studentService;
        this.courseService = courseService;
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
        Random random = new Random();

        for (int i = 1; i < 11; i++) {
            int numberStudents = random.nextInt(0, 30);
            List<Integer> students = new ArrayList<>(numberStudents);

            for (int j = 0; j < numberStudents; j++) {
                int studentId = random.nextInt(1, 201);
                if (!students.contains(studentId)) {
                    students.add(studentId);
                }
            }

            studentService.addStudentsOnCourse(i, students.stream().map(id -> new Student(id)).toList());
        }
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
