package com.ipiecoles.java.java350;

import java.util.*;
import java.io.*;

public class ListeCompte implements Serializable {
    private HashMap<String, Compte> liste;

    public ListeCompte() {
        liste = new HashMap<String, Compte>();
    }

    public void ajouteUnCompte(String t) {
        Compte nouveau = new Compte("");
        if (t.equalsIgnoreCase("A")) nouveau = new Compte();
        else if (t.equalsIgnoreCase("E")) nouveau = new CompteEpargne();
        String clé = nouveau.getNumeroCompte();
        if (liste.get(clé) == null) liste.put(clé, nouveau);
        else System.out.println("Ce compte existe deja !");
    }

    public void ajouteUneLigne(String n) {
        String clé = n;
        Compte c = (Compte) liste.get(clé);
        if (c != null) c.creerLigne();
        else System.out.println("Le systeme ne connait pas le compte " + n);
    }

    public Compte quelCompte(String n) {
        String clé = n;
        Compte c = (Compte) liste.get(clé);
        if (c == null)
            System.out.println("Le systeme ne connait pas le compte " + n);
        return (c);
    }

    public Compte rechercheUnCompte(String n) {
        String clé = n;
        Compte c = (Compte) liste.get(clé);
        if (c != null) c.afficherCompte();
        else System.out.println("Le systeme ne connait pas le compte " + n);
        return c;
    }

    public void supprimeUnCompte(String n) {
        String clé = n;
        Compte c = (Compte) liste.get(clé);
        if (c != null) {
            liste.remove(clé);
            System.out.println(n + " a été supprimé ");
        } else System.out.println(n + " est inconnu ! ");
    }

    public void afficheLesComptes() {
        if (liste.size() != 0) {
            Collection<Compte> colCompte = liste.values();
            for (Compte c : colCompte) c.afficherCompte();
        } else System.out.println("Aucun compte n'a ete cree, dans cette liste");
    }

}
