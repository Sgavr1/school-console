package org.example.Dao;

import org.example.ConnectionFactory;
import org.example.Entity.Group;

import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;

public class GroupDao {
    private ConnectionFactory factory;

    public GroupDao(ConnectionFactory factory) {
        this.factory = factory;
    }

    public List<Group> getGroupLargeOrEqualsStudents(int numbers) {
        List<Group> groups = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT groups.group_id, group_name " +
                "FROM groups JOIN students ON groups.group_id = students.group_id " +
                "GROUP BY group_name, groups.group_id " +
                "HAVING count(student_id) >= ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, numbers);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("group_name"));

                groups.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return groups;
    }

    public void insert(Group group) {
        PreparedStatement statement = null;
        String query = "INSERT INTO groups(group_name) VALUES(?);";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setString(1, group.getName());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void insertList(List<Group> groups) {

        PreparedStatement statement = null;
        String query = "INSERT INTO groups(group_name) VALUES(?);";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            for (Group group : groups) {
                statement.setString(1, group.getName());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Group> getAll() {
        List<Group> groups = new ArrayList<>();

        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM groups;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.createStatement();

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Group group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("group_name"));

                groups.add(group);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return groups;
    }

    public Optional<Group> getGroupByName(String name) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Group group = null;

        String query = "SELECT * FROM groups WHERE group_name = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setString(1, name);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                group = new Group();
                group.setId(resultSet.getInt("group_id"));
                group.setName(resultSet.getString("group_name"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return Optional.ofNullable(group);
    }
}
