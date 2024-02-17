package org.example.command;

import org.example.entity.Group;
import org.example.entity.Student;
import org.example.service.GroupService;
import org.example.service.StudentService;

import java.util.Scanner;

public class AddStudentCommand implements Command {
    private final static String COMMAND_NAME = "Add a new student";
    private final static String STRING_WRITE_FIRST_NAME = "Write first name: ";
    private final static String STRING_WRITE_LAST_NAME = "Write last name: ";
    private final static String STRING_GROUP_NAME = "Group name: ";
    private final static String STRING_CHOOSE_GROUP = "Choose a group: ";
    private final static String STRING_ERROR = "Group name is not valid";
    private Scanner scanner;
    private GroupService groupService;
    private StudentService studentService;

    public AddStudentCommand(GroupService groupService, StudentService studentService, Scanner scanner) {
        this.groupService = groupService;
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

        System.out.print(STRING_WRITE_FIRST_NAME);
        student.setFirstName(scanner.next());
        System.out.print(STRING_WRITE_LAST_NAME);
        student.setLastName(scanner.next());

        groupService.getGroups().stream().forEach(group -> System.out.println(STRING_GROUP_NAME + group.getName()));

        Group group = new Group();
        do {
            System.out.print(STRING_CHOOSE_GROUP);
            group.setName(scanner.next());
            group = groupService.getGroupByName(group.getName());
            if (group == null) {
                System.out.println(STRING_ERROR);
            }
        } while (group == null);

        student.setGroupId(group.getId());

        studentService.addStudent(student);

    }
}
