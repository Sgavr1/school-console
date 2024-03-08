package org.example.service;

import org.example.dao.StudentDao;
import org.example.dto.StudentDto;
import org.example.map.StudentMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentDao studentDao;
    private final StudentMapper studentMapper;

    public StudentService(StudentDao studentDao, StudentMapper studentMapper) {
        this.studentDao = studentDao;
        this.studentMapper = studentMapper;
    }

    public void addStudent(StudentDto student) {
        studentDao.insert(studentMapper.toEntity(student));
    }

    public void addStudents(List<StudentDto> students) {
        studentDao.insertList(students.stream().map(studentMapper::toEntity).toList());
    }

    public StudentDto getStudentById(int id) {
        return studentDao.getById(id).map(studentMapper::toDto).orElse(null);
    }

    public List<StudentDto> getStudentsByCourseName(String courseName) {
        return studentDao.getStudentsByCourseName(courseName).stream().map(studentMapper::toDto).toList();
    }

    public void delete(StudentDto student) {
        studentDao.delete(student.getId());
    }

    public List<StudentDto> getStudents() {
        return studentDao.getAll().stream().map(studentMapper::toDto).toList();
    }

    public void addStudentToCourse(int studentId, int courseId) {
        studentDao.insertStudentToCourse(studentId, courseId);
    }

    public void addStudentsOnCourse(int courseId, List<StudentDto> students) {
        studentDao.insertListStudentsOnCourse(courseId, students.stream().map(studentMapper::toEntity).toList());
    }

    public void deleteFromCourse(int studentId, int courseId) {
        studentDao.deleteFromCourse(studentId, courseId);
    }

    public boolean isEmpty() {
        return studentDao.isEmptyTable();
    }
}