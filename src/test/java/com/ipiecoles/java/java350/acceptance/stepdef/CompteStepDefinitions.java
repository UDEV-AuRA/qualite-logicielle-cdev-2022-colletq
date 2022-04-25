package com.ipiecoles.java.java350.acceptance.stepdef;

import com.ipiecoles.java.java350.Compte;
import com.ipiecoles.java.java350.utils.TestUtils;
import cucumber.api.java8.En;
import org.assertj.core.api.Assertions;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;

import java.io.ByteArrayInputStream;

import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

public class CompteStepDefinitions implements En {

    private Compte compte = null;

    private TextFromStandardInputStream systemInMock = emptyStandardInputStream();

    public CompteStepDefinitions() {
        When("^Je crée un compte (.*) de numéro (.*) avec un solde de (.*) €$",(String typeCompte, String numero, String solde) -> {
            String input = typeCompte.toUpperCase().substring(0,1) + TestUtils.LINE_SEPARATOR + numero + TestUtils.LINE_SEPARATOR + solde + TestUtils.LINE_SEPARATOR;
            ByteArrayInputStream inContent = new ByteArrayInputStream(input.getBytes());
            System.setIn(inContent);

            //systemInMock.provideLines(typeCompte.toUpperCase().substring(0,1), numero, solde.toString());
            compte = new Compte();
        });

        Then("^J'obtiens bien un compte (.*) de numéro (.*) avec un solde de (\\d+.\\d*) € et (\\d+) ligne\\(s\\) comptable\\(s\\)$", (String typeCompte, String numero, Double solde, Integer nombreLignesComptables) -> {
            Assertions.assertThat(compte.getTypeCompte()).isEqualToIgnoringCase(typeCompte);
            Assertions.assertThat(compte.getNumeroCompte()).isEqualTo(numero);
            Assertions.assertThat(compte.getValeurCourante()).isEqualTo(solde);
            Assertions.assertThat(compte.getLigneComptables()).isNotNull();
            Assertions.assertThat(compte.getLigneComptables().size()).isEqualTo(nombreLignesComptables);
        });
    }

}
