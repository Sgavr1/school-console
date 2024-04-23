package org.example.service;

import org.example.repository.GroupRepository;
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
    private final GroupRepository groupRepository;
    private final GroupMapper groupMapper;

    public GroupService(GroupRepository groupDao, GroupMapper groupMapper) {
        this.groupRepository = groupDao;
        this.groupMapper = groupMapper;
    }

    public List<GroupDto> getGroupsLessOrEqualsStudents(int studentsAmount) {
        try {
            return groupRepository.findGroupLessOrEqualsStudents(studentsAmount).stream().map(groupMapper::toDto).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    public void addGroup(GroupDto group) {
        groupRepository.save(groupMapper.toEntity(group));
    }


    public void addGroups(List<GroupDto> groups) {
        groupRepository.saveAll(groups.stream().map(groupMapper::toEntity).toList());
    }

    public List<GroupDto> getGroups() {
        return groupRepository.findAll().stream().map(groupMapper::toDto).toList();
    }

    public GroupDto getGroupByName(String name) {
        try {
            return groupRepository.findByName(name).map(groupMapper::toDto).orElse(null);
        } catch (SQLException e) {
            logger.warn(String.format("Not found group by name = %s", name));
            e.printStackTrace();
        }

        return null;
    }


    public boolean isEmpty() {
        return groupRepository.findAll().isEmpty();
    }
}