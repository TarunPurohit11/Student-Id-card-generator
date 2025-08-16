package com.app.idCard.services;

import com.app.idCard.entities.School;
import com.app.idCard.entities.Student;
import com.app.idCard.repository.SchoolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Service
public class UploadService {
    @Autowired
    private SchoolRepository schoolRepository;

    public School uploadFolder(MultipartFile[] files,int schoolId){
        Optional<School> optionalSchool = schoolRepository.findById(schoolId);
        School school = null;
        if(optionalSchool.isPresent()){
            school = optionalSchool.get();
            try {
                Path uploadDir = Paths.get("uploads", "images",school.getSchoolName());
                Files.createDirectories(uploadDir);

                for (MultipartFile file : files) {
                    // Get only file name, remove any folder path from browser
                    String originalFileName = Paths.get(file.getOriginalFilename()).getFileName().toString();
                    byte[] data = file.getBytes();

                    Path path = uploadDir.resolve(originalFileName);
                    Files.write(path, data);

                    System.out.println("Saved to: " + path.toAbsolutePath());
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throw new IllegalArgumentException("Can't find school for uploading folder");
        }
        return school;

    }

    public School uploadExcelFile(MultipartFile excelFile,int id){
        Optional<School> optionalSchool= schoolRepository.findById(id);
        School school = null;
        if(optionalSchool.isPresent()) {
            school = optionalSchool.get();
            try {
                Path uploadDir = Paths.get("uploads", "excelFiles", school.getSchoolName());
                Files.createDirectories(uploadDir);

                String originalFileName = Paths.get(excelFile.getOriginalFilename()).getFileName().toString();
                byte[] data = excelFile.getBytes();

                Path path = uploadDir.resolve(originalFileName);
                Files.write(path, data);
                school.setSchoolExcelFilePath(path.toString());
                schoolRepository.save(school);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }else {
            throw new IllegalArgumentException("Cant find school to upload the file");
        }
        return school;
    }
}
