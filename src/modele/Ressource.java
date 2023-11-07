/*
 * 
 */

package modele;

import java.util.ArrayList;

/**
 * 
 */
public class Ressource implements Enseignement{
	
    private ArrayList<Evaluation> listeEvaluations;
    private String libelle;
    private String identifiant;
    /**
     * 
     * @param identifiant
     * @param libelle
     */
    public Ressource(String identifiant, String libelle) {
        this.libelle = libelle;
        this.identifiant = identifiant;
        listeEvaluations = new ArrayList<>();
    }
    /**
     * 
     * @param aAjouter
     * @return
     */
    public boolean ajouterEvaluation(Evaluation aAjouter) {
        boolean ajoutOk;
        ajoutOk=false;
        
        if(verifierPonderation(this.getPonderations())){
        	listeEvaluations.add(aAjouter);
        	ajoutOk= true;
        }
        return ajoutOk; 
    }
    /**
     * 
     * @param idEvaluation
     * @param idChamps
     * @param donnee
     * @return
     */
    public boolean modifierEvaluation(int idEvaluation, int idChamps, String donnee) {
        
        return false; //STUB
    }
    /**
     * 
     * @param idEvaluation
     * @return
     */
    public boolean supprimerEvaluation(int idEvaluation) {
        boolean suprOk;
        suprOk = false;
        return false; //STUB
    }
    /**
     * 
     * @param ponderations
     * @return
     */
    private static boolean verifierPonderation(ArrayList<Integer> ponderations) {
        int poidsTotal;
        poidsTotal =0;
        for(int i = 0;i<ponderations.size();i++) {
        	poidsTotal += ponderations.get(i);
        }
        return     poidsTotal > 0
        	    && poidsTotal <=100; 
    }
    /**
     * 
     */
    public String getLibelle() {
        return libelle;
    }
    /**
     * 
     */
    public String getIdentifiant() {
        return identifiant;
    }
    /**
     * 
     */
    public String creerIntitule() {
        return this.getLibelle() + this.getIdentifiant();
    }
    /**
     * 
     * @return
     */
    public ArrayList<Evaluation> getListeEvaluations() {
        return listeEvaluations;
    }
    /**
     * 
     * @return
     */
    public double calculerMoyenne() {
    	double moyenne;
    	moyenne = 0.0;
        if(isCalculable()) {
        	
        }
        return moyenne; 
    }
    /**
     * 
     * @return true si on peut calculer la moyenne, sinon false
     */
    public boolean isCalculable() {
    	boolean calculOk;
    	calculOk = false;
    	if(verifierPonderation(this.getPonderations())) {
    		
    	}
        return false; 
    }
    
    /**
     * 
     * @return
     */
    public ArrayList<Integer> getPonderations(){
    	ArrayList<Integer> ponderations;
        ponderations = new ArrayList<>();
        
        for(int i = 0;i<listeEvaluations.size();i++){
        	ponderations.add(listeEvaluations.get(i).getPoids());
        }
		return ponderations;
    	
    }
    
}
