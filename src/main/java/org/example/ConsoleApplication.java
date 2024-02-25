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
    private static final String BEGIN_MESSAGE = "Fill the database? (Be careful, if there is already data in the database, it is better to choose \"no\")";
    private static final String COMMAND_YES = "1 - YES";
    private static final String COMMAND_NO = "2 - NO";
    private Scanner scanner;
    private final DatabaseSetupManager databaseSetupManager;
    private Map<Integer, Command> commands;

    public ConsoleApplication(DatabaseSetupManager setupManager, StudentService studentService, GroupService groupService, CourseService courseService) {
        this.databaseSetupManager = setupManager;

        scanner = new Scanner(System.in);
        commands = new HashMap<>();

        commands.put(1, new AddStudentCommand(groupService, studentService, scanner));
        commands.put(2, new AddStudentCourseCommand(studentService, courseService, scanner));
        commands.put(3, new DeleteStudentCommand(studentService, scanner));
        commands.put(4, new DisplayStudentsCommand(studentService));
        commands.put(5, new FindGroupsGreaterCommand(groupService, scanner));
        commands.put(6, new FindStudentsCourseCommand(studentService, scanner));
        commands.put(7, new RemoveStudentCourseCommand(studentService, courseService, scanner));
    }

    private void setupDatabase() {
        databaseSetupManager.insertGroup();
        databaseSetupManager.insertCourses();
        databaseSetupManager.insertRandomStudents();
        databaseSetupManager.insertStudentCourse();
    }

    public void start() {
        System.out.println(BEGIN_MESSAGE);
        System.out.println(COMMAND_YES);
        System.out.println(COMMAND_NO);

        int numberCommand = scanner.nextInt();

        if (numberCommand == 1) {
            setupDatabase();
        }

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