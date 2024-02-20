package org.example.command;

import org.example.entity.Student;
import org.example.service.StudentService;

import java.util.Scanner;

public class DeleteStudentCommand implements Command {
    private static final String COMMAND_LABEL = "Delete a student by the STUDENT_ID";
    private static final String WRITE_STUDENT_ID = "Write student id: ";
    private static final String ERROR = "Unfaithful ID";
    private Scanner scanner;
    private StudentService studentService;

    public DeleteStudentCommand(StudentService studentService, Scanner scanner) {
        this.studentService = studentService;
        this.scanner = scanner;
    }

    @Override
    public String commandLabel() {
        return COMMAND_LABEL;
    }

    @Override
    public void start() {
        Student student = new Student();

        do {
            System.out.print(WRITE_STUDENT_ID);
            student.setId(scanner.nextInt());

            student = studentService.getStudentById(student.getId());

            if (student == null) {
                System.out.println(ERROR);
            }

        } while (student == null);

        studentService.delete(student);
    }
}
