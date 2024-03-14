package org.example;

import org.example.command.*;
import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.StudentService;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@Component
public class ConsoleApplication {
    private static final String CHOOSE_COMMAND = "Choose a command: ";
    private static final String COMMAND_EXIT = "Exit";
    private Scanner scanner;
    private Map<Integer, Command> commands;

    public ConsoleApplication(StudentService studentService, GroupService groupService, CourseService courseService) {
        scanner = new Scanner(System.in);
        commands = new HashMap<>();

        commands.put(1, new AddStudentCommand(groupService, studentService, scanner));
        commands.put(2, new AddStudentCourseCommand(studentService, courseService, scanner));
        commands.put(3, new DeleteStudentCommand(studentService, scanner));
        commands.put(4, new DisplayStudentsCommand(studentService));
        commands.put(5, new FindGroupsLessCommand(groupService, scanner));
        commands.put(6, new FindStudentsCourseCommand(studentService, scanner));
        commands.put(7, new RemoveStudentCourseCommand(studentService, courseService, scanner));
    }

    public void start() {
        int numberCommand;

        while (true) {
            for (Map.Entry<Integer, Command> command : commands.entrySet()) {
                System.out.println(command.getKey() + ": " + command.getValue().commandLabel());
            }

            System.out.println("0: " + COMMAND_EXIT);
            System.out.print(CHOOSE_COMMAND);
            numberCommand = scanner.nextInt();

            if (numberCommand == 0) {
                break;
            } else {
                if (commands.containsKey(numberCommand)) {
                    commands.get(numberCommand).start();
                }
            }
        }
    }
}