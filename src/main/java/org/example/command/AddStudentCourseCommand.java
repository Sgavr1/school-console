package org.example.command;

import org.example.service.CourseService;
import org.example.service.StudentService;

import java.util.Scanner;

public class AddStudentCourseCommand implements Command {
    private static final String COMMAND_LABEL = "Add a student to the course";
    private static final String COURSE_NAME = "Course Name: ";
    private static final String DESCRIPTION = "Description: ";
    private static final String CHOOSE_COURSE = "Choose course: ";
    private static final String WRITE_STUDENT_ID = "Write student id: ";
    private Scanner scanner;
    private StudentService studentService;
    private CourseService courseService;

    public AddStudentCourseCommand(StudentService studentService, CourseService courseService, Scanner scanner) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.scanner = scanner;
    }

    @Override
    public String commandLabel() {
        return COMMAND_LABEL;
    }

    @Override
    public void start() {
        scanner.nextLine();

        courseService.getAllCourses().stream().forEach(course ->
                System.out.println(COURSE_NAME + course.getName() + "\n" + DESCRIPTION + course.getDescription() + "\n"));

        System.out.println(CHOOSE_COURSE);
        int courseId = courseService.getCourseByName(scanner.nextLine()).getId();
        System.out.println(WRITE_STUDENT_ID);
        int studentId = scanner.nextInt();

        studentService.addStudentToCourse(studentId, courseId);
    }
}
