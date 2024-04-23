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

    public StudentService(StudentRepository studentRepository, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.studentMapper = studentMapper;
    }

    @Transactional
    public void addStudent(StudentDto student) {
        studentRepository.save(studentMapper.toEntity(student));
    }

    @Transactional
    public void addStudents(List<StudentDto> students) {
        studentRepository.saveAll(students.stream().map(studentMapper::toEntity).toList());
    }

    @Transactional
    public StudentDto getStudentById(Integer id) {
        return studentRepository.findById(id).map(studentMapper::toDto).orElse(null);
    }

    @Transactional
    public List<StudentDto> getStudentsByCourseName(String courseName) {
        try {
            return studentRepository.findAllStudentByCourseName(courseName).stream().map(studentMapper::toDto).toList();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return new ArrayList<>();
    }

    @Transactional
    public void delete(StudentDto student) {
        studentRepository.deleteById(student.getId());
    }

    @Transactional
    public List<StudentDto> getStudents() {
        return studentRepository.findAll().stream().map(studentMapper::toDto).toList();
    }

    @Transactional
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
            List<Integer> studentsId = students.stream().map(student -> student.getId()).toList();

            studentRepository.saveAllStudentsOnCourse(studentsId, courseId);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteFromCourse(int studentId, int courseId) {
        try {
            studentRepository.deleteFromCourse(studentId, courseId);
        } catch (SQLException e) {
            logger.error(String.format("Error delete student from course: studentId = %d courseId = %d : {}",
                    studentId, courseId), e.getMessage(), e);
            e.printStackTrace();
        }
    }

    @Transactional
    public boolean isEmpty() {
        return studentRepository.findAll().isEmpty();
    }
}