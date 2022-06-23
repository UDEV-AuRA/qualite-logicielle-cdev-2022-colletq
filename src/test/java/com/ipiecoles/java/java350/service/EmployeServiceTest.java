package com.ipiecoles.java.java350.service;

import com.ipiecoles.java.java350.exception.EmployeException;
import com.ipiecoles.java.java350.model.Employe;
import com.ipiecoles.java.java350.model.NiveauEtude;
import com.ipiecoles.java.java350.model.Poste;
import com.ipiecoles.java.java350.repository.EmployeRepository;
import com.ipiecoles.java.java350.service.EmployeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;

@ExtendWith(MockitoExtension.class)
public class EmployeServiceTest {
    @InjectMocks
    private EmployeService employeService;

    @Mock
    private EmployeRepository employeRepository;

    @Test
    public void testFindTypeEmploye() throws EmployeException {
        //Given
        Mockito.when(employeRepository.findLastMatricule()).thenReturn(null);
        Mockito.when(employeRepository.findByMatricule("C00001")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        //When
        employeService.embaucheEmploye("Doe","John",
                Poste.COMMERCIAL, NiveauEtude.MASTER,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());

        //Employe employe = employeRepository.findByMatricule("C00001");
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
        Assertions.assertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00001");
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2129.71);
    }

    @Test
    public void testEmbauchePlusieursEmployes() throws EmployeException {
        //Given
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("12345");
        Mockito.when(employeRepository.findByMatricule("C12346")).thenReturn(null);
        Mockito.when(employeRepository.save(Mockito.any(Employe.class))).thenAnswer(AdditionalAnswers.returnsFirstArg());
        //When
        employeService.embaucheEmploye("Doe","John",
                Poste.COMMERCIAL, NiveauEtude.MASTER,1.0);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());

        //Employe employe = employeRepository.findByMatricule("C00001");
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
        Assertions.assertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C12346");
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(1.0);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getSalaire()).isEqualTo(2129.71);
    }

    @Test
    public void testEmbaucheLimiteMatricule() {
        //Given
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("99999");

        //When
        /*try {
            employeService.embaucheEmploye("Doe", "John",
                    Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
            Assertions.fail("Aurait dû planter !");
        } catch(Exception e) {
            Assertions.assertThat(e).isInstanceOf(EmployeException.class);
            Assertions.assertThat(e).hasMessage("Limite des 100000 maitrcules atteintes !");
        }*/

        //when
        Throwable e = Assertions.catchThrowable(() -> {
           employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
        });

        //then
        Assertions.assertThat(e)
                .isInstanceOf(EmployeException.class)
                .hasMessage("Limite des 100000 matricules atteinte !");
    }

    @Test
    public void testEmbaucheEmployeExisteDeja() {
        //Given
        Mockito.when(employeRepository.findLastMatricule()).thenReturn("55555");
        Mockito.when(employeRepository.findByMatricule("C55556")).thenReturn(new Employe("Doe","John", "C55556", LocalDate.now(),2500d,1,1.0));

        //when
        Throwable e = Assertions.catchThrowable(() -> {
            employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
        });

        //then
        Assertions.assertThat(e)
                .isInstanceOf(EntityExistsException.class)
                .hasMessage("L'employé de matricule C55556 existe déjà en BDD");
    }

    @Test
    public void testEmbaucheEmployeTempsPartiel() throws EmployeException {
        //Given
        employeService.embaucheEmploye("Doe","John",
                Poste.COMMERCIAL, NiveauEtude.MASTER,0.8);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());

        //Employe employe = employeRepository.findByMatricule("C00001");
        Employe employe = employeArgumentCaptor.getValue();
        Assertions.assertThat(employe).isNotNull();
        Assertions.assertThat(employe.getPrenom()).isEqualTo("John");
        Assertions.assertThat(employe.getNom()).isEqualTo("Doe");
        Assertions.assertThat(employe.getMatricule()).isEqualTo("C00001");
        Assertions.assertThat(employe.getPerformance()).isEqualTo(1);
        Assertions.assertThat(employe.getTempsPartiel()).isEqualTo(0.8);
        Assertions.assertThat(employe.getDateEmbauche()).isEqualTo(LocalDate.now());
        Assertions.assertThat(employe.getSalaire()).isEqualTo(1703.77);
    }



    @BeforeEach
    @AfterEach
    public void purge(){
        employeRepository.deleteAll();
    }
}
