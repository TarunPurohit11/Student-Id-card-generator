package com.app.idCard.controllers;

import com.app.idCard.entities.School;
import com.app.idCard.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;


@Controller
public class UploadController {

    @Autowired
    private UploadService uploadService;

    @PostMapping("/uploadFolder/{id}")
    public String uploadFolder(@RequestParam("image") MultipartFile[] files,
                               @PathVariable("id") int schoolId,
                               RedirectAttributes redirectAttributes) {
        School school = uploadService.uploadFolder(files,schoolId);
        redirectAttributes.addFlashAttribute("school",school);
        if(school != null) {
            redirectAttributes.addFlashAttribute("success","Folder uploaded successfully");
            return "redirect:/welcomePage";
        }
        redirectAttributes.addFlashAttribute("failed","Folder upload failed");
        return "redirect:/welcomePage";

    }

    @PostMapping("/uploadExcel/{id}")
    public String uploadExcelFile(@RequestParam MultipartFile excelFile,
                                  @PathVariable("id") int schoolId,
                                  RedirectAttributes redirectAttributes) {
        School school = uploadService.uploadExcelFile(excelFile,schoolId);
        redirectAttributes.addFlashAttribute("school",school);
        if(school != null) {
            redirectAttributes.addFlashAttribute("success","File uploaded successfully");
            return "redirect:/welcomePage";
        }
        redirectAttributes.addFlashAttribute("failed","File upload failed");
        return "redirect:/welcomePage";
    }
}
