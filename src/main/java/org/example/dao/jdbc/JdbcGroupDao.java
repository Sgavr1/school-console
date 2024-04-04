package org.example.dao.jdbc;

import org.example.dao.GroupDao;
import org.example.entity.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository("JDBCGroup")
public class JdbcGroupDao implements GroupDao {
    private final Logger logger = LoggerFactory.getLogger(JdbcGroupDao.class);
    private static final String QUERY_CHECK_EMPTY_TABLE = "Select count(group_id) From groups;";
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
            WHERE groups.group_name = ?;
            """;
    private static final String QUERY_SELECT_LESS_OR_EQUALS_STUDENT = """
            SELECT groups.*, s.*
            FROM groups
            LEFT JOIN students ON students.group_id = groups.group_id
            LEFT JOIN students as s ON s.group_id = groups.group_id
            GROUP BY group_name, groups.group_id, s.student_id
            HAVING count(students.student_id) <= ?;
            """;
    private final JdbcTemplate template;
    private final RowMapper<Group> groupMapper;

    public JdbcGroupDao(JdbcTemplate template, RowMapper<Group> groupMapper) {
        this.template = template;
        this.groupMapper = groupMapper;
    }

    @Override
    public List<Group> getGroupLessOrEqualsStudents(int studentsAmount) {
        try {
            return buildUniqueList(template.query(QUERY_SELECT_LESS_OR_EQUALS_STUDENT, groupMapper, studentsAmount));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void insert(Group group) {
        try {
            template.update(QUERY_INSERT, group.getName());
        } catch (DataAccessException e) {
            logger.error("Error insert group: name = %s", group.getName());
            e.printStackTrace();
        }
    }

    @Override
    public void insertList(List<Group> groups) {
        try {
            template.batchUpdate(QUERY_INSERT, new BatchPreparedStatementSetter() {
                @Override
                public void setValues(PreparedStatement ps, int i) throws SQLException {
                    ps.setString(1, groups.get(i).getName());
                }

                @Override
                public int getBatchSize() {
                    return groups.size();
                }
            });
        } catch (DataAccessException e) {
            logger.error("Error insert list groups");
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAll() {
        try {
            return buildUniqueList(template.query(QUERY_SELECT_ALL, groupMapper));
        } catch (EmptyResultDataAccessException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public Optional<Group> getGroupByName(String name) {
        try {
            List<Group> groups = buildUniqueList(template.query(QUERY_SELECT_BY_NAME, groupMapper, name));
            if (groups.isEmpty()) {
                return Optional.empty();
            }
            return Optional.of(groups.get(0));
        } catch (EmptyResultDataAccessException e) {
            logger.warn("Not found group by name = %s", name);
            return Optional.empty();
        }
    }

    @Override
    public boolean isEmptyTable() {
        return template.queryForObject(QUERY_CHECK_EMPTY_TABLE, Integer.class) == 0;
    }

    private List<Group> buildUniqueList(List<Group> groups) {
        List<Group> uniqueList = new ArrayList<>();

        groups.stream().forEach(group ->
                uniqueList.stream().filter(g -> g.getId() == group.getId()).findFirst().ifPresentOrElse(
                        g -> g.getStudents().add(group.getStudents().get(0)),
                        () -> uniqueList.add(group)
                ));

        return uniqueList;
    }
}