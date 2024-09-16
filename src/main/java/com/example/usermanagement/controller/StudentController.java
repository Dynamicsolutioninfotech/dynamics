package com.example.usermanagement.controller;

import com.example.usermanagement.model.Student;
import com.example.usermanagement.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping
    public String viewStudents(Model model) {
        model.addAttribute("students", studentService.getAllStudents());
        return "student";
    }

    @PostMapping("/add")
    public String addStudent(@ModelAttribute Student student) {
        studentService.addStudent(student);
        return "redirect:/students";
    }

    @PostMapping("/delete")
    public String deleteStudent(@RequestParam("id") Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

    @GetMapping("/edit/{id}") // GET mapping for edit form
    public String showEditForm(@PathVariable("id") Long id, Model model) {
        Optional<Student> student = studentService.getStudentById(id);
        if (student.isPresent()) {
            model.addAttribute("student", student.get());
            return "edit_student";
        } else {
            return "redirect:/students";
        }
    }

    @PostMapping("/edit/{id}") // POST mapping for handling edit form submission
    public String editStudent(@PathVariable("id") Long id, @ModelAttribute Student updatedStudent) {
        updatedStudent.setId(id); // Ensure the ID is set for updating the correct student
        studentService.updateStudent(updatedStudent);
        return "redirect:/students";
    }
    @GetMapping("/search")
    public String searchStudents(@RequestParam("course") String course, Model model) {
        if (course != null && !course.isEmpty()) {
            model.addAttribute("students", studentService.getStudentsByCourse(course));
        } else {
            model.addAttribute("students", studentService.getAllStudents());
        }
        return "student";
    }
}
