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

    @Test
    public void testAvgPerformanceWhereMatriculeStartsWith(){
        //Given
        Employe e1 = new Employe("Doe","John", "C55550", LocalDate.now(),2500d,3,1.0d);
        Employe e2 = new Employe("Smith","John", "C55551", LocalDate.now(),2500d,1,1.0d);
        Employe e3 = new Employe("Richad","John", "C55552", LocalDate.now(),2500d,2,1.0d);
        Employe e4 = new Employe("Dough","John", "C55553", LocalDate.now(),2500d,7,1.0d);
        Employe e5 = new Employe("Wilson","John", "C55554", LocalDate.now(),2500d,15,1.0d);

        employeRepository.save(e1);
        employeRepository.save(e2);
        employeRepository.save(e3);
        employeRepository.save(e4);
        employeRepository.save(e5);


        //When
        Double performanceMoyenne = employeRepository.avgPerformanceWhereMatriculeStartsWith("C");

        //Then
        Assertions.assertThat(performanceMoyenne).isEqualTo(5.6d);
    }

    @BeforeEach
    @AfterEach
    public void purge(){
        employeRepository.deleteAll();
    }
}