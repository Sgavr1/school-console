package org.example;

import org.example.entity.Group;
import org.example.entity.Student;
import org.example.factory.ConnectionFactory;
import org.example.factory.ServiceFactory;
import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.StudentService;

import java.util.Scanner;

public class ConsoleApplication {
    private Scanner scanner;
    private DatabaseSetupManager databaseSetupManager;
    private StudentService studentService;
    private GroupService groupService;
    private CourseService courseService;

    public ConsoleApplication(ServiceFactory serviceFactory, ConnectionFactory connectionFactory) {
        studentService = serviceFactory.getStudentService();
        groupService = serviceFactory.getGroupService();
        courseService = serviceFactory.getCourseService();

        databaseSetupManager = new DatabaseSetupManager(new FileReader(), connectionFactory, studentService, courseService, groupService);

        scanner = new Scanner(System.in);
    }

    public void databaseInitialization() {
        databaseSetupManager.dropTables();
        databaseSetupManager.createTable();
        databaseSetupManager.insertGroup();
        databaseSetupManager.insertCourses();
        databaseSetupManager.insertRandomStudents();
        databaseSetupManager.insertStudentCourse();
    }

    public void start() {
        while (true) {
            System.out.println("1: Find all groups with less or equal students’ number");
            System.out.println("2: Find all students related to the course with the given name");
            System.out.println("3: Add a new student");
            System.out.println("4: Delete a student by the STUDENT_ID");
            System.out.println("5: Add a student to the course");
            System.out.println("6: Remove the student from one of their courses");
            System.out.println("7: Display all courses");
            System.out.println("8: Display all students");
            System.out.println("9: Exit");

            System.out.print("Choose a command: ");
            int command = scanner.nextInt();

            if (command == 9) {
                scanner.close();
                break;
            }

            if (command == 1) {
                findAllGroupsLessStudentsNumber();
            }

            if (command == 2) {
                findAllStudentsInCourse();
            }

            if (command == 3) {
                addStudent();
            }

            if (command == 4) {
                deleteStudent();
            }

            if (command == 5) {
                addStudentToCourse();
            }

            if (command == 6) {
                removeStudentFromCourse();
            }

            if (command == 7) {
                displayAllCourses();
            }

            if (command == 8) {
                displayAllStudents();
            }
        }
    }

    public void findAllGroupsLessStudentsNumber() {
        System.out.print("Write numbers: ");
        int numbers = scanner.nextInt();

        groupService.getGroupsGreaterOrEqualsStudents(numbers).stream().forEach(group -> System.out.println(group.getName()));
    }

    public void findAllStudentsInCourse() {
        scanner.nextLine();
        System.out.print("Write course name: ");
        String courseName = scanner.nextLine();

        studentService.getStudentsByCourseName(courseName).stream().forEach(student -> System.out.println(student.getLastName() + " " + student.getFirstName()));
    }

    public void addStudent() {
        Student student = new Student();

        System.out.print("Write first name: ");
        student.setFirstName(scanner.next());
        System.out.print("Write last name: ");
        student.setLastName(scanner.next());

        groupService.getGroups().stream().forEach(group -> System.out.println("Group name: " + group.getName()));

        Group group = new Group();
        do {
            System.out.print("Choose a group: ");
            group.setName(scanner.next());
            group = groupService.getGroupByName(group.getName());
            if (group == null) {
                System.out.println("Group name is not valid");
            }
        } while (group == null);

        student.setGroupId(group.getId());

        studentService.addStudent(student);

    }

    public void deleteStudent() {
        Student student = new Student();

        do {
            System.out.print("Write student id: ");
            student.setId(scanner.nextInt());

            student = studentService.getStudentById(student.getId());

            if (student == null) {
                System.out.println("Unfaithful ID");
            }

        } while (student == null);

        studentService.delete(student);
    }

    public void addStudentToCourse() {
        scanner.nextLine();

        displayAllCourses();

        System.out.println("Choose course: ");
        int courseId = courseService.getCourseByName(scanner.nextLine()).getId();
        System.out.println("Write student id: ");
        int studentId = scanner.nextInt();

        studentService.addStudentToCourse(studentId, courseId);
    }

    public void removeStudentFromCourse() {
        scanner.nextLine();

        displayAllCourses();

        System.out.print("Choose course: ");
        int courseId = courseService.getCourseByName(scanner.nextLine()).getId();
        System.out.print("Write student id: ");
        int studentId = scanner.nextInt();

        studentService.addStudentToCourse(courseId, studentId);
    }

    public void displayAllCourses() {
        courseService.getAllCourses().stream().forEach(course ->
                System.out.println("Course Name: " + course.getName() + "\nDescription: " + course.getDescription() + "\n"));
    }

    public void displayAllStudents() {
        studentService.getStudents().stream().forEach(student ->
                System.out.println(String.format("Id: %-5d  First Name: %-10s Last Name: %-10s", student.getId(), student.getFirstName(), student.getLastName())));
    }
}
