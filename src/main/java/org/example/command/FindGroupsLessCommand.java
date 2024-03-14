package org.example.command;

import org.example.service.GroupService;

import java.util.Scanner;

public class FindGroupsLessCommand implements Command{
    private static final String COMMAND_LABEL = "Find all groups with less or equal students’ number";
    private static final String WRITE = "Write numbers: ";
    private GroupService groupService;
    private Scanner scanner;

    public FindGroupsLessCommand(GroupService groupService, Scanner scanner) {
        this.groupService = groupService;
        this.scanner = scanner;
    }

    @Override
    public String commandLabel() {
        return COMMAND_LABEL;
    }

    @Override
    public void start() {
        System.out.print(WRITE);
        int numbers = scanner.nextInt();

        groupService.getGroupsLessOrEqualsStudents(numbers).stream().forEach(group -> System.out.println(group.getName()));
    }
}
