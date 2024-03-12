package org.example.service;

import org.example.configuration.MapperConfiguration;
import org.example.dao.GroupDao;
import org.example.dto.GroupDto;
import org.example.entity.Group;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {GroupService.class, MapperConfiguration.class})
public class GroupServiceTest {

    private static final String GROUP_NAME_1 = "AB-01";
    private static final String GROUP_NAME_2 = "SA-02";
    @MockBean
    private GroupDao groupDao;
    @Autowired
    private GroupService groupService;

    @Test
    public void shouldGroupWhenCorrectGroupName() {
        Group group = new Group();
        group.setId(1);
        group.setName(GROUP_NAME_1);

        when(groupDao.getGroupByName(GROUP_NAME_1)).thenReturn(Optional.of(group));

        GroupDto response = groupService.getGroupByName(GROUP_NAME_1);

        verify(groupDao).getGroupByName(GROUP_NAME_1);

        assertEquals(GROUP_NAME_1, response.getName());
        assertEquals(group.getId(), response.getId());
    }

    @Test
    public void shouldListGroupWhenNumbersStudentGreaterOrEqualsNumber() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        group.setId(1);
        group.setName(GROUP_NAME_1);
        groups.add(group);

        group = new Group();
        group.setId(2);
        group.setName(GROUP_NAME_2);
        groups.add(group);

        Mockito.when(groupDao.getGroupGreaterOrEqualsStudents(5)).thenReturn(groups);

        List<GroupDto> response = groupService.getGroupsGreaterOrEqualsStudents(5);

        verify(groupDao).getGroupGreaterOrEqualsStudents(5);

        GroupDto responseGroup1 = response.get(0);
        GroupDto responseGroup2 = response.get(1);

        assertEquals(1, responseGroup1.getId());
        assertEquals(GROUP_NAME_1, responseGroup1.getName());
        assertEquals(2, responseGroup2.getId());
        assertEquals(GROUP_NAME_2, responseGroup2.getName());
    }

    @Test
    public void shouldAddGroup() {
        GroupDto group = new GroupDto();

        groupService.addGroup(group);

        verify(groupDao).insert(any(Group.class));

    }

    @Test
    public void shouldAddGroups() {
        List<GroupDto> groups = new ArrayList<>();

        groupService.addGroups(groups);

        verify(groupDao).insertList(anyList());
    }

    @Test
    public void shouldListAllGroups() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group(GROUP_NAME_1);
        group.setId(1);

        groups.add(group);

        group = new Group(GROUP_NAME_2);
        group.setId(2);

        groups.add(group);

        Mockito.when(groupDao.getAll()).thenReturn(groups);

        List<GroupDto> response = groupService.getGroups();

        verify(groupDao).getAll();

        GroupDto responseGroup1 = response.get(0);
        GroupDto responseGroup2 = response.get(1);

        assertEquals(1, responseGroup1.getId());
        assertEquals(GROUP_NAME_1, responseGroup1.getName());

        assertEquals(2, responseGroup2.getId());
        assertEquals(GROUP_NAME_2, responseGroup2.getName());
    }
}