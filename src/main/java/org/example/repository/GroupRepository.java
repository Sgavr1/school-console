package org.example.repository;

import org.example.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.SQLException;
import java.util.Optional;
import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Integer> {
    Optional<Group> findByName(String name) throws SQLException;

    @Query(value = "SELECT g FROM Group g LEFT JOIN g.students s GROUP BY g.name, g.id, s.id HAVING count(s.id) <= ?1")
    List<Group> findGroupLessOrEqualsStudents(int studentsAmount) throws SQLException;
}