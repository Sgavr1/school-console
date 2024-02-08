package org.example.Dao;

import org.example.ConnectionFactory;
import org.example.Entity.Student;
import org.example.Entity.StudentCourse;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentCourseDao {
    private ConnectionFactory factory;

    public StudentCourseDao(ConnectionFactory factory) {
        this.factory = factory;
    }

    public List<StudentCourse> getAllStudentByCourseId(int courseId) {
        List<StudentCourse> studentCourses = new ArrayList<>();
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        String query = "SELECT * FROM student_course WHERE course_id = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, courseId);

            resultSet = statement.executeQuery();

            while (resultSet.next()) {
                StudentCourse studentCourse = new StudentCourse();

                studentCourse.setStudentId(resultSet.getInt("student_id"));
                studentCourse.setCourseId(resultSet.getInt("course_id"));

                studentCourses.add(studentCourse);
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

        return studentCourses;
    }

    public void insert(StudentCourse studentCourse) {
        PreparedStatement statement = null;

        String query = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?);";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, studentCourse.getStudentId());
            statement.setInt(2, studentCourse.getCourseId());

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

    public void insertList(List<StudentCourse> studentCourses) {
        PreparedStatement statement = null;

        String query = "INSERT INTO student_course(student_id, course_id) VALUES (?, ?);";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);

            for (StudentCourse studentCourse : studentCourses) {
                statement.setInt(1, studentCourse.getStudentId());
                statement.setInt(2, studentCourse.getCourseId());

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

    public void deleteByStudent(int studentId) {
        PreparedStatement statement = null;

        String query = "DELETE FROM student_course WHERE student_id = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, studentId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteByCourse(int courseId) {
        PreparedStatement statement = null;

        String query = "DELETE FROM student_course WHERE student_id = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, courseId);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void deleteStudentCourse(StudentCourse studentCourse) {
        PreparedStatement statement = null;

        String query = "DELETE FROM student_course WHERE student_id = ? AND course_id = ?;";

        try (Connection connection = factory.getConnection()) {
            statement = connection.prepareStatement(query);
            statement.setInt(1, studentCourse.getStudentId());
            statement.setInt(2, studentCourse.getCourseId());

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
}
