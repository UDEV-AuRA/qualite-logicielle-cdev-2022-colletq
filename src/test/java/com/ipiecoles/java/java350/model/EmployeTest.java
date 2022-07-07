package com.ipiecoles.java.java350.model;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.LocalDate;
import java.time.Month;

public class EmployeTest {

    //----------Tests getNombreAnneeAnciennete-----------

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

    //---------Tests augmenterSalaire---------

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

    //---------Tests getNbRTT--------

    @ParameterizedTest(name = "La fonction donne le bon nombre de jours fériés pour l'année {0}: {1} jours")
    @CsvSource({
            "2019,10",
            "2021,7",
            "2022,7",
            "2032,7"
    })
    public void testGetNbRtt(int annee, int joursFeries) {
        //Given
        Employe e = new Employe();
        LocalDate anneeDate = LocalDate.of(annee, Month.JANUARY, 1);

        //When
        e.setTempsPartiel(1d);

        //Then
        Assertions.assertThat(e.getNbRtt(anneeDate) == joursFeries);
    }

    @ParameterizedTest(name = "La fonction donne le bon nombre de jours fériés")
    @CsvSource({
            "2019,8",
            "2021,6",
            "2022,6",
            "2032,6"
    })
    public void testGetNbRttTempsPartiel(int annee, int joursFeries) {
        //Given
        Employe e = new Employe();
        LocalDate anneeDate = LocalDate.of(annee, Month.JANUARY, 1);

        //When
        e.setTempsPartiel(0.8d);

        //Then
        Assertions.assertThat(e.getNbRtt(anneeDate) == joursFeries);
    }
}
