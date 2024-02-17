package org.example.command;

import org.example.service.CourseService;
import org.example.service.StudentService;

import java.util.Scanner;

public class RemoveStudentCourseCommand implements Command {
    private final static String COMMAND_NAME = "Remove the student from one of their courses";
    private final static String STRING_COURSE_NAME = "Course Name: ";
    private final static String STRING_DESCRIPTION = "Description: ";
    private final static String STRING_CHOOSE_COURSE = "Choose course: ";
    private final static String STRING_WRITE_STUDENT_ID = "Write student id: ";
    private Scanner scanner;
    private StudentService studentService;
    private CourseService courseService;

    public RemoveStudentCourseCommand(StudentService studentService, CourseService courseService, Scanner scanner) {
        this.studentService = studentService;
        this.courseService = courseService;
        this.scanner = scanner;
    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void start() {
        scanner.nextLine();

        courseService.getAllCourses().stream().forEach(course ->
                System.out.println(STRING_COURSE_NAME + course.getName() + "\n" + STRING_DESCRIPTION + course.getDescription() + "\n"));

        System.out.print(STRING_CHOOSE_COURSE);
        int courseId = courseService.getCourseByName(scanner.nextLine()).getId();
        System.out.print(STRING_WRITE_STUDENT_ID);
        int studentId = scanner.nextInt();

        studentService.addStudentToCourse(courseId, studentId);
    }
}
