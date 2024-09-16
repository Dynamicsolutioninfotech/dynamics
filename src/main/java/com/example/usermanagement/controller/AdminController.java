package com.example.usermanagement.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

@Controller
public class AdminController {


    private static final Logger logger = Logger.getLogger(AdminController.class.getName());

    private static final String ADMIN_EMAIL = "DynamicInfo@dymail.in";
    private static final String ADMIN_PASSWORD_HASH = "6ebe76c9fb411be97b3b0d48b791a7c9"; // MD5 hash for "987654321"

    @PostMapping("/AdminLogin")
    public String adminLogin(@RequestParam("email") String email,
                             @RequestParam("password") String password,
                             RedirectAttributes redirectAttributes,
                             HttpSession session) {
        logger.info("Attempting to log in with email: " + email);
        String hashedPassword = md5Hash(password);
        logger.info("Expected email: " + ADMIN_EMAIL);
        logger.info("Expected password hash: " + ADMIN_PASSWORD_HASH);
        logger.info("Provided password hash: " + hashedPassword);

        if (ADMIN_EMAIL.equals(email) && ADMIN_PASSWORD_HASH.equals(hashedPassword)) {
            session.setAttribute("adminLoggedIn", true);
            logger.info("Admin logged in successfully.");
            return "redirect:/AdminOptions";
        } else {
            redirectAttributes.addFlashAttribute("error", "Incorrect email or password. Please try again.");
            logger.warning("Admin login failed. Incorrect email or password.");
            return "redirect:/AdminLogin";
        }
    }

    @GetMapping("/AdminOptions")
    public String adminOptions(HttpSession session, RedirectAttributes redirectAttributes, Model model) {
        logger.info("Checking access to AdminOptions page.");
        Boolean isAdminLoggedIn = (Boolean) session.getAttribute("adminLoggedIn");
        if (Boolean.TRUE.equals(isAdminLoggedIn)) {
            logger.info("Access granted to AdminOptions.");
            // Add your logic to populate the model or perform other operations
            return "AdminOptions"; // Return the view name
        } else {
            logger.warning("Access denied to AdminOptions. Not logged in.");
            redirectAttributes.addFlashAttribute("error", "You must be logged in to view this page.");
            return "redirect:/AdminLogin";
        }
    }

    private String md5Hash(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : messageDigest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}
