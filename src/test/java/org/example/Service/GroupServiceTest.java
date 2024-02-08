package org.example.Service;

import org.example.Dao.GroupDao;
import org.example.Entity.Group;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GroupServiceTest {

    @Test
    public void getGroupWhenCorrectGroupName() {
        GroupDao groupDao = Mockito.mock(GroupDao.class);

        Group group = new Group();
        group.setId(1);
        group.setName("AB-01");

        Mockito.when(groupDao.getGroupByName("AB-01")).thenReturn(Optional.of(group));

        GroupService groupService = new GroupService(groupDao);

        Group response = groupService.getGroupByName("AB-01");

        verify(groupDao).getGroupByName("AB-01");

        assertEquals(group.getName(), response.getName());
        assertEquals(group.getId(), response.getId());
    }

    @Test
    public void getListGroupWhenNumbersStudentLargeOrEqualsNumber() {
        List<Group> groups = new ArrayList<>();
        Group group = new Group();
        group.setId(1);
        group.setName("AB-01");
        groups.add(group);

        group = new Group();
        group.setId(2);
        group.setName("SA-02");
        groups.add(group);

        GroupDao groupDao = Mockito.mock(GroupDao.class);

        Mockito.when(groupDao.getGroupLargeOrEqualsStudents(5)).thenReturn(groups);

        GroupService groupService = new GroupService(groupDao);

        List<Group> response = groupService.getGroupLargeStudent(5);

        verify(groupDao).getGroupLargeOrEqualsStudents(5);

        assertEquals(groups.get(0).getId(), response.get(0).getId());
        assertEquals(groups.get(0).getName(), response.get(0).getName());
        assertEquals(groups.get(1).getId(), response.get(1).getId());
        assertEquals(groups.get(1).getName(), response.get(1).getName());
    }
}