package org.example.dao;

import org.example.entity.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.JdbcTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@JdbcTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JdbcGroupDaoTest {
    private static final String INSERT_GROUP_1_NAME = "CV-01";
    private static final String INSERT_GROUP_2_NAME = "CD-05";
    private static final String GROUP_BY_ID_1_NAME = "AV-01";
    private static final String GROUP_BY_ID_2_NAME = "AB-04";
    @Autowired
    private JdbcTemplate template;
    @Autowired
    private RowMapper<Group> groupMapper;
    private GroupDao groupDao;
    private Group group1;
    private Group group2;

    @BeforeAll
    public void init() {
        groupDao = new JdbcGroupDao(template, groupMapper);

        group1 = new Group();
        group1.setName(INSERT_GROUP_1_NAME);

        group2 = new Group();
        group2.setName(INSERT_GROUP_2_NAME);
    }

    @Test
    public void shouldGetGroupGreaterOrEqualsStudents() {
        List<Group> groups = groupDao.getGroupGreaterOrEqualsStudents(2);

        assertEquals(1, groups.size());
    }

    @Test
    public void shouldInsert() {
        groupDao.insert(group1);

        List<Group> groups = groupDao.getAll();

        assertEquals(true, groups.stream().filter(g -> g.getName().equals(group1.getName())).findFirst().isPresent());
    }

    @Test
    public void shouldInsertList() {
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        groupDao.insertList(groups);

        List<Group> responseGroups = groupDao.getAll();

        assertEquals(true, responseGroups.stream().filter(g -> g.getName().equals(group1.getName())).findFirst().isPresent());
        assertEquals(true, responseGroups.stream().filter(g -> g.getName().equals(group2.getName())).findFirst().isPresent());
    }

    @Test
    public void shouldGetAll() {
        List<Group> responseGroups = groupDao.getAll();

        assertEquals(true, responseGroups.stream().filter(g -> g.getName().equals(GROUP_BY_ID_1_NAME)).findFirst().isPresent());
        assertEquals(true, responseGroups.stream().filter(g -> g.getName().equals(GROUP_BY_ID_2_NAME)).findFirst().isPresent());
    }

    @Test
    public void shouldGetGroupByName() {
        Optional<Group> optionalGroup = groupDao.getGroupByName(GROUP_BY_ID_1_NAME);

        assertEquals(true, optionalGroup.isPresent());

        Group group = optionalGroup.get();

        assertEquals(1, group.getId());
        assertEquals(GROUP_BY_ID_1_NAME, group.getName());
    }
}