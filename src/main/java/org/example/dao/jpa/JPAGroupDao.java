package org.example.dao.jpa;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import org.example.dao.GroupDao;
import org.example.entity.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("JPAGroup")
public class JPAGroupDao implements GroupDao {
    private final Logger logger = LoggerFactory.getLogger(JPAGroupDao.class);
    private static final String QUERY_CHECK_EMPTY_TABLE = "Select count(g) From Group g";
    private static final String QUERY_SELECT_ALL = "SELECT g FROM Group g";
    private static final String QUERY_SELECT_BY_NAME = """
            SELECT g
            FROM Group g
            LEFT JOIN g.students
            WHERE g.name = ?1
            """;
    private static final String QUERY_SELECT_LESS_OR_EQUALS_STUDENT = """
            SELECT g
            FROM Group g
            LEFT JOIN g.students s
            LEFT JOIN g.students as s1
            GROUP BY g.name, g.id, s1.id
            HAVING count(s.id) <= ?1
            """;
    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Group> getGroupLessOrEqualsStudents(int studentsAmount) {
        return em.createQuery(QUERY_SELECT_LESS_OR_EQUALS_STUDENT, Group.class).setParameter(1, studentsAmount).getResultList();
    }

    @Override
    public void insert(Group group) {
        try {
            em.merge(group);
        } catch (PersistenceException e) {
            logger.error(String.format("Error insert group: name = %s", group.getName()));
            e.printStackTrace();
        }
    }

    @Override
    public void insertList(List<Group> groups) {
        try {
            for (Group group : groups) {
                em.merge(group);
            }
            em.flush();
        } catch (PersistenceException e) {
            logger.error("Error insert list groups");
            e.printStackTrace();
        }
    }

    @Override
    public List<Group> getAll() {
        return em.createQuery(QUERY_SELECT_ALL, Group.class).getResultList();
    }

    @Override
    public Optional<Group> getGroupByName(String name) {
        Group group = em.createQuery(QUERY_SELECT_BY_NAME, Group.class).setParameter(1, name).getSingleResult();
        if (group == null) {
            logger.warn(String.format("Not found group by name = %s", name));
        }
        return Optional.ofNullable(group);
    }

    @Override
    public boolean isEmptyTable() {
        return em.createQuery(QUERY_CHECK_EMPTY_TABLE, Long.class).getSingleResult() == 0;
    }
}
