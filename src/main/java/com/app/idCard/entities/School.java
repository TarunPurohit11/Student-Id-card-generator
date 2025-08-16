package com.app.idCard.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;


@Entity
@Data
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String schoolName;
    private String schoolEmail;
    private String password;
    private String schoolPhoneNo;
    private String schoolAddress;

    private String schoolLogoFilePath;

    private String schoolExcelFilePath;

    @OneToMany(mappedBy = "school",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    private List<Student> students = new ArrayList<>();


}
