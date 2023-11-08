package modele;

import java.util.HashMap;

/** Classe objet permettant de représenter une compétence à partir
 * de son identifiant, de son libellé, de la liste de ses ressources
 * et de la liste de ses saes.
 * @author noah.miquel, jodie.monterde, benjamin.nicol, ugo.schardt
 */
public class Competence implements Enseignement{
    private HashMap<Ressource, Double> listeRessources;
    private HashMap<Sae, Double> listeSaes;
    private String libelle;
    private String identifiant;

    /** Constructeur de la classe Competence
     * @param identifiant
     * @param libelle
     */
    public Competence(String identifiant, String libelle) {
        if (libelle == null || libelle.equals("") || identifiant == null || identifiant.equals("")) {
            throw new IllegalArgumentException();
        }
        this.libelle = libelle;
        this.identifiant = identifiant;
        this.listeRessources = new HashMap<Ressource, Double>();
        this.listeSaes = new HashMap<Sae, Double>();
    }
    
    /** Getter de l'attribut libelle
     * @return libelle : le libellé de la compétence
     */
    public String getLibelle() {
        return libelle;
    }
    
    /** Getter de l'attribut identifiant
     * @return identifiant : l'identifiant de la compétence
     */
    public String getIdentifiant() {
        return identifiant;
    }
    
    /** Méthode permettant de créer l'intitulé de la compétence
     * @return intitule : correspond à l'identifiant + le libellé
     */
    public String creerIntitule() {
        String intitule = this.identifiant + " " + this.libelle;
        return intitule;
    }
    
    /** Getter de l'attribut listeRessources
     * @return listeRessources : la liste des ressources de la compétence
     */
    public HashMap<Ressource, Double> getListeRessources() {
        return listeRessources;
    }
    
    /** Getter de l'attribut listeSaes
     * @return listeSaes : la liste des saes de la compétence
     */
    public HashMap<Sae, Double> getListeSaes() {
        return listeSaes;
    }
    
    /** Setter de l'attribut listeRessources
     * @param listeRessources
     */
    public void setListeRessources(HashMap<Ressource, Double> listeRessources) {
        this.listeRessources = listeRessources;
    }
    
    /** Setter de l'attribut listeSaes
     * @param listeSaes
     */
    public void setListeSaes(HashMap<Sae, Double> listeSaes) {
        this.listeSaes = listeSaes;
    }
    
    /** Méthode permettant de calculer la note obtenue à la compétence
     * @return moyenne : la note obtenue à la compétence
     */
    public Double calculerMoyenne() {
        Double moyenne = null;
        if (this.isCalculable()) {
            moyenne = 0.0;
            for (Ressource i : listeRessources.keySet()) {
                moyenne += i.calculerMoyenne() * listeRessources.get(i);
            }
            for (Sae i : listeSaes.keySet()) {
                moyenne += i.getNote() * listeSaes.get(i);
            }
        }
        return moyenne; //Somme des coefficients == 1 donc pas besoin de diviser
    }
    
    /** Méthode permettant de vérifier si la moyenne
     * d'une compétence est calculable
     * @return calculable : true si calculable, false sinon
     */
    public boolean isCalculable() {
        boolean calculable = true;
        for (Ressource i : listeRessources.keySet()) {
            if(!i.isCalculable()) {
                calculable = false;
                break;
            }
        }
        if (calculable) {
            for (Sae i : listeSaes.keySet()) {
                if (i.getNote() == null) {
                    calculable = false;
                    break;
                }
            }
        }
        return calculable;
    }
}
