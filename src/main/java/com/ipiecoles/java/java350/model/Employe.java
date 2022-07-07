package com.ipiecoles.java.java350.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Entity
public class Employe {


    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String nom;

    private String prenom;

    private String matricule;

    private LocalDate dateEmbauche;

    private Double salaire = Entreprise.SALAIRE_BASE;

    private Integer performance = Entreprise.PERFORMANCE_BASE;

    private Double tempsPartiel = 1.0;

    public Employe() {
    }

    public Employe(String nom, String prenom, String matricule, LocalDate dateEmbauche, Double salaire, Integer performance, Double tempsPartiel) {
        this.nom = nom;
        this.prenom = prenom;
        this.matricule = matricule;
        this.dateEmbauche = dateEmbauche;
        this.salaire = salaire;
        this.performance = performance;
        this.tempsPartiel = tempsPartiel;
    }

    /**
     * Méthode calculant le nombre d'années d'ancienneté à partir de la date d'embauche
     * @return
     */
    public Integer getNombreAnneeAnciennete() {
        if(this.dateEmbauche != null && dateEmbauche.isBefore(LocalDate.now())) {
            return LocalDate.now().getYear() - dateEmbauche.getYear();
        }
        return 0;
    }

    public Integer getNbConges() {
        return Entreprise.NB_CONGES_BASE + this.getNombreAnneeAnciennete();
    }

    public Integer getNbRtt(){
        return getNbRtt(LocalDate.now());
    }

    /**
     *<h1>getNbRtt</h1>
     * Cette fonction permet de déterminer le nombre de RTT d'une année X avec le calcul suivant :
     * (Nombre de jours dans l'année - Nombre de jours travaillés dans l'année en plein temps -
     * Nombre de samedi et dimanche dans l'année - Nombre de jours fériés ne tombant pas le week-end - Nombre de congés payés)*
     * taux de temps partiel (ex : 0.8 pour 80%)
     *
     *
     * @param annee integer de l'année pour laquelle on veut trouver le nombre de RTT
     *
     * @return nombre de RTT d'une année arrondi à l'entier supérieur
     */
    public Integer getNbRtt(LocalDate annee){
        //On récupère le nombre de jours dans l'année
        int nbJoursAnnee = annee.isLeapYear() ? 365 : 366;

        //Portion de code calculant le nombre de jours de weekend dans l'année
        //Début de l'année
        LocalDate startDay = LocalDate.of(annee.getYear(), Month.JANUARY, 1);

        //Fin de l'année, il faut mettre le 1 janvier de l'année d'après
        // pour englober tout l'année concernée
        LocalDate endDay = LocalDate.of(annee.getYear()+1, Month.JANUARY, 1);

        //Predicate qui va servir pour filtrer les jours
        Predicate<LocalDate> isWeekDay = date -> date.getDayOfWeek() != DayOfWeek.SATURDAY && date.getDayOfWeek() != DayOfWeek.SUNDAY;

        //On filtre les jours de weekends dans une liste avec le predicate isWeekDay
        List<LocalDate> listJoursWeekend = startDay.datesUntil(endDay)
                .filter(isWeekDay.negate())
                .collect(Collectors.toList());

        //Récupération du nombre de jours en comptant le nombre d'éléments dans la liste
        int joursWeekEnd = (int) listJoursWeekend.stream().count();

        //calcul des jours fériés hors weekend :
        //le tout arrondi à l'entier supérieur
        int joursFeriesHorsWeekend = (int) Entreprise.joursFeries(annee).stream().filter(localDate ->
                localDate.getDayOfWeek().getValue() <= DayOfWeek.FRIDAY.getValue()).count();

        return (int) Math.ceil((nbJoursAnnee - Entreprise.NB_JOURS_MAX_FORFAIT - joursWeekEnd - Entreprise.NB_CONGES_BASE - joursFeriesHorsWeekend) * tempsPartiel);
    }

