package com.example.usermanagement.service;

import com.example.usermanagement.model.Student;
import com.example.usermanagement.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }



    public void addStudent(Student student) {
        studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public List<Student> getStudentsByCourse(String course) {
        return studentRepository.findByCourse(course);
    }
    public void updateStudent(Student student) {
        // To ensure you're updating an existing student
        Optional<Student> existingStudentOptional = studentRepository.findById(student.getId());
        if (existingStudentOptional.isPresent()) {
            Student existingStudent = existingStudentOptional.get();
            existingStudent.setName(student.getName());
            existingStudent.setPhone(student.getPhone());
            existingStudent.setAltPhone(student.getAltPhone());
            existingStudent.setEmail(student.getEmail());
            existingStudent.setCourse(student.getCourse());
            studentRepository.save(existingStudent);
        } else {
            // Handle not found scenario or throw an exception
            throw new RuntimeException("Student not found with id: " + student.getId());
        }
    }
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
}
