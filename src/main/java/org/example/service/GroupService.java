package org.example.service;

import org.example.dao.GroupDao;
import org.example.dto.GroupDto;
import org.example.map.dto.GroupMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupDao groupDao;
    private final GroupMapper groupMapper;

    public GroupService(GroupDao groupDao, GroupMapper groupMapper) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
    }

    public List<GroupDto> getGroupsGreaterOrEqualsStudents(int studentsAmount) {
        return groupDao.getGroupGreaterOrEqualsStudents(studentsAmount).stream().map(groupMapper::toDto).toList();
    }

    public void addGroup(GroupDto group) {
        groupDao.insert(groupMapper.toEntity(group));
    }

    public void addGroups(List<GroupDto> groups) {
        groupDao.insertList(groups.stream().map(groupMapper::toEntity).toList());
    }

    public List<GroupDto> getGroups() {
        return groupDao.getAll().stream().map(groupMapper::toDto).toList();
    }

    public GroupDto getGroupByName(String name) {
        return groupDao.getGroupByName(name).map(groupMapper::toDto).orElse(null);
    }

    public boolean isEmpty() {
        return groupDao.isEmptyTable();
    }
}