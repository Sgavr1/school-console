package org.example.command;

import org.example.entity.Group;
import org.example.entity.Student;
import org.example.service.GroupService;
import org.example.service.StudentService;

import java.util.Scanner;

public class AddStudentCommand implements Command {
    private static final String COMMAND_LABEL = "Add a new student";
    private static final String WRITE_FIRST_NAME = "Write first name: ";
    private static final String WRITE_LAST_NAME = "Write last name: ";
    private static final String GROUP_NAME = "Group name: ";
    private static final String CHOOSE_GROUP = "Choose a group: ";
    private static final String ERROR = "Group name is not valid";
    private Scanner scanner;
    private GroupService groupService;
    private StudentService studentService;

    public AddStudentCommand(GroupService groupService, StudentService studentService, Scanner scanner) {
        this.groupService = groupService;
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

        System.out.print(WRITE_FIRST_NAME);
        student.setFirstName(scanner.next());
        System.out.print(WRITE_LAST_NAME);
        student.setLastName(scanner.next());

        groupService.getGroups().stream().forEach(group -> System.out.println(GROUP_NAME + group.getName()));

        Group group = new Group();
        do {
            System.out.print(CHOOSE_GROUP);
            group.setName(scanner.next());
            group = groupService.getGroupByName(group.getName());
            if (group == null) {
                System.out.println(ERROR);
            }
        } while (group == null);

        student.setGroupId(group.getId());

        studentService.addStudent(student);

    }
}
