package modele;

public class Evaluation {
    private String nom;
    private int poids;
    private String date;
    private double note;
    public Evaluation(String nom, int poids, String date) {
        this.nom = nom;
        this.poids = poids;
        this.date = date;
    }
    public String getNom() {
        return nom;
    }
    public int getPoids() {
        return poids;
    }
    public String getDate() {
        return date;
    }
    public double getNote() {
        return note;
    }
    public void setNom(String nom) {
        this.nom = nom;
    }
    public void setPoids(int poids) {
        this.poids = poids;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public void setNote(double note) {
        this.note = note;
    }

}
