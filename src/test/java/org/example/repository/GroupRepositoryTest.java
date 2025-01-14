package org.example.repository;

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

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {
        GroupRepository.class
}))
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Sql(scripts = {"/sql/clear_table.sql", "/sql/insert_table.sql"})
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class GroupRepositoryTest {
    private static final String INSERT_GROUP_1_NAME = "CV-01";
    private static final String INSERT_GROUP_2_NAME = "CD-05";
    private static final String GROUP_BY_ID_1_NAME = "AV-01";
    private static final String GROUP_BY_ID_2_NAME = "AB-04";
    @Autowired
    private GroupRepository groupRepository;
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
    public void shouldGetGroupLessOrEqualsStudents() throws SQLException {
        List<Group> groups = groupRepository.findGroupLessOrEqualsStudents(2);

        assertEquals(2, groups.size());
    }

    @Test
    public void shouldGetGroupByName() throws SQLException {
        Optional<Group> optionalGroup = groupRepository.findByName(GROUP_BY_ID_1_NAME);

        assertTrue(optionalGroup.isPresent());

        Group group = optionalGroup.get();

        assertEquals(1, group.getId());
        assertEquals(GROUP_BY_ID_1_NAME, group.getName());
    }
}