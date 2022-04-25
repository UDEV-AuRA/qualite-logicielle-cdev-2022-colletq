package com.ipiecoles.java.java350;

import java.io.*;
import java.text.*;
import java.util.*;

public class LigneComptable implements Serializable {
    private double valeur;
    private String date;
    private String motif;
    private String mode;

    public LigneComptable() {
        Scanner lectureClavier = new Scanner(System.in);
        System.out.print("Entrer la valeur à créditer (+) ou débiter (-) : ");
        valeur = lectureClavier.nextDouble();
        motif = controleMotif();
        mode = controleMode();
        date = controleDate();
    }

    private String controleDate() {
        Scanner lectureClavier = new Scanner(System.in);
        int nb = 0;
        Date d = null;
        SimpleDateFormat formatIn = new SimpleDateFormat("dd/MM/yyyy");
        String sdate;
        while (d == null) {
            try {
                System.out.print("Entrer une date (jj/mm/aaaa): ");
                d = formatIn.parse(lectureClavier.next());
            } catch (ParseException p) {
                nb++;
                if (nb >= 3) d = new Date();
            }
        }
        sdate = formatIn.format(d);
        return sdate;
    }

    /*
    public LigneComptable()	{
                Scanner lectureClavier = new Scanner(System.in);
        System.out.print("Entrer la valeur à créditer (+) ou débiter (-) : ");
        valeur = lectureClavier.nextDouble();
        System.out.print("Date de l'opération [jj/mm/an] ");
        date = lectureClavier.next();
        motif = controleMotif();
        mode = controleMode();
    } */
    private String controleMode() {
        Scanner lectureClavier = new Scanner(System.in);
        String tmpS = "";
        char tmpc;
        do {
            System.out.print("Mode [C(B), N(° Cheque), V(irement ) ]  : ");
            tmpc = lectureClavier.next().charAt(0);
        } while (tmpc != 'C' && tmpc != 'N' && tmpc != 'V');
        switch (tmpc) {
            case 'C':
                tmpS = "CB";
                break;
            case 'N':
                tmpS = "Cheque";
                break;
            case 'V':
                tmpS = "Virement";
                break;
        }
        return tmpS;
    }

    private String controleMotif() {
        Scanner lectureClavier = new Scanner(System.in);
        String tmpS = "";
        char tmpc;
        do {
            System.out.print("Motif de l'operation [S(alaire),");
            System.out.print(" L(oyer), A(limentation), D(ivers)] : ");
            tmpc = lectureClavier.next().charAt(0);
        } while (tmpc != 'S' && tmpc != 'L' && tmpc != 'A' && tmpc != 'D');
        switch (tmpc) {
            case 'S':
                tmpS = "Salaire";
                break;
            case 'L':
                tmpS = "Loyer";
                break;
            case 'A':
                tmpS = "Alimentation";
                break;
            case 'D':
                tmpS = "Divers";
                break;
        }
        return tmpS;
    }

    public double getValeur() {
        return valeur;
    }

    public String getDate() {
        return date;
    }

    public String getMotif() {
        return motif;
    }

    public String getMode() {
        return mode;
    }

    public void afficherLigne() {
        if (valeur < 0)
            System.out.print("Débiter : " + valeur);
        else
            System.out.print("Créditer : " + valeur);
        System.out.println(" le : " + date + " motif  : " + motif + " mode : " + mode);
    }
}