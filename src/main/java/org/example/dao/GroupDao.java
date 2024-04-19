package org.example.dao;

import org.example.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

@Repository
public interface GroupDao extends JpaRepository<Group, Integer> {

    static final String QUERY_SELECT_LESS_OR_EQUALS_STUDENT = """
            SELECT g
            FROM Group g
            LEFT JOIN g.students s
            GROUP BY g.name, g.id, s.id
            HAVING count(s.id) <= ?1
            """;
    Optional<Group> findByName(String name) throws SQLException;
    @Query(value = QUERY_SELECT_LESS_OR_EQUALS_STUDENT)
    List<Group> findGroupLessOrEqualsStudents(int studentsAmount) throws SQLException;
}