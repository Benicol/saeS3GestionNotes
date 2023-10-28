package modele;

public class Parametrage {
    private String semestre;
    private String parcours;
    private Ressource[] listeRessources;
    private Competence[] listeCompetences;
    private Sae[] listeSaes;

    public void Parametrage(String semestre, String parcours, String[][] donneesCompetences, String[][] donneesSaes, String[][] donneesRessources) {
        //TODO
    }
    public String getSemestre() {
        return semestre;
    }
    public String getParcours() {
        return parcours;
    }
    public Ressource[] getListeRessources() {
        return listeRessources;
    }
    public Competence[] getListeCompetences() {
        return listeCompetences;
    }
    public Sae[] getListeSaes() {
        return listeSaes;
    }
}
