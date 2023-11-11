package controleur;

import modele.Competence;
import modele.Evaluation;
import modele.Modele;
import modele.Ressource;
import modele.Sae;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

/** 
 * Controleur de la vue vue.Homepage.fxml
 */
public class VueHomepageController {
    /** Bouton invisible contenant le nom de l'utilisateur*/
    @FXML
    private Button boutonUtilisateur;
    /** listePrincipal, VBox central de l'application */
    @FXML
    private VBox listePrincipal;
    /** liste des ressources à gauche */
    @FXML
    private VBox listeRessources;
    /** liste des saes à gauche */
    @FXML
    private VBox listeSaes;
    /** liste des competences à gauche */
    @FXML
    private VBox listesCompetences;
    /** bouton Séléectionner dans le menu à gauche */
    private Button selected;
    
    //Zone d'initialisation de l'applciation
    
    /**
     * Effectue les traitement suivant dans cette ordre : 
     * - si le parametrage = null, alors lance la vue importer
     * - Met en grand la fenetre
     * - Met à jour l'utilisateur
     * - affiche les competences
     * - affiche les ressources
     * - affiche les saes
     * @throws Exception 
     * 
     */
    @FXML
    void initialize() throws Exception {
        selected = null;
        // si le parametrage = null, alors lance la vue importer
        if (Modele.getParametrage() == null) {
            System.err.println("LANCE LA VUE PAS FAITE LA");
        }
        // Met en grand la fenetre
        EchangeurDeVue.getPrimaryStage().setMaximized(true);
        // Met à jour l'utilisateur
        boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
        // affiche les competences
        setVBoxListeCompetence();
        // affiche les ressources
        setVBoxListeRessouces();
        // affiche les saes
        setVBoxListeSaes();
    }
    
