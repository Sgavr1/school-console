package org.example.map;

import org.example.entity.Group;
import org.example.entity.Student;

import java.util.ArrayList;
import java.util.List;
import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper {
    private StudentMapper studentMapper;

    public void setStudentMapper(StudentMapper studentMapper) {
        this.studentMapper = studentMapper;
    }

    public Group map(ResultSet resultSet) throws SQLException {
        Group group = null;
        if (resultSet.next()) {
            group = fill(resultSet);
            do {
                group.getStudents().add(studentMapper.map(resultSet));
            } while (resultSet.next());
        }

        return group;
    }

    public List<Group> mapGroups(ResultSet resultSet) throws SQLException {
        List<Group> groups = new ArrayList<>();
        while (resultSet.next()) {
            Group mapGroup = fill(resultSet);
            Group group = groups.stream().filter(g -> g.getId() == mapGroup.getId()).findFirst().orElseGet(() -> {
                groups.add(mapGroup);
                return mapGroup;
            });

            Student student = studentMapper.fill(resultSet);
            if (student != null) {
                group.getStudents().add(student);
            }
        }
        return groups;
    }

    public Group fill(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setName(resultSet.getString("group_name"));
        if (resultSet.wasNull()) {
            return null;
        }
        group.setId(resultSet.getInt("group_id"));

        return group;
    }
}
