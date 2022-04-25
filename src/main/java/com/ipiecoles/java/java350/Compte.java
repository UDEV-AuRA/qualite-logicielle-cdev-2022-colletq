package com.ipiecoles.java.java350;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Scanner;

public class Compte implements Serializable {
    private String typeCompte;
    protected Double valeurCourante;
    private String numeroCompte;
    private ArrayList<LigneComptable> ligneComptables;
    public static final int NB_LIGNE = 10;
    private int nbLigneReel;
    private Scanner lectureClavier;

    public Compte() {
        lectureClavier = new Scanner(System.in);
        typeCompte = controleType();
        System.out.print("Numéro du compte : ");
        numeroCompte = lectureClavier.next();
        valeurCourante = controleValinit();
        ligneComptables = new ArrayList();
        nbLigneReel = -1;
    }

    public Compte(String type) {
        lectureClavier = new Scanner(System.in);
        if (type.equalsIgnoreCase("Epargne")) {
            typeCompte = type;
            System.out.print("Numéro du compte : ");
            numeroCompte = lectureClavier.next();
            valeurCourante = controleValinit();
            ligneComptables = new ArrayList();
            nbLigneReel = -1;
        }
    }

    public LigneComptable quelleLigne(int n) {
        return ligneComptables.get(n);
    }



    private String controleType() {
        char tmpc;
        String tmpS = "";
        do {
            System.out.print("Type du compte [Types possibles :");
            System.out.print("C(ourant), J(oint)] : ");
            tmpc = lectureClavier.next().charAt(0);
        } while (tmpc != 'C' && tmpc != 'J');
        switch (tmpc) {
            case 'C':
                tmpS = "Courant";
                break;
            case 'J':
                tmpS = "Joint";
                break;
        }
        return tmpS;
    }

    private Double controleValinit() {
        Double tmp, tmpval;
        do {
            System.out.print("Valeur initiale du compte : ");
            tmpval = lectureClavier.nextDouble();
        } while (tmpval <= 0);
        return tmpval;
    }

    public void creerLigne() {
        LigneComptable ligne = new LigneComptable();
        insererLigne(ligne);
        valeurCourante = valeurCourante + ligne.getValeur();
    }

    private void insererLigne(LigneComptable ligneComptable) {
        ligneComptables.add(ligneComptable);
    }

    public void afficherCompte() {
        System.out.print("Le compte n° : " + numeroCompte);
        System.out.println(" est un compte " + typeCompte);
        ligneComptables.stream().forEach(LigneComptable::afficherLigne);

        System.out.println("Valeur courante : " + valeurCourante);
        if (valeurCourante < 0) System.out.println("Attention compte débiteur ... !!!");
    }

    public void genererReleve(byte mois, short annee) throws IOException {
        if(mois <= 0 || mois > 12){
            throw new IllegalArgumentException("Le mois doit être compris entre 1 et 12 : " + mois);
        }

        if(annee < 2000 || annee > Calendar.getInstance().get(Calendar.YEAR)){
            throw new IllegalArgumentException("L'année doit être comprise entre 2000 et l'année courante : " + annee);
        }
        String nomFichier = numeroCompte + "_" + annee + "_" + mois + "_releve.txt";
        BufferedWriter  releve = new BufferedWriter(new FileWriter(nomFichier));
        releve.write("Relevé du mois de " + mois + " " + annee + "\n\n");
        releve.write("Date\t\t\tMotif\t\t\tMode\t\t\tValeur\n");
        ligneComptables.stream().forEach(ligneComptable -> {
            try {
                releve.write(ligneComptable.getDate() + "\t\t" +
                        ligneComptable.getMotif() + "\t\t\t" +
                        ligneComptable.getMode() + "\t\t" +
                        ligneComptable.getValeur() + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        releve.write("\n\nSolde : " + valeurCourante);

        releve.flush();
        releve.close();
        System.out.println("Relevé généré dans le fichier " + nomFichier);
    }

    public String getNumeroCompte() {
        return numeroCompte;
    }

    public String getTypeCompte() {
        return typeCompte;
    }

    public Double getValeurCourante() {
        return valeurCourante;
    }

    public ArrayList<LigneComptable> getLigneComptables() {
        return ligneComptables;
    }
}