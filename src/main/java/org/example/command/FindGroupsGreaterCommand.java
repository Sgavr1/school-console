package org.example.command;

import org.example.service.GroupService;

import java.util.Scanner;

public class FindGroupsGreaterCommand implements Command {
    private final static String COMMAND_NAME = "Find all groups with less or equal students’ number";
    private final static String STRING_WRITE = "Write numbers: ";
    private GroupService groupService;
    private Scanner scanner;

    public FindGroupsGreaterCommand(GroupService groupService, Scanner scanner) {
        this.groupService = groupService;
        this.scanner = scanner;
    }

    @Override
    public String commandName() {
        return COMMAND_NAME;
    }

    @Override
    public void start() {
        System.out.print(STRING_WRITE);
        int numbers = scanner.nextInt();

        groupService.getGroupsGreaterOrEqualsStudents(numbers).stream().forEach(group -> System.out.println(group.getName()));
    }
}
