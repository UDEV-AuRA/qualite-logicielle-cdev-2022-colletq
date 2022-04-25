package com.ipiecoles.java.java350.unit;

import com.ipiecoles.java.java350.Compte;
import com.ipiecoles.java.java350.utils.TestUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.io.ByteArrayInputStream;

public class CompteTest {

    @Test
    public void testCompte(){
        //Given
        String input = "C" + TestUtils.LINE_SEPARATOR + "123" + TestUtils.LINE_SEPARATOR + "500" + TestUtils.LINE_SEPARATOR;
        ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
        System.setIn(inContent);

        //When
        Compte c = new Compte();

        //Then
        Assertions.assertThat(c.getTypeCompte()).isEqualTo("Courant");
        Assertions.assertThat(c.getNumeroCompte()).isEqualTo("123");
        Assertions.assertThat(c.getValeurCourante()).isEqualTo(500.0);
    }
}
