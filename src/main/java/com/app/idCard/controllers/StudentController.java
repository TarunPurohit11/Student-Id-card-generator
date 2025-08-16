package com.app.idCard.controllers;

import com.app.idCard.entities.Student;
import com.app.idCard.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
@Controller
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/generate/{id}")
    public String generateStudentIdCard(@PathVariable int id,
                                        @RequestParam(defaultValue = "0") int page,
                                        @PageableDefault(size = 3) Pageable pageable,
                                        Model model) throws IOException {
        if(page == 0)
            studentService.saveExcelFile(id);
        Page<Student> studentPage = studentService.getStudents(id, pageable);
        model.addAttribute("studentPage",studentPage);
        model.addAttribute("schoolId",id);

        return "generate";
    }

}
