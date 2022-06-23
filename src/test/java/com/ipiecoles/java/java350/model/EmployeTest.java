package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

public class EmployeTest {
    @Test
    public void testNbAnneeAncienneteNow(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now());

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNowMinus2(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().minusYears(2));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(2);
    }

    @Test
    public void testNbAnneeAncienneteNowPlus3(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(LocalDate.now().plusYears(3));

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testNbAnneeAncienneteNull(){
        //Given
        Employe employe = new Employe();
        employe.setDateEmbauche(null);

        //When
        Integer nbAnnees = employe.getNombreAnneeAnciennete();

        //Then
        Assertions.assertThat(nbAnnees).isEqualTo(0);
    }

    @Test
    public void testAugmenterSalaireEmploye() {
        //Given
        Employe e = new Employe();
        e.setSalaire(2137d);

        //When
        e.augmenterSalaire(25.6);

        //Then
        Assertions.assertThat(e.getSalaire() == 2685);
    }

    @Test
    public void testAugmenterSalaireNegatifEmploye() {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(-25d);

        //Then
        Assertions.assertThat(e.getSalaire() == 2000);
    }

    @Test
    public void testAugmenterSalaireZeroEmploye() {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        //When
        e.augmenterSalaire(0d);

        //Then
        Assertions.assertThat(e.getSalaire() == 2000);
    }

    @Test
    public void testAugmenterSalaireNullEmploye() {
        //Given
        Employe e = new Employe();
        e.setSalaire(2000d);

        Double d = null;

        //When
        e.augmenterSalaire(d);

        //Then
        Assertions.assertThat(e.getSalaire() == 2000);
    }
}
