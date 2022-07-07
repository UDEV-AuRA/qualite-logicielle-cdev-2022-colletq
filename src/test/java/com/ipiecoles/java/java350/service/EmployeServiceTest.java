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
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.persistence.EntityExistsException;
import java.time.LocalDate;
import java.util.stream.Stream;

import static org.junit.jupiter.params.provider.Arguments.arguments;

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

        //When
        Throwable e = Assertions.catchThrowable(() -> {
            employeService.embaucheEmploye("Doe", "John", Poste.COMMERCIAL, NiveauEtude.MASTER, 1.0);
        });

        //Then
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

    @ParameterizedTest(name = "La performance est bien calculé")
    @MethodSource("testCalculPerformanceEmployeStream")
    public void testCalculPerformanceCommercial(Employe employeCommercial, Long caTraite, Long objectifCa, Double performanceMoyenne, Integer resultatAttendu) throws EmployeException {
        //Given
        Mockito.when(employeRepository.findByMatricule("C55556")).thenReturn(employeCommercial);
        Mockito.when(employeRepository.avgPerformanceWhereMatriculeStartsWith("C")).thenReturn(performanceMoyenne);


        //When
        employeService.calculPerformanceCommercial("C55556", caTraite, objectifCa);

        //Then
        ArgumentCaptor<Employe> employeArgumentCaptor = ArgumentCaptor.forClass(Employe.class);
        Mockito.verify(employeRepository).save(employeArgumentCaptor.capture());
        Employe e = employeArgumentCaptor.getValue();
        Assertions.assertThat(e.getPerformance()).isEqualTo(resultatAttendu);
    }

    static Stream<Arguments> testCalculPerformanceEmployeStream() {
        return Stream.of(
                //Tests où performance base est toujours choisie
                //Cas 2 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 800L, 1000L, 0.8d, 2),
                //Cas 2 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 800L, 1000L, 1.2d, 1),
                //Cas 3 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 960L, 1000L, 0.8d, 2),
                //Cas 3 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 960L, 1000L, 4d, 1),
                //Cas 4 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 1100L, 1000L, 1.5d, 3),
                //Cas 4 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 1100L, 1000L, 5d, 2),
                //Cas 5 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 1300L, 1000L, 1.3d, 6),
                //Cas 5 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 1, 1.0d), 1300L, 1000L, 10d, 5),

                //Tests où la performance de base ne sera jamais choisie
                //Cas 2 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 800L, 1000L, 2.3d, 5),
                //Cas 2 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 800L, 1000L, 9d, 4),
                //Cas 3 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 960L, 1000L, 3d, 7),
                //Cas 3 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 960L, 1000L, 6d, 6),
                //Cas 4 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 1100L, 1000L, 2d, 8),
                //Cas 4 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 1100L, 1000L, 10.3d, 7),
                //Cas 5 et > à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 1300L, 1000L, 4.5d, 11),
                //Cas 5 et < à performance moyenne
                arguments(new Employe("Doe", "John", "C55556", LocalDate.now(), 2500d, 6, 1.0d), 1300L, 1000L, 18.5d, 10)
        );
    }

    @BeforeEach
    @AfterEach
    public void purge(){
        employeRepository.deleteAll();
    }
}
