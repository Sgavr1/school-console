package org.example.dao;

import org.example.entity.Student;
import org.example.factory.ConnectionFactory;
import org.example.entity.Group;
import org.example.map.GroupMapper;
import org.example.map.StudentMapper;

import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private static final String QUERY_INSERT = "INSERT INTO groups(group_name) VALUES(?);";
    private static final String QUERY_SELECT_ALL = """
            SELECT groups.*, students.*
            FROM groups
            LEFT JOIN students ON students.group_id = groups.group_id;
            """;
    private static final String QUERY_SELECT_BY_NAME = """
            SELECT groups.*, students.*
            FROM groups
            LEFT JOIN students ON students.group_id = groups.group_id
            WHERE groups.group_name = 'SQ-06';
            """;
    private static final String QUERY_SELECT_LARGE_OR_EQUALS_STUDENT = """
            SELECT groups.*, s.*
            FROM groups
            LEFT JOIN students ON students.group_id = groups.group_id
            LEFT JOIN students as s ON s.group_id = groups.group_id
            GROUP BY group_name, groups.group_id, s.student_id
            HAVING count(students.student_id) >= ?;
            """;
    private ConnectionFactory factory;
    private GroupMapper groupMapper;
    private StudentMapper studentMapper;

    public GroupDao(ConnectionFactory factory, GroupMapper groupMapper, StudentMapper studentMapper) {
        this.factory = factory;
        this.groupMapper = groupMapper;
        this.studentMapper = studentMapper;
    }

    public List<Group> getGroupGreaterOrEqualsStudents(int studentsAmount) {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_LARGE_OR_EQUALS_STUDENT)) {
            statement.setInt(1, studentsAmount);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    Group mapGroup = groupMapper.map(resultSet);
                    Group group = groups.stream().filter(g -> g.getId() == mapGroup.getId()).findFirst().orElseGet(() -> {
                        groups.add(mapGroup);
                        return mapGroup;
                    });

                    Student student = studentMapper.map(resultSet);
                    if (student != null) {
                        group.getStudents().add(student);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public void insert(Group group) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            statement.setString(1, group.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertList(List<Group> groups) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT)) {
            for (Group group : groups) {
                statement.setString(1, group.getName());

                statement.addBatch();
            }

            statement.executeBatch();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL)) {
            while (resultSet.next()) {
                Group mapGroup = groupMapper.map(resultSet);
                Group group = groups.stream().filter(g -> g.getId() == mapGroup.getId()).findFirst().orElseGet(() -> {
                    groups.add(mapGroup);
                    return mapGroup;
                });

                Student student = studentMapper.map(resultSet);
                if (student != null) {
                    group.getStudents().add(student);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public Optional<Group> getGroupByName(String name) {

        Group group = null;

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_NAME)) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group = groupMapper.map(resultSet);
                    do {
                        group.getStudents().add(studentMapper.map(resultSet));
                    } while (resultSet.next());
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(group);
    }
}