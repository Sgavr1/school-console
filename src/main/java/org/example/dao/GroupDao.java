package org.example.dao;

import org.example.factory.ConnectionFactory;
import org.example.entity.Group;
import org.example.map.EntityMapper;

import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GroupDao {
    private final static String QUERY_SELECT_LARGE_OR_EQUALS_STUDENT = """
            SELECT groups.group_id, group_name
            FROM groups JOIN students ON groups.group_id = students.group_id
            GROUP BY group_name, groups.group_id
            HAVING count(student_id) >= ?;
            """;
    private final static String QUERY_INSERT = "INSERT INTO groups(group_name) VALUES(?);";
    private final static String QUERY_SELECT_ALL = "SELECT * FROM groups;";
    private final static String QUERY_SELECT_BY_NAME = "SELECT * FROM groups WHERE group_name = ?;";
    private ConnectionFactory factory;
    private EntityMapper mapper;

    public GroupDao(ConnectionFactory factory, EntityMapper mapper) {
        this.factory = factory;
        this.mapper = mapper;
    }

    public List<Group> getGroupGreaterOrEqualsStudents(int amountStudents) {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = factory.getConnection().prepareStatement(QUERY_SELECT_LARGE_OR_EQUALS_STUDENT);) {
            statement.setInt(1, amountStudents);

            try (ResultSet resultSet = statement.executeQuery();) {
                while (resultSet.next()) {
                    groups.add(mapper.map(resultSet, Group.class));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public void insert(Group group) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT);) {
            statement.setString(1, group.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertList(List<Group> groups) {
        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_INSERT);) {
            connection.setAutoCommit(false);

            for (Group group : groups) {
                statement.setString(1, group.getName());

                statement.addBatch();
            }

            statement.executeBatch();

            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();

        try (Connection connection = factory.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(QUERY_SELECT_ALL);) {
            while (resultSet.next()) {
                groups.add(mapper.map(resultSet, Group.class));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groups;
    }

    public Optional<Group> getGroupByName(String name) {

        Group group = null;

        try (Connection connection = factory.getConnection();
             PreparedStatement statement = connection.prepareStatement(QUERY_SELECT_BY_NAME);) {
            statement.setString(1, name);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    group = mapper.map(resultSet, Group.class);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.ofNullable(group);
    }
}