    /**
     * Calcul de la prime annuelle selon la règle :
     * Pour les managers : Prime annuelle de base bonnifiée par l'indice prime manager
     * Pour les autres employés, la prime de base plus éventuellement la prime de performance calculée si l'employé
     * n'a pas la performance de base, en multipliant la prime de base par un l'indice de performance
     * (égal à la performance à laquelle on ajoute l'indice de prime de base)
     *
     * Pour tous les employés, une prime supplémentaire d'ancienneté est ajoutée en multipliant le nombre d'année
     * d'ancienneté avec la prime d'ancienneté. La prime est calculée au pro rata du temps de travail de l'employé
     *
     * @return la prime annuelle de l'employé en Euros et cents
     */
    //Matricule, performance, date d'embauche, temps partiel, prime
    public Double getPrimeAnnuelle(){
        //Calcule de la prime d'ancienneté
        Double primeAnciennete = Entreprise.PRIME_ANCIENNETE * this.getNombreAnneeAnciennete();
        Double prime;
        //Prime du manager (matricule commençant par M) : Prime annuelle de base multipliée par l'indice prime manager
        //plus la prime d'anciennté.
        if(matricule != null && matricule.startsWith("M")) {
            prime = Entreprise.primeAnnuelleBase() * Entreprise.INDICE_PRIME_MANAGER + primeAnciennete;
        }
        //Pour les autres employés en performance de base, uniquement la prime annuelle plus la prime d'ancienneté.
        else if (this.performance == null || Entreprise.PERFORMANCE_BASE.equals(this.performance)){
            prime = Entreprise.primeAnnuelleBase() + primeAnciennete;
        }
        //Pour les employés plus performance, on bonnifie la prime de base en multipliant par la performance de l'employé
        // et l'indice de prime de base.
        else {
            prime = Entreprise.primeAnnuelleBase() * (this.performance + Entreprise.INDICE_PRIME_BASE) + primeAnciennete;
        }
        //Au pro rata du temps partiel.
        return prime * this.tempsPartiel;
    }

    //Augmenter salaire
    public void augmenterSalaire(Double pourcentage) {
        if(pourcentage != null && pourcentage > 0) {
            salaire = salaire * 1+pourcentage/100;
            Math.ceil(salaire);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * @param nom the nom to set
     */
    public Employe setNom(String nom) {
        this.nom = nom;
        return this;
    }

    /**
     * @return the prenom
     */
    public String getPrenom() {
        return prenom;
    }

    /**
     * @param prenom the prenom to set
     */
    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    /**
     * @return the matricule
     */
    public String getMatricule() {
        return matricule;
    }

    /**
     * @param matricule the matricule to set
     */
    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    /**
     * @return the dateEmbauche
     */
    public LocalDate getDateEmbauche() {
        return dateEmbauche;
    }

    /**
     * @param dateEmbauche the dateEmbauche to set
     */
    public void setDateEmbauche(LocalDate dateEmbauche) {
        this.dateEmbauche = dateEmbauche;
    }

    /**
     * @return the salaire
     */
    public Double getSalaire() {
        return salaire;
    }

    /**
     * @param salaire the salaire to set
     */
    public void setSalaire(Double salaire) {
        this.salaire = salaire;
    }

    public Integer getPerformance() {
        return performance;
    }

    public void setPerformance(Integer performance) {
        this.performance = performance;
    }

    public Double getTempsPartiel() {
        return tempsPartiel;
    }

    public void setTempsPartiel(Double tempsPartiel) {
        this.tempsPartiel = tempsPartiel;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employe)) return false;
        Employe employe = (Employe) o;
        return Objects.equals(id, employe.id) &&
                Objects.equals(nom, employe.nom) &&
                Objects.equals(prenom, employe.prenom) &&
                Objects.equals(matricule, employe.matricule) &&
                Objects.equals(dateEmbauche, employe.dateEmbauche) &&
                Objects.equals(salaire, employe.salaire) &&
                Objects.equals(performance, employe.performance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, nom, prenom, matricule, dateEmbauche, salaire, performance);
    }

    @Override
    public String toString() {
        return "Employe{" +
                "id=" + id +
                ", nom='" + nom + '\'' +
                ", prenom='" + prenom + '\'' +
                ", matricule='" + matricule + '\'' +
                ", dateEmbauche=" + dateEmbauche +
                ", salaire=" + salaire +
                ", performance=" + performance +
                ", tempsPartiel=" + tempsPartiel +
                '}';
    }
}
