package org.example.service;

import org.example.dao.GroupDao;
import org.example.dto.GroupDto;
import org.example.map.GroupDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GroupService {
    private final GroupDao groupDao;
    private final GroupDtoMapper groupDtoMapper;

    public GroupService(GroupDao groupDao, GroupDtoMapper groupDtoMapper) {
        this.groupDao = groupDao;
        this.groupDtoMapper = groupDtoMapper;
    }

    public List<GroupDto> getGroupsGreaterOrEqualsStudents(int studentsAmount) {
        return groupDao.getGroupGreaterOrEqualsStudents(studentsAmount).stream().map(groupDtoMapper::mapGroupToDto).toList();
    }

    public void addGroup(GroupDto group) {
        groupDao.insert(groupDtoMapper.mapDtoToGroup(group));
    }

    public void addGroups(List<GroupDto> groups) {
        groupDao.insertList(groups.stream().map(groupDtoMapper::mapDtoToGroup).toList());
    }

    public List<GroupDto> getGroups() {
        return groupDao.getAll().stream().map(groupDtoMapper::mapGroupToDto).toList();
    }

    public GroupDto getGroupByName(String name) {
        return groupDao.getGroupByName(name).map(groupDtoMapper::mapGroupToDto).orElse(null);
    }
}