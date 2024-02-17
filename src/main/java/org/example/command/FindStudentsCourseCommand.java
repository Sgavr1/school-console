package org.example.command;

import org.example.service.StudentService;

import java.util.Scanner;

public class FindStudentsCourseCommand implements Command {
    private final static String COMMAND_NAME = "Find all students related to the course with the given name";
    private final static String STRING_WRITE_COURSE_NAME = "Write course name: ";
    private Scanner scanner;
    private StudentService studentService;

    public FindStudentsCourseCommand(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void start() {
        scanner.nextLine();
        System.out.print(STRING_WRITE_COURSE_NAME);
        String courseName = scanner.nextLine();

        studentService.getStudentsByCourseName(courseName).stream().forEach(student -> System.out.println(student.getLastName() + " " + student.getFirstName()));
    }
}
