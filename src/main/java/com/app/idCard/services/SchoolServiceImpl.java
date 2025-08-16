package com.app.idCard.services;

import com.app.idCard.entities.School;
import com.app.idCard.repository.SchoolRepository;
import lombok.AllArgsConstructor;


import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;



import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SchoolServiceImpl {


    private final SchoolRepository schoolRepository;



    public void registerSchool(School school, MultipartFile logo) {
        try {
            Path uploadDir = Paths.get("uploads", "schoolLogos",school.getSchoolName());
            Files.createDirectories(uploadDir);

                String originalFileName = Paths.get(logo.getOriginalFilename()).getFileName().toString();
                byte[] data = logo.getBytes();

                Path path = uploadDir.resolve(originalFileName);
                Files.write(path, data);

                school.setSchoolLogoFilePath("/uploads/schoolLogos/"+school.getSchoolName()+"/"+originalFileName);

        } catch (IOException e) {
            e.printStackTrace();
        }

        schoolRepository.save(school);
    }

    public School loginSchool(String email) {
        return schoolRepository.findBySchoolEmail(email);
    }


    public Optional<School> getSchoolById(int id) {
        return schoolRepository.findById(id);
    }

}
