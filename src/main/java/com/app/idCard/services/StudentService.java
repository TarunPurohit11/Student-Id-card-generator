package com.app.idCard.services;


import com.app.idCard.entities.School;
import com.app.idCard.entities.Student;
import com.app.idCard.repository.SchoolRepository;
import com.app.idCard.repository.StudentRepository;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;



import java.io.FileInputStream;
import java.io.IOException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class StudentService {

    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private SchoolRepository schoolRepository;


    public void saveExcelFile(int id) throws IOException {

        Optional<School> optionalSchool = schoolRepository.findById(id);
        School  school;
        if(optionalSchool.isPresent()){
            school = optionalSchool.get();
        }
        else{
            throw new IllegalArgumentException("Cant generate IDs");
        }
        studentRepository.deleteAll();
        String filePath = school.getSchoolExcelFilePath();

        System.out.println(filePath);

        Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
        Sheet sheet = workbook.getSheetAt(0);

        DataFormatter formatter = new DataFormatter();
        List<Student> students = new ArrayList<>();

        for(Row row : sheet){
            if(row.getRowNum() == 0) continue;

            String rollNo = formatter.formatCellValue(row.getCell(0));
            String name = formatter.formatCellValue(row.getCell(1));
            String standard = formatter.formatCellValue(row.getCell(2));
            String dob = formatter.formatCellValue(row.getCell(3));
            String studentAddress = formatter.formatCellValue(row.getCell(4));
            String guardianName = formatter.formatCellValue(row.getCell(5));
            String guardianContact = formatter.formatCellValue(row.getCell(6));
            String studentPicture = formatter.formatCellValue(row.getCell(7));
            String session = formatter.formatCellValue(row.getCell(8));

            LocalDate parsedDob;
            try {
                parsedDob = LocalDate.parse(dob, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            } catch (DateTimeParseException e) {
                parsedDob = LocalDate.parse(dob, DateTimeFormatter.ofPattern("d/M/yy"));
            }
            Student student = new Student();
            student.setStudentName(name);
            student.setRollNo(Integer.parseInt(rollNo));
            student.setStandard(standard);
            student.setDob(parsedDob);
            student.setStudentAddress(studentAddress);
            student.setGuardianName(guardianName);
            student.setGuardianContact(guardianContact);
            student.setCurrent_session(session);

            String studentPicturePath = "/uploads/images/" + school.getSchoolName() +"/"+ studentPicture;
            student.setStudentPicturePath(studentPicturePath);
            student.setSchool(school);
            students.add(student);
        }
        school.setStudents(students);
        schoolRepository.save(school);
    }


    public Page<Student> getStudents(int schoolId,Pageable pageable) {
            return studentRepository.findBySchoolId(schoolId,pageable);
    }

}
