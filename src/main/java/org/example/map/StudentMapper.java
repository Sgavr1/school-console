package org.example.map;

import org.example.entity.Course;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper {
    private CourseMapper courseMapper;

    public void setCourseMapper(CourseMapper courseMapper) {
        this.courseMapper = courseMapper;
    }

    public Student map(ResultSet resultSet) throws SQLException {
        Student student = null;
        if (resultSet.next()) {
            student = fill(resultSet);
            do {
                student.getCourses().add(courseMapper.fill(resultSet));
            } while (resultSet.next());
        }

        return student;
    }

    public List<Student> mapStudents(ResultSet resultSet) throws SQLException {
        List<Student> students = new ArrayList<>();

        while (resultSet.next()) {
            Student mapStudent = fill(resultSet);
            Student student = students.stream().filter(s -> s.getId() == mapStudent.getId()).findFirst().orElseGet(() -> {
                students.add(mapStudent);
                return mapStudent;
            });

            Course course = courseMapper.fill(resultSet);
            if (course != null) {
                student.getCourses().add(course);
            }
        }

        return students;
    }

    public Student fill(ResultSet resultSet) throws SQLException {
        Student student = new Student();
        student.setFirstName(resultSet.getString("first_name"));
        if (resultSet.wasNull()) {
            return null;
        }
        student.setId(resultSet.getInt("student_id"));
        student.setLastName(resultSet.getString("last_name"));
        student.setGroupId(resultSet.getInt("group_id"));

        return student;
    }
}
