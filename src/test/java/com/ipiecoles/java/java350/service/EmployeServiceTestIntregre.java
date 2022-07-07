package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;

@SpringBootTest
public class EmployeServiceTestIntregre {

    @Autowired
    EmployeService employeService;

    @Autowired
    EmployeRepository employeRepository;

    @Test
    public void testIntegreNominalCalculPerformanceCommercial() throws EmployeException {
        //Given
        Employe e = new Employe("Doe","John", "C55556", LocalDate.now(),2500d,6,1.0d);
        employeRepository.save(e);

        //When
        employeService.calculPerformanceCommercial((e.getMatricule()), 1100L, 1000L);

        //Then
        //Cas 4 > à la moyenne (qui ici est de 6 étant donné qu'il n'y a que l'employé e dans la bdd
        Assertions.assertThat(employeRepository.findByMatricule("C55556").getPerformance()).isEqualTo(8);
    }

    @BeforeEach
    @AfterEach
    public void purge(){
        employeRepository.deleteAll();
    }
}
