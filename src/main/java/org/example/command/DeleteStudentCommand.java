package org.example.command;

import org.example.entity.Student;
import org.example.service.StudentService;

import java.util.Scanner;

public class DeleteStudentCommand implements Command {
    private final static String COMMAND_NAME = "Delete a student by the STUDENT_ID";
    private final static String STRING_WRITE_STUDENT_ID = "Write student id: ";
    private final static String STRING_ERROR = "Unfaithful ID";
    private Scanner scanner;
    private StudentService studentService;

    public DeleteStudentCommand(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void start() {
        Student student = new Student();

        do {
            System.out.print(STRING_WRITE_STUDENT_ID);
            student.setId(scanner.nextInt());

            student = studentService.getStudentById(student.getId());

            if (student == null) {
                System.out.println(STRING_ERROR);
            }

        } while (student == null);

        studentService.delete(student);
    }
}
