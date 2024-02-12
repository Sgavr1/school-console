package org.example.service;

import org.example.dao.GroupDao;
import org.example.entity.Group;

import java.util.List;

public class GroupService {
    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

        public List<Group> getGroupsGreaterOrEqualsStudents(int amountStudents) {
        return groupDao.getGroupGreaterOrEqualsStudents(amountStudents);
    }

    public void addGroup(Group group) {
        groupDao.insert(group);
    }

    public void addGroups(List<Group> groups) {
        groupDao.insertList(groups);
    }

    public List<Group> getGroups() {
        return groupDao.getAll();
    }

    public Group getGroupByName(String name) {
        return groupDao.getGroupByName(name).orElse(null);
    }
}
