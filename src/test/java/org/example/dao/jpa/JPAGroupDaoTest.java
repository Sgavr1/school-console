package org.example.dao.jpa;

import org.example.dao.GroupDao;
import org.example.entity.Group;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        JPAGroupDao.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JPAGroupDaoTest {
    private static final String INSERT_GROUP_1_NAME = "CV-01";
    private static final String INSERT_GROUP_2_NAME = "CD-05";
    private static final String GROUP_BY_ID_1_NAME = "AV-01";
    private static final String GROUP_BY_ID_2_NAME = "AB-04";
    @Autowired
    private GroupDao groupDao;
    private Group group1;
    private Group group2;

    @BeforeAll
    public void init() {
        group1 = new Group();
        group1.setName(INSERT_GROUP_1_NAME);

        group2 = new Group();
        group2.setName(INSERT_GROUP_2_NAME);
    }

    @Test
    public void shouldGetGroupLessOrEqualsStudents() {
        List<Group> groups = groupDao.getGroupLessOrEqualsStudents(2);

        assertEquals(2, groups.size());
    }

    @Test
    public void shouldInsert() {
        groupDao.insert(group1);

        List<Group> groups = groupDao.getAll();

        boolean presentGroup = groups.stream().filter(g -> g.getName().equals(group1.getName())).findFirst().isPresent();

        assertTrue(presentGroup);
    }

    @Test
    public void shouldInsertList() {
        List<Group> groups = new ArrayList<>();
        groups.add(group1);
        groups.add(group2);

        groupDao.insertList(groups);

        List<Group> responseGroups = groupDao.getAll();

        boolean presentGroup1 = responseGroups.stream().filter(g -> g.getName().equals(group1.getName())).findFirst().isPresent();
        boolean presentGroup2 = responseGroups.stream().filter(g -> g.getName().equals(group2.getName())).findFirst().isPresent();

        assertTrue(presentGroup1);
        assertTrue(presentGroup2);
    }

    @Test
    public void shouldGetAll() {
        List<Group> responseGroups = groupDao.getAll();

        boolean presentGroup1 = responseGroups.stream().filter(g -> g.getName().equals(GROUP_BY_ID_1_NAME)).findFirst().isPresent();
        boolean presentGroup2 = responseGroups.stream().filter(g -> g.getName().equals(GROUP_BY_ID_2_NAME)).findFirst().isPresent();

        assertTrue(presentGroup1);
        assertTrue(presentGroup2);
    }

    @Test
    public void shouldGetGroupByName() {
        Optional<Group> optionalGroup = groupDao.getGroupByName(GROUP_BY_ID_1_NAME);

        assertTrue(optionalGroup.isPresent());

        Group group = optionalGroup.get();

        assertEquals(1, group.getId());
        assertEquals(GROUP_BY_ID_1_NAME, group.getName());
    }
}