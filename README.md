# TP sur la qualité logicielle

Exercices de Java, module 350 pour l'IPI. Il est nécessaire de forker ce repository pour pouvoir faire tout le TP !! Après chaque question, pusher vos modifications sur votre repository.

## Pré-requis

- Installer IntelliJ Ultimate en utilisant votre adresse IPI sur Jetbrains Student https://www.jetbrains.com/student/
ou un autre IDE si vous avez l'habitude (Eclipse)
- Si vous n'êtes pas familié avec Git, je vous recommande ce site : https://learngitbranching.js.org/ (faire au moins la première leçon du niveau 1)
Via Microsoft Imagine, activer votre crédit étudiant Azure https://imagine.microsoft.com/fr-fr (ou via le mail reçu en début d'année, onthehub https://onthehub.com/)
- S'inscrire également au programme AWS Educate toujours avec votre adresse IPI (https://aws.amazon.com/fr/education/awseducate/).

## Introduction

Questions à se poser : 
- Que représente pour vous la qualité logicielle ?
- Quels éléments faut-il pour avoir un logiciel de qualité ?
- Dans un projet, qui est responsable de la qualité ?
- Quand doit-on se préoccuper de la qualité dans la vie d'un projet ?

Faire une recherche sur la qualité logicielle sur le web...
Répondre de nouveau aux questions. Qu'est-ce qui a changé ?

## Intégration continue

   - Rajouter la configuration nécessaire pour Travis dans le projet.
   - Vous connecter à Travis https://travis-ci.org avec votre compte Github.
   - Configurer le projet et vérifier que le premier build se passe correctement. Après chaque exercice, vérifier que le build passe toujours...
 
## Evaluation de la qualité

   - Connectez-vous à SonarQube https://about.sonarcloud.io/ avec votre compte Github
   - Ajouter votre projet dans Sonar
   - Modifier votre configuration Travis pour lancer une analyse après chaque build
   - Vérifier que tout est ok
   - Analyser le premier rapport de Sonar

## Tests unitaires

### Tests unitaires classiques

Créer la classe permettant de tester la méthode `getNombreAnneeAnciennete` et mettre en place les tests unitaires nécessaires pour tester le plus exhaustivement possible cette méthode. Bien penser à tous les cas possibles, notamment les cas aux limites. Ne pas hésiter à corriger le code de la méthode initiale si besoin.

### Tests paramétrés

Créer une méthode de test paramétré permettant de tester le plus exhaustivement possible la méthode `getPrimeAnnuelle` et corriger les éventuels problème de cette méthode.

### Tests mockés

Créer la classe de test et les méthodes permettant de tester la méthode `embaucheEmploye` de `EmployeService` sans la dépendance à la BDD.

## Tests d'intégration

### Tests de repository

Créer la classe de test et les méthodes permettant de tester la méthode `findLastMatricule` de `EmployeRepository`.

### Tests de service intégrés

Tester de façon intégrée un cas nominal de la méthode `embaucheEmploye`.

## Tests d'acceptation

TODO

## Maintenabilité

- S'assurer de la lisibilité du code et du respect des conventions
- Ajouter des `logger` aux endroits stratégiques du code en utilisant le bon niveau de log.
- Vérifier et le cas échéant compléter la documentation du code, générer la JavaDoc.
- Ajouter à votre Github une documentation statique avec Jekyll
- Ajouter des badges contenant les métriques principales de votre projet en haut de ce README

# Evaluation

Commencer par faire une branche `evaluation` à partir de votre branche `master` une fois le TP terminé. Travailler sur cette branche pour l'évaluation.

## Tests unitaires et TDD

- Tester de manière unitaire le plus exhaustivement possible la méthode `augmenterSalaire` d'`Employe` en essayant de faire du TDD. Décommenter la méthode dans `Employe` et écrire d'abord les tests entièrement (en réflechissant particulièrement aux cas limites) avant d'écrire la méthode. Pensez-vous que vous auriez écrit la méthode directement comme cela si vous n'aviez pas écrit les tests en premier ?
- Tester unitairement (en utilisant les tests paramétrés) la méthode `getNbRtt` d'`Employe` et corriger les éventuelles erreurs de cette méthode. Rendre cette méthode plus propre, documentée et lisible.

## Tests d'intégration

- Tester sans dépendance à la BDD la méthode `calculPerformanceCommercial` d'`EmployeService`
- Tester de manière intégrée un cas nominal de la méthode précédente
- Tester de manière intégrée la méthode d'`EmployeRepository` `avgPerformanceWhereMatriculeStartsWith`

## Autres

- S'assurer que votre code passe et qu'il n'y a aucun *code smells* ou *anomalies* ou *bugs* bloquants, critiques ou majeurs. Si c'est le cas, corriger le code fourni.
- S'assurer d'avoir 100% de couverture de code sur les méthodes testés dans l'évaluation

## Revue de code

En fin de TP, créer une Pull Request de votre branche `evaluation` vers `master` et mettez-vous d'accord avec un collègue pour qu'il fasse la revue de code. Faites les éventuelles modifications puis affectez-moi la PR.