    private void setVBoxListeCompetence() throws Exception {
        String[] keys = Modele.getCompetences().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        competenceCliquer(Modele.getCompetences().get(key));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                button.setText(Modele.getCompetences().get(key).creerIntitule());
                listesCompetences.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (1) : " + e.getMessage());
            }
        }
    }
    
    private void setVBoxListeRessouces() throws Exception {
        String[] keys = Modele.getRessources().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        ressourceCliquer(Modele.getRessources().get(key));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                button.setText(Modele.getRessources().get(key).creerIntitule());
                listeRessources.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (2) : " + e.getMessage());
            }
        }
    }

    private void setVBoxListeSaes() throws Exception {
        String[] keys = Modele.getSae().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        saeCliquer(Modele.getSae().get(key));
                    } catch (Exception e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                });
                button.setText(Modele.getSae().get(key).creerIntitule());
                listeSaes.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (3) : " + e.getMessage());
            }
        }
    }
    
    //Zone Reactiviter

    @FXML
    void afficherMesMoyennesPresser(ActionEvent event) {
        System.out.println("moyennes presser");
    }

    @FXML
    void boutonPleinEntree(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("primary-button-not-hover");
        bouton.getStyleClass().add("primary-button-hover");
    }

    @FXML
    void boutonPleinSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("primary-button-hover");
        bouton.getStyleClass().add("primary-button-not-hover");
    }

    @FXML
    void boutonVideEntree(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-hover");
    }

    @FXML
    void boutonVideSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }

    @FXML
    void exporterPresser(ActionEvent event) {
        System.out.println("exporter presser");
    }

    @FXML
    void importerPresser(ActionEvent event) {
        System.out.println("importer presser");
    }

    @FXML
    void reinitialiserPresser(ActionEvent event) {
        System.out.println("reset presser");
    }

    @FXML
    void utilisateurEntree(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-not-hover");
        bouton.getStyleClass().add("utilisateur-hover");
    }

    @FXML
    void utilisateurPresser(ActionEvent event) {
        System.out.println("utilisateur presser");
    }

    @FXML
    void utilisateurSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-hover");
        bouton.getStyleClass().add("utilisateur-not-hover");
    }
    
    private void eltMenuSelectionner(Button button) {
        if (selected != null) {
            selected.getStyleClass().remove("side-nav-element-active");
            selected.getStyleClass().add("side-nav-element-inactive");
        }
        selected = button;
        selected.getStyleClass().remove("side-nav-element-inactive");
        selected.getStyleClass().add("side-nav-element-active");
    }
    
    //COMPETENCE
    
    private void competenceCliquer(Competence competence) throws Exception {
        viderListePrincipal();
        ajouterTitreComeptence(competence);
    }
    
    private void ajouterTitreComeptence(Competence competence) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTC")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Text titre = ((Text) ((HBox) hbox.getChildren().get(0)).getChildren().get(0));
            Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
            titre.setText(competence.creerIntitule());
            if (competence.isCalculable()) {
                note.setText(competence.calculerMoyenne().toString()); 
            } else {
                note.setText("Moyenne indisponible");
                diviseur.setText("");
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    
    //RESSOURCE NORMAL
    
    private void ressourceCliquer(Ressource ressource) throws Exception {
        viderListePrincipal();
        ajouterTitreRessource(ressource);
        ArrayList<Evaluation> listeEvaluation = ressource.getListeEvaluations();
        if (listeEvaluation.isEmpty()){
            afficherEvaluationVide();
        } else {
            for (Evaluation eval : listeEvaluation) {
                ajouterEvaluation(eval);
            }
        }
    }
    
    private void ajouterTitreRessource(Ressource ressource) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTR")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Text titre = (Text)((HBox) ((HBox) hbox.getChildren().get(0))
                                                   .getChildren().get(0))
                                                   .getChildren().get(0);
            Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
            Button bouton = (Button)((HBox) hbox.getChildren().get(0))
                                                .getChildren().get(1);
            bouton.setOnMouseEntered((event) -> boutonPleinEntree(event));
            bouton.setOnMouseExited((event) -> boutonPleinSortie(event));
            bouton.setOnAction((event) -> {
                try {
                    modaliteCliquer(ressource);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            titre.setText(ressource.creerIntitule());
            if (ressource.isCalculable()) {
                note.setText(ressource.calculerMoyenne().toString()); 
            } else {
                note.setText("Moyenne indisponible");
                diviseur.setText("");
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (7) : " + e.getMessage());
        }
    }
    
    private void ajouterEvaluation(Evaluation eval) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEval")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            //Récupération de tous les éléments
            VBox colonneGauche = (VBox) hbox.getChildren().get(0);
            HBox ligneHaute = (HBox) colonneGauche.getChildren().get(0);
            Text ponderation = (Text) ((HBox) ligneHaute.getChildren().get(0)).getChildren().get(0);
            System.out.println(3);
            Text nomEval = (Text) ligneHaute.getChildren().get(1);
            HBox ligneBasse = (HBox) colonneGauche.getChildren().get(1);
            Text date = (Text) ligneBasse.getChildren().get(1);
            HBox colonneDroite = (HBox) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Label feedback = (Label) colonneDroite.getChildren().get(0);
            TextField noteText = (TextField) colonneDroite.getChildren().get(1);
            //setter des éléments
            ponderation.setText(String.format("%.0f ", eval.getPoids() * 100) + "%");
            nomEval.setText(eval.getNom());
            date.setText(eval.getDate());
            Double note = eval.getNote();
            if (note != null) {
                noteText.setText(note.toString());
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (8) : " + e.getMessage() + ",");
        }
    }
    
    private void afficherEvaluationVide() throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEvalV")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (9) : " + e.getMessage() + ",");
        }
    }
    
    private void noteChangerSae(Evaluation eval, Label feedback, TextField noteText) {
        try {
            eval.setNote(Double.parseDouble(noteText.getText()));
            Modele.sauvegarder();
            if (feedback.getStyleClass().contains("negatif")) {
                feedback.getStyleClass().remove("negatif");
            }
            feedback.getStyleClass().add("positif");
            feedback.setText("Note valide, note enregistré  ");
        } catch (IllegalArgumentException e) {
            if (feedback.getStyleClass().contains("positif")) {
                feedback.getStyleClass().remove("positif");
            }
            feedback.getStyleClass().add("negatif");
            feedback.setText("Note invalide, note pas enregistré  ");
        }
    }
    
    //RESSOURCE EDITER MODALITER
    private void modaliteCliquer(Ressource ressource) throws Exception {
        viderListePrincipal();
        System.out.println("MODALITER EDITER");
    }
    //SAE
    private void saeCliquer(Sae sae) throws Exception {
        viderListePrincipal();
        ajouterTitreSae(sae);
        ajouterElementSae(sae);
    }
    
    private void ajouterTitreSae(Sae sae) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Text titre = (Text) hbox.getChildren().get(0);
            titre.setText(sae.creerIntitule());
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (5) : " + e.getMessage());
        }
    }
    
    private void ajouterElementSae(Sae sae) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            TextField titre = (TextField) ((HBox) ((HBox) hbox.getChildren().get(1))
                                                              .getChildren().get(0))
                                                              .getChildren().get(1);
            titre.setOnAction((Event) -> noteChangerSae(sae));
            Double note = sae.getNote();
            if (note != null) {
                titre.setText(note.toString());
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (6) : " + e.getMessage());
        }
    }
    
    private void noteChangerSae(Sae sae) {
        Label feedback = (Label) ((HBox) ((HBox) ((HBox) listePrincipal.getChildren().get(1))
                                                                     .getChildren().get(1))
                                                                     .getChildren().get(0))
                                                                     .getChildren().get(0);
        TextField reponse = (TextField) ((HBox) ((HBox) ((HBox) listePrincipal.getChildren().get(1))
                                                                              .getChildren().get(1))
                                                                              .getChildren().get(0))
                                                                              .getChildren().get(1);
        try {
            sae.setNote(Double.parseDouble(reponse.getText()));
            Modele.sauvegarder();
            if (feedback.getStyleClass().contains("negatif")) {
                feedback.getStyleClass().remove("negatif");
            }
            feedback.getStyleClass().add("positif");
            feedback.setText("Note valide, note enregistré  ");
        } catch (IllegalArgumentException e) {
            if (feedback.getStyleClass().contains("positif")) {
                feedback.getStyleClass().remove("positif");
            }
            feedback.getStyleClass().add("negatif");
            feedback.setText("Note invalide, note pas enregistré  ");
        }
    }
    
    //GENERAL
    
    private void viderListePrincipal() {
        while (!listePrincipal.getChildren().isEmpty()) {
            listePrincipal.getChildren().remove(0);
        }
    }

}
