package com.ipiecoles.java.java350;

/**
 * Created by Pijey on 04/11/2017.
 */

import java.io.IOException;
import java.util.*;

public class Main {
    public static void main (String [] argument) throws IOException {
        byte choix = 0 ;
        String numeroLu = "";
        // Les classes ListeCompte et FichierCompte sont définies dans le dossier "commun"
        ListeCompte C = new ListeCompte();
        FichierCompte F = new FichierCompte();
        Scanner lectureClavier = new Scanner(System.in);
        if (F.ouvrir("L")) {
            C = F.lire();
            F.fermer();
        }
        do {
            choix = menuPrincipal();
            switch (choix)	{
                case 1 :
                    System.out.print (" Compte Epargne (o/n) : ");
                    if (lectureClavier.next().charAt(0) == 'o')
                        C.ajouteUnCompte("E") ;
                    else
                        C.ajouteUnCompte("A");
                    break;
                case 2 :
                    System.out.print ( "Quel compte souhaitez vous afficher ? : ");
                    numeroLu = lectureClavier.next();
                    C.rechercheUnCompte(numeroLu);
                    break;
                case 3 :
                    C.afficheLesComptes();
                    break;
                case 4 :
                    System.out.print ( "Pour quel compte souhaitez vous créer une ligne ? : ");
                    numeroLu = lectureClavier.next();
                    C.ajouteUneLigne(numeroLu);
                    break;
                case 5 :
                    System.out.print ( "Quel compte souhaitez vous supprimer ? : ");
                    numeroLu = lectureClavier.next();
                    C.supprimeUnCompte(numeroLu);
                    break;
                case 6 :
                    System.out.println("Sauvegarde des données dans Compte.dat");
                    F.ouvrir("E");
                    F.ecrire(C);
                    F.fermer();
                    sortir();
                    break;
                case 7 :
                    System.out.print ( "Pour quel compte souhaitez vous générer un relevé ? : ");
                    numeroLu = lectureClavier.next();
                    Compte compte = C.rechercheUnCompte(numeroLu);
                    System.out.println("Pour quelle année souhaitez-vous générer un relevé ?");
                    short annee = lectureClavier.nextShort();
                    System.out.println("Pour quel mois de l'année " + annee + " ?");
                    byte mois = lectureClavier.nextByte();
                    compte.genererReleve(mois, annee);
                    break;
                case 8 :
                    alAide();
                    break;
                default :
                    System.out.println("Cette option n'existe pas ");
            }
        } while (choix != 6);
    }
    public static byte menuPrincipal() {
        byte tmp;
        Scanner lectureClavier = new Scanner(System.in);
        System.out.println("1. Création d'un compte");
        System.out.println("2. Affichage d'un compte");
        System.out.println("3. Affichage de tous les comptes");
        System.out.println("4. Ecrire une ligne comptable");
        System.out.println("5. Supprimer un compte ");
        System.out.println("6. Sortir");
        System.out.println("7. Générer un relevé");
        System.out.println("8. De l'aide");
        System.out.println();
        System.out.print("Votre choix : ");
        tmp  = lectureClavier.nextByte();
        return tmp;
    }
    public static void sortir( ) {
        System.out.println("Au revoir et a bientot");
        System.exit(0) ;
    }
    public static void alAide( ) {
        System.out.println("Option 1. Pour creer un compte Courant entrer C ");
        System.out.println("          Pour creer un compte Joint entrer J ");
        System.out.println("          Pour creer un compte Epargne entrer E");
        System.out.print("            Puis, entrer le numero du compte, et");
        System.out.println(" sa premiere valeur creditee ");
        System.out.println("          Dans le cas d'un compte epargne, entrer le taux ");
        System.out.println("Option 2. Le systeme affiche les donnees du compte choisi ");
        System.out.println("Option 3. Ecrire une ligne comptable");
        System.out.println("Option 4. Pour quitter le programme");
        System.out.println("Option 5. Pour afficher de l'aide");
    }
}
