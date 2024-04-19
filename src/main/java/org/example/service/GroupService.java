package org.example.service;

import org.example.dao.GroupDao;
import org.example.dto.GroupDto;
import org.example.mapper.GroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GroupService {
    private final Logger logger = LoggerFactory.getLogger(GroupService.class);
    private final GroupDao groupDao;
    private final GroupMapper groupMapper;

    public GroupService(GroupDao groupDao, GroupMapper groupMapper) {
        this.groupDao = groupDao;
        this.groupMapper = groupMapper;
    }

    public List<GroupDto> getGroupsLessOrEqualsStudents(int studentsAmount) {
        List<GroupDto> groupsDto = new ArrayList<>();
        try {
            groupsDto = groupDao.findGroupLessOrEqualsStudents(studentsAmount).stream().map(groupMapper::toDto).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  groupsDto;
    }

    public void addGroup(GroupDto group) {
        groupDao.save(groupMapper.toEntity(group));
    }


    public void addGroups(List<GroupDto> groups) {
        groupDao.saveAll(groups.stream().map(groupMapper::toEntity).toList());
    }

    public List<GroupDto> getGroups() {
        return groupDao.findAll().stream().map(groupMapper::toDto).toList();
    }

    public GroupDto getGroupByName(String name) {
        GroupDto groupDto = null;
        try {
            groupDto = groupDao.findByName(name).map(groupMapper::toDto).orElse(null);
        } catch (SQLException e) {
            logger.warn(String.format("Not found group by name = %s", name));
            e.printStackTrace();
        }

        return groupDto;
    }


    public boolean isEmpty() {
        return groupDao.findAll().isEmpty();
    }
}