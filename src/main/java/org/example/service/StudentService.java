package org.example.service;

import jakarta.transaction.Transactional;
import org.example.dao.StudentDao;
import org.example.dto.StudentDto;
import org.example.mapper.StudentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class StudentService {
    private final Logger logger = LoggerFactory.getLogger(StudentService.class);
    private final StudentDao studentDao;
    private final StudentMapper studentMapper;

    public StudentService(StudentDao studentDao, StudentMapper studentMapper) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
    }

    public void addStudent(StudentDto student) {
        studentDao.save(studentMapper.toEntity(student));
    }

    public void addStudents(List<StudentDto> students) {
        studentDao.saveAll(students.stream().map(studentMapper::toEntity).toList());
    }

    public StudentDto getStudentById(Integer id) {
        return studentDao.findById(id).map(studentMapper::toDto).orElse(null);
    }

    public List<StudentDto> getStudentsByCourseName(String courseName) {
        List<StudentDto> studentsDto = new ArrayList<>();
        try {
            studentsDto = studentDao.findAllStudentByCourseName(courseName).stream().map(studentMapper::toDto).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return studentsDto;
    }

    public void delete(StudentDto student) {
        studentDao.deleteById(student.getId());
    }

    public List<StudentDto> getStudents() {
        return studentDao.findAll().stream().map(studentMapper::toDto).toList();
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try {
            studentDao.saveStudentOnCourse(studentId, courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void addStudentsToCourse(int courseId, List<StudentDto> students) {
        try {
            for (StudentDto student : students){
                studentDao.saveStudentOnCourse(student.getId(), courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromCourse(int studentId, int courseId) {
        try {
            studentDao.deleteFromCourse(studentId, courseId);
        } catch (SQLException e) {
            logger.error(String.format("Error delete student from course: studentId = %d courseId = %d : {}",
                    studentId, courseId), e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        return studentDao.findAll().isEmpty();
    }
}