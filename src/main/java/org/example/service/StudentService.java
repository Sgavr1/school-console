package org.example.service;

import org.example.dao.StudentDao;
import org.example.dto.StudentDto;
import org.example.map.StudentDtoMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentService {
    private final StudentDao studentDao;
    private final StudentDtoMapper studentDtoMapper;

    public StudentService(StudentDao studentDao, StudentDtoMapper studentDtoMapper) {
        this.studentDao = studentDao;
        this.studentDtoMapper = studentDtoMapper;
    }

    public void addStudent(StudentDto student) {
        studentDao.insert(studentDtoMapper.mapDtoToStudent(student));
    }

    public void addStudents(List<StudentDto> students) {
        studentDao.insertList(students.stream().map(studentDtoMapper::mapDtoToStudent).toList());
    }

    public StudentDto getStudentById(int id) {
        return studentDao.getById(id).map(studentDtoMapper::mapStudentToDto).orElse(null);
    }

    public List<StudentDto> getStudentsByCourseName(String courseName) {
        return studentDao.getStudentsByCourseName(courseName).stream().map(studentDtoMapper::mapStudentToDto).toList();
    }

    public void delete(StudentDto student) {
        studentDao.delete(student.getId());
    }

    public List<StudentDto> getStudents() {
        return studentDao.getAll().stream().map(studentDtoMapper::mapStudentToDto).toList();
    }

    public void addStudentToCourse(int studentId, int courseId) {
        studentDao.insertStudentToCourse(studentId, courseId);
    }

    public void addStudentsOnCourse(int courseId, List<StudentDto> students) {
        studentDao.insertListStudentsOnCourse(courseId, students.stream().map(studentDtoMapper::mapDtoToStudent).toList());
    }

    public void deleteFromCourse(int studentId, int courseId) {
        studentDao.deleteFromCourse(studentId, courseId);
    }
}