package org.example.command;

import org.example.service.StudentService;

public class DisplayStudentsCommand implements Command{
    private final static String COMMAND_NAME = "Display all students";
    private StudentService studentService;

    public DisplayStudentsCommand(StudentService studentService){
        this.studentService = studentService;
    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void start() {
        studentService.getStudents().stream().forEach(student ->
                System.out.println(String
                        .format("Id: %-5d  First Name: %-10s Last Name: %-10s",
                                student.getId(), student.getFirstName(), student.getLastName())));
    }
}
