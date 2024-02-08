package org.example.Dao;

import org.example.ConnectionFactory;
import org.example.Entity.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StudentDao {
    private ConnectionFactory factory;

    public StudentDao(ConnectionFactory factory) {
        this.factory = factory;
    }

    public void insert(Student student) {
        PreparedStatement statement = null;

        String query = "INSERT INTO students(first_name, last_name, group_id) VALUES (?, ?, ?)";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);

            statement.setString(1, student.getFirstName());
            statement.setString(2, student.getLastName());
            statement.setInt(3, student.getGroupId());

            statement.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void insertList(List<Student> students) {
        PreparedStatement statement = null;

        String query = "INSERT INTO students(first_name, last_name, group_id) VALUES (?, ?, ?)";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);

            for (Student student : students) {
                statement.setString(1, student.getFirstName());
                statement.setString(2, student.getLastName());
                statement.setInt(3, student.getGroupId());

                statement.executeUpdate();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (statement != null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Optional<Student> getById(int id) {
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        Student student = null;

        String query = "SELECT * FROM students WHERE student_id = ?";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                student = new Student();
                student.setId(resultSet.getInt("student_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));
                student.setGroupId(resultSet.getInt("group_id"));
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

        return Optional.ofNullable(student);
    }

    public void delete(int id) {
        PreparedStatement statement = null;

        String query = "DELETE FROM students WHERE student_id = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);

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

    public List<Student> getAll() {
        List<Student> students = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;

        String query = "SELECT * FROM students;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                Student student = new Student();
                student.setId(resultSet.getInt("student_id"));
                student.setGroupId(resultSet.getInt("group_id"));
                student.setFirstName(resultSet.getString("first_name"));
                student.setLastName(resultSet.getString("last_name"));

                students.add(student);
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

        return students;
    }
}
