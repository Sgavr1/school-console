package org.example.command;

import org.example.service.StudentService;

import java.util.Scanner;

public class FindStudentsCourseCommand implements Command {
    private static final String COMMAND_LABEL = "Find all students related to the course with the given name";
    private static final String WRITE_COURSE_NAME = "Write course name: ";
    private Scanner scanner;
    private StudentService studentService;

    public FindStudentsCourseCommand(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public String commandLabel() {
        return COMMAND_LABEL;
    }

    @Override
    public void start() {
        scanner.nextLine();
        System.out.print(WRITE_COURSE_NAME);
        String courseName = scanner.nextLine();

        studentService.getStudentsByCourseName(courseName).stream().forEach(student -> System.out.println(student.getLastName() + " " + student.getFirstName()));
    }
}
