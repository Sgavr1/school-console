package org.example.service;

import jakarta.transaction.Transactional;
import org.example.repository.StudentRepository;
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
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;

    public StudentService(StudentRepository studentDao, StudentMapper studentMapper) {
        this.studentRepository = studentDao;
        this.studentMapper = studentMapper;
    }

    public void addStudent(StudentDto student) {
        studentRepository.save(studentMapper.toEntity(student));
    }

    public void addStudents(List<StudentDto> students) {
        studentRepository.saveAll(students.stream().map(studentMapper::toEntity).toList());
    }

    public StudentDto getStudentById(Integer id) {
        return studentRepository.findById(id).map(studentMapper::toDto).orElse(null);
    }

    public List<StudentDto> getStudentsByCourseName(String courseName) {
        try {
            return studentRepository.findAllStudentByCourseName(courseName).stream().map(studentMapper::toDto).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    public void delete(StudentDto student) {
        studentRepository.deleteById(student.getId());
    }

    public List<StudentDto> getStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toDto).toList();
    }

    public void addStudentToCourse(int studentId, int courseId) {
        try {
            studentRepository.saveStudentOnCourse(studentId, courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void addStudentsToCourse(int courseId, List<StudentDto> students) {
        try {
            for (StudentDto student : students) {
                studentRepository.saveStudentOnCourse(student.getId(), courseId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteFromCourse(int studentId, int courseId) {
        try {
            studentRepository.deleteFromCourse(studentId, courseId);
        } catch (SQLException e) {
            logger.error(String.format("Error delete student from course: studentId = %d courseId = %d : {}",
                    studentId, courseId), e.getMessage(), e);
            e.printStackTrace();
        }
    }

    public boolean isEmpty() {
        return studentRepository.findAll().isEmpty();
    }
}