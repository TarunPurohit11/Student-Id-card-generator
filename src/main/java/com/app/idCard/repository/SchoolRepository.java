package com.app.idCard.repository;

import com.app.idCard.entities.School;
import org.springframework.data.jpa.repository.JpaRepository;



public interface SchoolRepository extends JpaRepository<School,Integer> {
    School findBySchoolEmail(String schoolEmail);
}
