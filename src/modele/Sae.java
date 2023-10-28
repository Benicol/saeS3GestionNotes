package modele;

public class Sae implements Enseignement{
    private String libelle;
    private String identifiant;
    private double note;

    public Sae(String identifiant, String libelle) {
        this.libelle = libelle;
        this.identifiant = identifiant;
    }
    public double getNote() {
        return note;
    }
    public void setNote() {
        this.note = note;
    }
    public String getLibelle() {
        return libelle;
    }
    public String getIdentifiant() {
        return identifiant;
    }
    public String creerIntitule() {
        //TODO
        return ""; //STUB
    }
}
