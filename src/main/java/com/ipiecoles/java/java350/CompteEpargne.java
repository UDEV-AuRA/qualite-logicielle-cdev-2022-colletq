package com.ipiecoles.java.java350;

import java.util.*;
import java.io.*;

public class CompteEpargne extends Compte implements Serializable {

    private double taux;

    public CompteEpargne() {
        super("Epargne");
        taux = controleTaux();
    }

    private double controleTaux() {
        double tmp;
        Scanner lectureClavier = new Scanner(System.in);
        do {
            System.out.print("Taux de placement :     ");
            tmp = lectureClavier.nextDouble();
        } while (tmp < 0);
        return tmp;
    }

    public void afficherCompte() {
        super.afficherCompte();
        System.out.println(" Taux d'epargne du compte :  " + taux);
    }

    public double getTaux() {
        return taux;
    }
}