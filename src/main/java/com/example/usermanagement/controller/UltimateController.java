package com.example.usermanagement.controller;

import com.example.usermanagement.model.CourseItem;
import com.example.usermanagement.repository.CourseItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class UltimateController {

    @Autowired
    private CourseItemRepository courseItemRepository;

    @GetMapping("/Courses")
    public String getCourses(Model model) {
        List<CourseItem> courseItems = courseItemRepository.findByEnabled(true);
        model.addAttribute("courseItems", courseItems);
        return "Courses";
    }

    public CourseItemRepository getCourseItemRepository() {
        return courseItemRepository;
    }

    public void setCourseItemRepository(CourseItemRepository courseItemRepository) {
        this.courseItemRepository = courseItemRepository;
    }
    
    @GetMapping("/Contact")
    public String contact() {
        return "contact";
    }

}
