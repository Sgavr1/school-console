package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dao.GroupDao;
import org.example.dto.GroupDto;
import org.example.mapper.GroupMapper;
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

    @Transactional
    public List<GroupDto> getGroupsLessOrEqualsStudents(int studentsAmount) {
        return groupDao.getGroupLessOrEqualsStudents(studentsAmount).stream().map(groupMapper::toDto).toList();
    }

    @Transactional
    public void addGroup(GroupDto group) {
        groupDao.insert(groupMapper.toEntity(group));
    }

    @Transactional
    public void addGroups(List<GroupDto> groups) {
        groupDao.insertList(groups.stream().map(groupMapper::toEntity).toList());
    }

    @Transactional
    public List<GroupDto> getGroups() {
        return groupDao.getAll().stream().map(groupMapper::toDto).toList();
    }

    @Transactional
    public GroupDto getGroupByName(String name) {
        return groupDao.getGroupByName(name).map(groupMapper::toDto).orElse(null);
    }

    @Transactional
    public boolean isEmpty() {
        return groupDao.isEmptyTable();
    }
}