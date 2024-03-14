package org.example.dao;

import org.example.entity.Group;

import java.util.Optional;
import java.util.List;

public interface GroupDao {

    List<Group> getGroupLessOrEqualsStudents(int studentsAmount);

    void insert(Group group);

    void insertList(List<Group> groups);

    List<Group> getAll();

    Optional<Group> getGroupByName(String name);

    boolean isEmptyTable();
}