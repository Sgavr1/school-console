package org.example.dao.mapper;

import org.example.entity.Group;
import org.example.entity.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupRowMapper implements RowMapper<Group> {
    private RowMapper<Student> studentMapper;

    public void setStudentMapper(RowMapper<Student> studentMapper) {
        this.studentMapper = studentMapper;
    }

    @Override
    public Group mapRow(ResultSet rs, int rowNum) throws SQLException {
        Group group = new Group();
        group.setName(rs.getString("group_name"));
        if (rs.wasNull()) {
            return null;
        }
        group.setId(rs.getInt("group_id"));

        Student student = studentMapper.mapRow(rs, rowNum);
        if (student != null) {
            group.getStudents().add(student);
        }

        return group;
    }
}
