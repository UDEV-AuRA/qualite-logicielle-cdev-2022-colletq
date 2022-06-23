package com.ipiecoles.java.java350.repository;

import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class EmployeRepositoryTest {
    @Autowired
    EmployeRepository employeRepository;

    @Test
    public void testFindMatriculeWith1Employe(){
        //Given
        Employe employe = new Employe("Doe", "John", "T02345",
                LocalDate.now(), 2500d, 1, 1.0);
        employeRepository.save(employe);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("02345");
    }

    @Test
    public void testFindMatriculeWith1Employe2(){
        //Given
        Employe employe = new Employe("Doe", "John", "T02345",
                LocalDate.now(), 2500d, 1, 1.0);
        Employe employe2 = new Employe("Doe", "jonas", "M02346",
                LocalDate.now(), 2500d, 1, 1.0);
        Employe employe3 = new Employe("Doe", "joe", "T02347",
                LocalDate.now(), 2500d, 1, 1.0);
        employeRepository.save(employe);
        employeRepository.save(employe2);
        employeRepository.save(employe3);

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isEqualTo("02347");
    }

    @Test
    public void testFindMatriculeWith1Employe3(){
        //Given

        //When
        String lastMatricule = employeRepository.findLastMatricule();

        //Then
        Assertions.assertThat(lastMatricule).isNull();
    }

    @BeforeEach
    @AfterEach
    public void purge(){
        employeRepository.deleteAll();
    }
}