package org.example.service;

import jakarta.transaction.Transactional;
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

    public GroupService(GroupRepository groupRepository, GroupMapper groupMapper) {
        this.groupRepository = groupRepository;
        this.groupMapper = groupMapper;
    }

    @Transactional
    public List<GroupDto> getGroupsLessOrEqualsStudents(int studentsAmount) {
        try {
            return groupRepository.findGroupLessOrEqualsStudents(studentsAmount).stream().map(groupMapper::toDto).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Transactional
    public void addGroup(GroupDto group) {
        groupRepository.save(groupMapper.toEntity(group));
    }


    @Transactional
    public void addGroups(List<GroupDto> groups) {
        groupRepository.saveAll(groups.stream().map(groupMapper::toEntity).toList());
    }

    @Transactional
    public List<GroupDto> getGroups() {
        return groupRepository.findAll().stream().map(groupMapper::toDto).toList();
    }

    @Transactional
    public GroupDto getGroupByName(String name) {
        try {
            return groupRepository.findByName(name).map(groupMapper::toDto).orElse(null);
        } catch (SQLException e) {
            logger.warn(String.format("Not found group by name = %s", name));
            e.printStackTrace();
        }

        return null;
    }

    @Transactional
    public boolean isEmpty() {
        return groupRepository.findAll().isEmpty();
    }
}