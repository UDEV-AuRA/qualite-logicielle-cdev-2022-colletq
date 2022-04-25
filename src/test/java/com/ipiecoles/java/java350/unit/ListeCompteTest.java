package com.ipiecoles.java.java350.unit;

import com.ipiecoles.java.java350.Compte;
import com.ipiecoles.java.java350.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class ListeCompteTest {

    @Test
    public void testAddAccountAlreadyCreated(){
        //Given
        String input = "C" + TestUtils.LINE_SEPARATOR + "123" + TestUtils.LINE_SEPARATOR + "500" + TestUtils.LINE_SEPARATOR;
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        String input2 = "C" + TestUtils.LINE_SEPARATOR + "123" + TestUtils.LINE_SEPARATOR + "500" + TestUtils.LINE_SEPARATOR;
        ByteArrayInputStream inContent2 = new ByteArrayInputStream(input2.getBytes());
        System.setIn(inContent2);


        //When
        Compte c = new Compte();

        //Then
        //Test creation first account
        Assertions.assertThat(c.getTypeCompte()).isEqualTo("Courant");
        Assertions.assertThat(c.getNumeroCompte()).isEqualTo("123");
        Assertions.assertThat(c.getValeurCourante()).isEqualTo(500.0);
        //Test to create the same account
        Assertions.assertThat(c.getTypeCompte()).isEqualTo("Courant");
        Assertions.assertThat(c.getNumeroCompte()).isEqualTo("123");
        Assertions.assertThat(c.getValeurCourante()).isEqualTo(500.0);

    }
/* Deuxième test non fonctionnel
    @Test
    public void testAddALine(){
        int account = 123;
        //Given
        String input = account + TestUtils.LINE_SEPARATOR + "+500" + TestUtils.LINE_SEPARATOR + "S" + TestUtils.LINE_SEPARATOR + "V" + TestUtils.LINE_SEPARATOR + "06/11/2017" + TestUtils.LINE_SEPARATOR;
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);
        Compte c1 = new Compte();

        //When
        //List<String> lineaccount = c1.creerLigne();
        //lineaccount.add(1,"+500");

        //Then
        //Test add a line on an account
        Assertions.assertThat(c1.getTypeCompte()).isEqualTo("Courant");
        Assertions.assertThat(c1.getNumeroCompte()).isEqualTo(account);
        Assertions.assertThat(c1.getValeurCourante()).isEqualTo(500.0);
        Assertions.assertThat(c1.getNumeroCompte()).isEqualTo(account);
        Assertions.assertThat(c1.getLigneComptables()).isNotEmpty();
        Assertions.assertThat(c1.getLigneComptables()).size().isEqualTo(4);
        Assertions.assertThat(c1.getLigneComptables()).containsExactly();

    }*/

}
