package org.example;

import org.example.dto.CourseDto;
import org.example.dto.GroupDto;
import org.example.dto.StudentDto;
import org.example.service.CourseService;
import org.example.service.GroupService;
import org.example.service.StudentService;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Component
public class DatabaseSetupManager {
    private final FileReader reader;
    private final StudentService studentService;
    private final CourseService courseService;
    private final GroupService groupService;

    public DatabaseSetupManager(FileReader reader, StudentService studentService, CourseService courseService, GroupService groupService) {
        this.reader = reader;

        this.studentService = studentService;
        this.courseService = courseService;
        this.groupService = groupService;
    }

    public void insertRandomStudents() {
        FileReader fileReader = new FileReader();

        String[] firstNames = fileReader.read("src/main/resources/firstNames.txt").split("\n");
        String[] lastNames = fileReader.read("src/main/resources/lastNames.txt").split("\n");

        List<StudentDto> students = new ArrayList<>();

        Random random = new Random();
        for (int i = 0; i < 200; i++) {
            students.add(StudentDto.builder()
                    .firstName(firstNames[random.nextInt(0, firstNames.length)])
                    .lastName(lastNames[random.nextInt(0, lastNames.length)])
                    .groupId(random.nextInt(1, 11))
                    .build());
        }

        studentService.addStudents(students);
    }

    public void insertStudentCourse() {
        Random random = new Random();

        for (int i = 1; i < 11; i++) {
            int numberStudents = random.nextInt(0, 50);
            List<Integer> students = new ArrayList<>(numberStudents);

            for (int j = 0; j < numberStudents; j++) {
                int studentId = random.nextInt(1, 201);
                if (!students.contains(studentId)) {
                    students.add(studentId);
                }
            }

            studentService.addStudentsOnCourse(i, students.stream()
                    .map(id -> StudentDto.builder().id(id).build()).toList());
        }
    }

    public void insertCourses() {
        FileReader fileReader = new FileReader();

        String[] coursesString = fileReader.read("src/main/resources/Courses.txt").split("\n");

        List<CourseDto> courses = new ArrayList<>();

        for (int i = 0; i < coursesString.length; i++) {
            courses.add(CourseDto.builder()
                    .name(coursesString[i].split("_")[0])
                    .description(coursesString[i].split("_")[1])
                    .build());
        }

        courseService.addCourses(courses);
    }

    public void insertGroup() {
        String[] groupsNames = reader.read("src/main/resources/Groups.txt").split("\n");

        groupService.addGroups(Arrays.stream(groupsNames).map(str -> GroupDto.builder().name(str).build()).toList());

    }
}
