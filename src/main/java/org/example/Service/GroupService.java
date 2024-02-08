package org.example.Service;

import org.example.Dao.GroupDao;
import org.example.Entity.Group;

import java.util.List;

public class GroupService {
    private GroupDao groupDao;

    public GroupService(GroupDao groupDao) {
        this.groupDao = groupDao;
    }

    public List<Group> getGroupLargeStudent(int numbers) {
        return groupDao.getGroupLargeOrEqualsStudents(numbers);
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
        return groupDao.getGroupByName(name).orElse(new Group());
    }
}
