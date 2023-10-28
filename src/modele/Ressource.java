package modele;

public class Ressource implements Enseignement{
    private Evaluation[] listeEvaluations;
    private String libelle;
    private String identifiant;
    public Ressource(String identifiant, String libelle) {
        this.libelle = libelle;
        this.identifiant = identifiant;
    }
    public boolean ajouterEvaluation(Evaluation aAjouter) {
        //TODO
        return false; //STUB
    }
    public boolean modifierEvaluation(int idEvaluation, int idChamps, String donnee) {
        //TODO
        return false; //STUB
    }
    public boolean supprimerEvaluation(int idEvaluation) {
        //TODO
        return false; //STUB
    }
    private static boolean verifierPonderation(int[] ponderations) {
        //TODO
        return false; //STUB
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
    public Evaluation[] getListeEvaluations() {
        return listeEvaluations;
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
