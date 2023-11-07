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
        ajoutOk=true;
        listeEvaluations.add(aAjouter);
        if(!verifierPonderation(this.getPonderations())){
        	listeEvaluations.remove(aAjouter);
        	ajoutOk = false;
        }
        return ajoutOk; 
    }
    /**
     * 
     * @param idEvaluation
     * @param idChamps
     * @param donnee
     * @return true
     */
    public boolean modifierEvaluation(int idEvaluation, int idChamps, String donnee) {
        boolean modifOk;
        modifOk = false;
        if(idEvaluation <= listeEvaluations.size() && idEvaluation >= 0) {
            switch(idChamps) {
                case 1:
                    try {
                        listeEvaluations.get(idEvaluation).setNom(donnee);
                        modifOk = true;
                        break;
                    }catch(IllegalArgumentException erreur) {
                        System.out.print(erreur.getMessage());
                    }
                case 2:
                    try {
                        listeEvaluations.get(idEvaluation).setDate(donnee);
                        modifOk = true;
                        break;
                    }catch(IllegalArgumentException erreur) {
                        System.out.print(erreur.getMessage());
                    }   
                case 3:
                    try {
                        listeEvaluations.get(idEvaluation).setNote(Double.parseDouble(donnee));
                        modifOk = true;
                        break;
                    }catch(IllegalArgumentException erreur) {
                        System.out.print(erreur.getMessage());
                    }              
                case 4:
                    try {
                        listeEvaluations.get(idEvaluation).setPoids(Integer.parseInt(donnee));
                        modifOk = true;
                        break;
                    }catch(IllegalArgumentException erreur) {
                        System.out.print(erreur.getMessage());
                    }
            }
        }
        return modifOk; 
    }
    /**
     * 
     * @param idEvaluation
     * @return
     */
    public boolean supprimerEvaluation(int idEvaluation) {
        boolean suprOk;
        suprOk = false;
        Evaluation sauvegarde;
        if(idEvaluation <= listeEvaluations.size() && idEvaluation >= 0) {
            suprOk = true;
            sauvegarde = listeEvaluations.get(idEvaluation);
            listeEvaluations.remove(idEvaluation);
        }
        return suprOk; 
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
        return         poidsTotal >= 0
        	    && poidsTotal <= 100; 
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
        return this.getLibelle() +" "+ this.getIdentifiant();
    }
    /**
     * 
     * @return
     */
    public String getListeEvaluations() {
        return listeEvaluations.toString();
    }
    /**
     * 
     * @return
     */
    public double calculerMoyenne() {
    	double moyenne;
    	moyenne = 0.0;
        if(isCalculable() && this.getPoidsTotal() == 100) {
            for(int i = 0; i<listeEvaluations.size();i++) {
                moyenne += listeEvaluations.get(i).getNote()*listeEvaluations.get(i).getPoids();
            }
            moyenne = moyenne / 100;
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
    	    calculOk=true;
    	    for(int i =0;i<this.listeEvaluations.size() && calculOk;i++) {
    	        if(listeEvaluations.get(i).getNote() == 0.0) {
    	            calculOk = false;
    	        }
    	    }
    	}
        return calculOk; 
    }
    
    /**
     * 
     * @return
     */
    private ArrayList<Integer> getPonderations(){
    	ArrayList<Integer> ponderations;
        ponderations = new ArrayList<>();
        
        for(int i = 0;i<listeEvaluations.size();i++){
        	ponderations.add(listeEvaluations.get(i).getPoids());
        }
	return ponderations;
    	
    }
    
    /**
     * TODO comment method role
     * @return
     */
    private int getPoidsTotal() {
        int poidsTotal;
        poidsTotal = 0;
        for (int i =0 ; i<this.getPonderations().size();i++) {
            poidsTotal += this.getPonderations().get(i);
        }
        return poidsTotal;
    }
    
}
