package com.app.idCard.controllers;

import com.app.idCard.entities.School;
import com.app.idCard.services.SchoolServiceImpl;
import com.app.idCard.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;


@Controller
public class SchoolController {

    @Autowired
    private SchoolServiceImpl schoolService;

    @Autowired
    private StudentService studentService;

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/loginPage")
    public String openLoginPage(Model model) {
        model.addAttribute("school",new School());
        return "login";
    }

    @GetMapping("/registerPage")
    public String openRegisterPage(Model model) {
        model.addAttribute("school",new School());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute("school") School school,
                            @RequestParam("image") MultipartFile image,
                           RedirectAttributes redirectAttributes) throws IOException {
        try {
            // Save school and image here
            schoolService.registerSchool(school,image);
            // Add flash attribute (available only after redirect)
            redirectAttributes.addFlashAttribute("success", "Registration successful!");
            return "redirect:/registerPage"; // Redirect here (important!)

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Registration failed: " + e.getMessage());
            return "redirect:/registerPage"; // Still redirect
        }
    }


    @PostMapping("/login")
    public String login(@ModelAttribute("school") School school,
                        RedirectAttributes redirectAttributes) {
        System.out.println(school.getSchoolEmail());
        School newSchool = schoolService.loginSchool(school.getSchoolEmail());
            if (newSchool.getPassword().equals(school.getPassword())) {
                redirectAttributes.addFlashAttribute("school",newSchool);
                return "redirect:/welcomePage";
            }
        redirectAttributes.addFlashAttribute("error","Invalid Credential");
        return "redirect:/loginPage";
    }

    @GetMapping("/welcomePage")
    public String openWelcomePage(@ModelAttribute("school") School school, Model model){
        model.addAttribute("school",school);
        return "welcome";
    }
}
