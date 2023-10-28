package modele;

import java.util.HashMap;

public class Competence implements Enseignement{
    private HashMap<Ressource, Double> listeRessources;
    private HashMap<Sae, Double> listeSaes;
    private String libelle;
    private String identifiant;

    public Competence(String identifiant, String libelle) {
        this.libelle = libelle;
        this.identifiant = identifiant;
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
    public HashMap<Ressource, Double> getListeRessources() {
        return listeRessources;
    }
    public HashMap<Sae, Double> getListeSaes() {
        return listeSaes;
    }
    public void setListeRessources(HashMap<Ressource, Double> listeRessources) {
        this.listeRessources = listeRessources;
    }
    public void setListeSaes(HashMap<Sae, Double> listeSaes) {
        this.listeSaes = listeSaes;
    }
    public double calculerMoyenne() {
        //TODO
        return 0.0; //STUB
    }
    private boolean isCalculable() {
        //TODO
        return false; //STUB
    }
}
