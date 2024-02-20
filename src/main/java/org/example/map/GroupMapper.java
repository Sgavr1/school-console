package org.example.map;

import org.example.entity.Group;

import java.sql.ResultSet;
import java.sql.SQLException;

public class GroupMapper {
    public Group map(ResultSet resultSet) throws SQLException {
        Group group = new Group();
        group.setName(resultSet.getString("group_name"));
        if (resultSet.wasNull()) {
            return null;
        }
        group.setId(resultSet.getInt("group_id"));

        return group;
    }
}
