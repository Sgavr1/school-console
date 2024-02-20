package org.example;

import org.example.command.*;
import org.example.factory.ConnectionFactory;
import org.example.factory.ServiceFactory;
import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.StudentService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ConsoleApplication {
    private static final String CHOOSE_COMMAND = "Choose a command: ";
    private static final String COMMAND_EXIT = "Exit";
    private Scanner scanner;
    private DatabaseSetupManager databaseSetupManager;
    private StudentService studentService;
    private GroupService groupService;
    private CourseService courseService;
    private Map<Integer, Command> commands;

    public ConsoleApplication(ServiceFactory serviceFactory, ConnectionFactory connectionFactory) {
        studentService = serviceFactory.getStudentService();
        groupService = serviceFactory.getGroupService();
        courseService = serviceFactory.getCourseService();

        databaseSetupManager = new DatabaseSetupManager(new FileReader(), connectionFactory, studentService, courseService, groupService);

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

    public void setupDatabase() {
        databaseSetupManager.dropTables();
        databaseSetupManager.createTable();
        databaseSetupManager.insertGroup();
        databaseSetupManager.insertCourses();
        databaseSetupManager.insertRandomStudents();
        databaseSetupManager.insertStudentCourse();
    }

    public void start() {
        while (true) {
            for (Map.Entry<Integer, Command> command : commands.entrySet()) {
                System.out.println(command.getKey() + ": " + command.getValue().commandLabel());
            }

            System.out.println("0: " + COMMAND_EXIT);
            System.out.print(CHOOSE_COMMAND);
            int numberCommand = scanner.nextInt();

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