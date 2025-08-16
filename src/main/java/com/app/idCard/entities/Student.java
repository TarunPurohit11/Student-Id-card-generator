package com.app.idCard.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;


@Entity
@Data
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String studentName;
    private String standard;
    private String studentAddress;
    private int rollNo;
    private LocalDate dob;
    private String current_session;

    private String guardianName;
    private String guardianContact;

    private String studentPicturePath;

    @ManyToOne
    @JoinColumn(name = "school_id")
    private School school;

}
