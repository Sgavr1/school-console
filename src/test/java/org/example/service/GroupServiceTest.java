package org.example.service;

import org.example.dao.GroupDao;
import org.example.entity.Group;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    private final static String GROUP_NAME_1 = "AB-01";
    private final static String GROUP_NAME_2 = "SA-02";
    private GroupDao groupDao;
    private GroupService groupService;

    @BeforeEach
    public void init() {
        groupDao = Mockito.mock(GroupDao.class);
        groupService = new GroupService(groupDao);
    }

    @Test
    public void getGroupWhenCorrectGroupName() {
        Group group = new Group();
        group.setId(1);
        group.setName(GROUP_NAME_1);

        Mockito.when(groupDao.getGroupByName(GROUP_NAME_1)).thenReturn(Optional.of(group));

        Group response = groupService.getGroupByName(GROUP_NAME_1);

        verify(groupDao).getGroupByName(GROUP_NAME_1);

        assertEquals(GROUP_NAME_1, response.getName());
        assertEquals(group.getId(), response.getId());
    }

    @Test
    public void getListGroupWhenNumbersStudentGreaterOrEqualsNumber() {
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

        List<Group> response = groupService.getGroupsGreaterOrEqualsStudents(5);

        verify(groupDao).getGroupGreaterOrEqualsStudents(5);

        Group responseGroup1 = response.get(0);
        Group responseGroup2 = response.get(1);

        assertEquals(1, responseGroup1.getId());
        assertEquals(GROUP_NAME_1, responseGroup1.getName());
        assertEquals(2, responseGroup2.getId());
        assertEquals(GROUP_NAME_2, responseGroup2.getName());
    }

    @Test
    public void shouldAddGroup() {
        Group group = new Group(GROUP_NAME_1);
        group.setId(1);

        groupService.addGroup(group);

        verify(groupDao).insert(group);

    }

    @Test
    public void shouldAddGroups() {
        List<Group> groups = new ArrayList<>();

        groupService.addGroups(groups);

        verify(groupDao).insertList(groups);
    }

    @Test
    public void testListAllGroups() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group(GROUP_NAME_1);
        group.setId(1);

        groups.add(group);

        group = new Group(GROUP_NAME_2);
        group.setId(2);

        groups.add(group);

        Mockito.when(groupDao.getAll()).thenReturn(groups);

        List<Group> response = groupService.getGroups();

        verify(groupDao).getAll();

        Group responseGroup1 = response.get(0);
        Group responseGroup2 = response.get(1);

        assertEquals(1, responseGroup1.getId());
        assertEquals(GROUP_NAME_1, responseGroup1.getName());

        assertEquals(2, responseGroup2.getId());
        assertEquals(GROUP_NAME_2, responseGroup2.getName());
    }
}