Feature: Compte

  Scenario: Création d'un compte
    When Je crée un compte courant de numéro 123456 avec un solde de 500 €
    Then J'obtiens bien un compte courant de numéro 123456 avec un solde de 500.0 € et 0 ligne(s) comptable(s)