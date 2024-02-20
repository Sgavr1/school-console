package org.example.map;

import org.example.entity.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentMapper {
    public Student map(ResultSet resultSet) throws SQLException {
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
