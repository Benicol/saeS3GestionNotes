/*
 * VueHomepageController.java                                             9 nov 2023
 * IUT Rodez, pas de copyright
 */
package controleur;

import modele.Competence;
import modele.Evaluation;
import modele.Modele;
import modele.Ressource;
import modele.Sae;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.layout.HBox;

/** 
 * Contrôleur de la vue vue.Homepage.fxml
 */
public class VueHomepageController {
    /** Texte donnant le numéro du semestre et parcours */
    @FXML
    private Text nbSemestreParcours;
    /** Bouton invisible contenant le nom de l'utilisateur*/
    @FXML
    private Button boutonUtilisateur;
    /** zone pour les tritre de la liste principale */
    @FXML
    private VBox zoneTitre;
    /** listePrincipal, VBox central de l'application */
    @FXML
    private VBox listePrincipale;
    /** liste des ressources à gauche */
    @FXML
    private VBox listeRessources;
    /** liste des saes à gauche */
    @FXML
    private VBox listeSaes;
    /** liste des competences à gauche */
    @FXML
    private VBox listesCompetences;
    /** bouton Sélectionner dans le menu à gauche */
    private Button selected;
    /** Utiliser pour la modification des modalitées d'une évluation */
    private ArrayList<Integer> evalTmpId;
    /** Utiliser pour la modification des modalitées d'une évluation */
    private ArrayList<Integer> evalTmpIdRemoved;
    private Label bienvenueX = null;
    private Ressource ressourceModifier = null;
    
    private boolean dansModifierModalite() {
        return ressourceModifier != null;
    }
    
    /** thread pour l'affichage de texte temporaire */
    private static HashMap<Label,Thread> currentThreads = new HashMap<>();
    
    //Zone d'initialisation de l'application
    
    /**
     * Effectue les traitement suivant dans cette ordre : 
     * - initialise le semestre et parcours
     * - Initialise selected à null
     * - Si le paramétrage est null, alors lance la vue importer
     * - Met en grand la fenêtre
     * - Met à jour l'utilisateur
     * - Affiche les compétences
     * - Affiche les ressources
     * - Affiche les saes
     * - Affiche le message de bienvenue par défaut
     * @throws Exception 
     */
    @FXML
    void initialize() throws Exception {
        // initialise le semestre et parcours
        nbSemestreParcours.setText("Semestre " + Modele.getParametrage()
                                   .getSemestre() + " - Parcours " + 
                                   Modele.getParametrage().getParcours());
        // Initialise selected à null
        selected = null;
        // Met en grand la fenêtre
        EchangeurDeVue.getPrimaryStage().setMaximized(true);
        // Met à jour l'utilisateur
        boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
        // Affiche les compétences
        setVBoxListeCompetence();
        // Affiche les ressources
        setVBoxListeRessouces();
        // Affiche les SAE
        setVBoxListeSaes();
        // Affiche le message de bienvenue par défaut
        FXMLLoader fxmlloader = new FXMLLoader(getClass()
                                      .getResource(EchangeurDeVue.getModule("MB")));
        Parent root = fxmlloader.load();
        HBox hbox = (HBox) root;
        bienvenueX = (Label) ((VBox) hbox.getChildren().get(0)).getChildren().get(0);
        bienvenueX.setText("Bienvenue " + Modele.getUtilisateur().getPseudo());
        listePrincipale.getChildren().add(hbox);

    }
    
    /* Rempli la liste des compétences dans le menu à gauche */
    private void setVBoxListeCompetence() throws Exception {
        // Récupère les compétences et les trie
        String[] keys = Modele.getCompetences().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                // Charge le module
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                                                 EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                // Configure le module
                button.setText(Modele.getCompetences().get(key).creerIntitule());
                button.setOnMouseEntered((event) -> sideNavButtonInactiveEntered(event));
                button.setOnMouseExited((event) -> sideNavButtonInactiveExited(event));
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        competenceCliquer(Modele.getCompetences().get(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                // Ajoute le module
                listesCompetences.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (1) : " + e.getMessage());
            }
        }
    }
    
    /* Rempli la liste des ressources dans le menu à gauche */
    private void setVBoxListeRessouces() throws Exception {
        // Récupère les ressources et les trie
        String[] keys = Modele.getRessources().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                // Charge le module
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                                                 EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                // Configure le module
                button.setText(Modele.getRessources().get(key).creerIntitule());
                button.setOnMouseEntered((event) -> sideNavButtonInactiveEntered(event));
                button.setOnMouseExited((event) -> sideNavButtonInactiveExited(event));
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        ressourceCliquer(Modele.getRessources().get(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
              // Ajoute le module
                listeRessources.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (2) : " + e.getMessage());
            }
        }
    }
    
    /* Rempli la liste des SAE dans le menu à gauche */
    private void setVBoxListeSaes() throws Exception {
        // Récupère les saes et les tris
        String[] keys = Modele.getSae().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                // Charge le module
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                // Configure le module
                button.setText(Modele.getSae().get(key).creerIntitule());
                button.setOnMouseEntered((event) -> sideNavButtonInactiveEntered(event));
                button.setOnMouseExited((event) -> sideNavButtonInactiveExited(event));
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        saeCliquer(Modele.getSae().get(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                // Ajoute le module
                listeSaes.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (3) : " + e.getMessage());
            }
        }
    }
    
    /* Vide la liste principale de tous ces éléments */
    private void viderZonePrincipale() {
        while (!listePrincipale.getChildren().isEmpty()) {
            listePrincipale.getChildren().remove(0);
        }
        while (!zoneTitre.getChildren().isEmpty()) {
            zoneTitre.getChildren().remove(0);
        }
    }
    private void quitterModaliter() {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("ATTENTION");
        alert.setHeaderText("Êtes-vous sûr de vouloir quitter sans sauvegarder ?");

        // Ajoutez des boutons personnalisés
        ButtonType buttonTypeOui = new ButtonType("Oui");
        ButtonType buttonTypeNon = new ButtonType("Non");

        alert.getButtonTypes().setAll(buttonTypeOui, buttonTypeNon);

        // Affichez la boîte de dialogue et attendez la réponse de l'utilisateur
        alert.showAndWait().ifPresent(response -> {
            if (response == buttonTypeOui) {
                // L'utilisateur a cliqué sur "Oui"
                ressourceModifier = null;
            }
        });
    }
    /* Change quel bouton est visuellement sélectionné dans le menu */
    private void eltMenuSelectionner(Button button) {
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (selected != button) {
                if (selected != null) {
                    selected.getStyleClass().remove("side-nav-element-active");
                    selected.getStyleClass().remove("side-nav-element-active-not-hover");
                    selected.getStyleClass().add("side-nav-element-inactive");
                    selected.getStyleClass().add("side-nav-element-inactive-not-hover");
                    selected.setOnMouseEntered((event) -> sideNavButtonInactiveEntered(event));
                    selected.setOnMouseExited((event) -> sideNavButtonInactiveExited(event));
                }
                selected = button;
                selected.getStyleClass().remove("side-nav-element-inactive");
                if (selected.getStyleClass().contains("side-nav-element-inactive-hover")) {
                    selected.getStyleClass().remove("side-nav-element-inactive-hover");
                }
                if (selected.getStyleClass().contains("side-nav-element-inactive-not-hover")) {
                    selected.getStyleClass().remove("side-nav-element-inactive-not-hover");
                }
                selected.getStyleClass().add("side-nav-element-active");
                selected.getStyleClass().add("side-nav-element-active-not-hover");
                selected.setOnMouseEntered(null);
                selected.setOnMouseExited(null);
                System.out.println(selected.getStyleClass());
            }
        }
    }
    
    // Zone Reactivitée
    
    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'primary-button' (bouton plein violet)
     * Boutons utilisant cette méthode : 
     * - Afficher mes moyennes
     * - Importer
     * - Exporter
     * - Editer les modalitées (ressource séléctionnée)
     * - Ajouter (modification des modalitées)
     * - Valider (modification des modalitées)
     */
    @FXML
    void primaryButtonEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("primary-button-not-hover");
        bouton.getStyleClass().add("primary-button-hover");
    }
    
    /**
     * Méthode appelée lors de la sortie de la souris dans un bouton de 
     * style 'primary-button' (bouton plein violet)
     * Boutons utilisant cette méthode : 
     * - Afficher mes moyennes
     * - Importer
     * - Exporter
     * - Editer les modalitées (ressource séléctionnée)
     * - Ajouter (modification des modalitées)
     * - Valider (modification des modalitées)
     */
    @FXML
    void primaryButtonExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("primary-button-hover");
        bouton.getStyleClass().add("primary-button-not-hover");
    }
    
    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - Reinitialiser
     * - Annuler (modification de modalitées)
     */
    @FXML
    void secondaryButtonEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-hover");
    }
    
    /**
     * Méthode appelé lors de la sortie de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - Reinitialiser
     * - Annuler (modification de modalitées)
     */
    @FXML
    void secondaryButtonExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    
    /**
     * Méthode appelée lors de l'entrée de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - oeuil (Competences et afficher les moyennes)
     */
    @FXML
    void secondaryButtonWhiteEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-white-hover");
    }
    
    /**
     * Méthode appelé lors de la sortie de la souris dans un bouton de 
     * style 'secondary-button' (bouton transparent avec contours violets)
     * Boutons utilisant cette méthode : 
     * - oeuil (Competences et afficher les moyennes)
     */
    @FXML
    void secondaryButtonWhiteExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-white-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    
    void sideNavButtonInactiveEntered(MouseEvent event) {
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("side-nav-element-inactive-not-hover");
        bouton.getStyleClass().add("side-nav-element-inactive-hover");
    }
    
    void sideNavButtonInactiveExited(MouseEvent event) {
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("side-nav-element-inactive-hover");
        bouton.getStyleClass().add("side-nav-element-inactive-not-hover");
    }
    
    /**
     * Méthode appelée quand le bouton "Exporter" est pressé
     * @throws Exception 
     */
    @FXML
    void exporterPresser(ActionEvent event) throws Exception {
        Ressource ressource = ressourceModifier;
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (ressource != null) {
                annulerModalite(ressource);
            }
            EchangeurDeVue.launchPopUp("ve", "Exporter");
        }
        
    }
    
    /**
     * Méthode appelée quand le bouton "Importer" est pressé
     * @throws Exception 
     */
    @FXML
    void importerPresser(ActionEvent event) throws Exception {
        Ressource ressource = ressourceModifier;
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (ressource != null) {
                annulerModalite(ressource);
            }
            EchangeurDeVue.launchPopUp("vpui", "Importer");
        }
        
    }
    
    /**
     * Méthode appelée quand le bouton "Reinitialiser" est pressé
     * @throws Exception 
     */
    @FXML
    void reinitialiserPresser(ActionEvent event) throws Exception {
        Ressource ressource = ressourceModifier;
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (ressource != null) {
                annulerModalite(ressource);
            }
            EchangeurDeVue.launchPopUp("vpur", "Réinitialisation");
            if (!Modele.isParametrageInitialise()) {
                EchangeurDeVue.echangerAvec("i", false);
            }
        }
    }
    
    /**
     * Méthode appelée quand le bouton "Reinitialiser" est pressé
     * @throws Exception 
     */
    @FXML
    void aidePresser(ActionEvent event) throws Exception {
        Ressource ressource = ressourceModifier;
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (ressource != null) {
                annulerModalite(ressource);
            }
            EchangeurDeVue.launchPopUp("vpua", "Aide");
        }
    }
    
    /**
     * Méthode appelée lorsque la souris entre sur bouton Utilisateur
     */
    @FXML
    void userEntered(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-not-hover");
        bouton.getStyleClass().add("utilisateur-hover");
    }
    
    /**
     * Methode appelée lors que la souris sort du bouton Utilisateur
     */
    @FXML
    void userExited(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-hover");
        bouton.getStyleClass().add("utilisateur-not-hover");
    }

    /**
     * Méthode appelée quand le bouton "Utilisateur" est pressé
     * @throws Exception 
     */
    @FXML
    void utilisateurPresser(ActionEvent event) throws Exception {
        Ressource ressource = ressourceModifier;
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (ressource != null) {
                annulerModalite(ressource);
            }
            EchangeurDeVue.launchPopUp("vcp", "Changement de nom d'utilisateur");
            bienvenueX.setText("Bienvenue " + Modele.getUtilisateur().getPseudo());
            boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
        }
        
    }
    
    //ZONE CALCUL DES MOYENNES
    
    /**
     * Méthode appelée lors de l'appui du bouton "afficher mes moyennes"
     */
    @FXML
    void afficherMesMoyennesPresser(ActionEvent event) throws Exception {
        if (dansModifierModalite()) {
            quitterModaliter();
        }
        if (!dansModifierModalite()) {
            if (selected != null) {
                if (selected != null) {
                    selected.getStyleClass().remove("side-nav-element-active");
                    selected.getStyleClass().remove("side-nav-element-active-not-hover");
                    selected.getStyleClass().add("side-nav-element-inactive");
                    selected.getStyleClass().add("side-nav-element-inactive-not-hover");
                    afficherMesMoyennesPresserStopNavMenuOnAction();
                    selected = null;
                }
            }
            viderZonePrincipale();
            titreMoyennes();
            competenceMoyennes();
            ressourceMoyennes();
            saeMoyennes();
            if (listePrincipale.getChildren().size() == 0) {
                try {
                    FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEvalV")));
                    Parent root = fxmlloader.load();
                    HBox hbox = (HBox) root;
                    Text texte = (Text) hbox.getChildren().get(0);
                    texte.setText("Aucune moyenne à calculer !");
                    listePrincipale.getChildren().add(hbox);
                } catch (Exception e) {
                    throw new Exception("application corrompu (9) : " + e.getMessage() + ",");
                }
            }
        }
    }
    private void afficherMesMoyennesPresserStopNavMenuOnAction() {
        selected.setOnMouseEntered((event) -> sideNavButtonInactiveEntered(event));
        selected.setOnMouseExited((event) -> sideNavButtonInactiveExited(event));
    }
    
    /*
     * Ajoute le titre de la sae
     */
    private void titreMoyennes() throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                    EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Label titre = (Label) hbox.getChildren().get(0);
            titre.setText("Affichage des moyennes calculables");
            zoneTitre.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (5) : " + e.getMessage());
        }
    }
    
    /**
     * Méthode mettant les moyennes des compétences et son titre dans la vbox 
     * principale
     */
    private void competenceMoyennes() throws Exception {
        try {
            // Charge la vue ModuleTitreSae
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                                                  EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hboxTitre = (HBox) root;
            // Lui associe le bon titre puis l'affiche
            Label titre = (Label) hboxTitre.getChildren().get(0);
            titre.setText("COMPETENCES");
            listePrincipale.getChildren().add(hboxTitre);
            // Vérifie pour chaque compétences si elles sont calculables
            String[] cles = Modele.getCompetences().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for (String cle : cles) {
                Competence competence = Modele.getCompetences().get(cle);
                if (competence.isCalculable()) {
                    // Charge la vue ModuleMoyenne
                    fxmlloader = new FXMLLoader(getClass().getResource(
                                                   EchangeurDeVue.getModule("MM")));
                    root = fxmlloader.load();
                    HBox hbox = (HBox) root;
                    // Va chercher les champs
                    VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
                    Text type = (Text) zoneEcriture.getChildren().get(1);
                    titre = (Label) zoneEcriture.getChildren().get(0);
                    HBox zoneDroite = (HBox) hbox.getChildren().get(1);
                    Button oeil = (Button) zoneDroite.getChildren().get(1);
                    HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
                    Text note = (Text) zoneNote.getChildren().get(0);
                    // Rempli les champs
                    type.setText("Competence");
                    titre.setText(competence.creerIntitule());
                    note.setText((new DecimalFormat("#.##").format(competence.calculerMoyenne()).replace(",", "."))); 
                    int boutonId = Integer.parseInt(competence.getIdentifiant().substring(3, 4)) - 1;
                    Button bouttonCompetence = (Button) listesCompetences.getChildren().get(boutonId);
                    oeil.setOnMouseEntered((event) -> secondaryButtonEntered(event));
                    oeil.setOnMouseExited((event) -> secondaryButtonExited(event));
                    oeil.setOnAction((event) -> {
                    eltMenuSelectionner(bouttonCompetence);
                    try {
                        competenceCliquer(competence);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    // Met la HBox dans la liste principale
                    listePrincipale.getChildren().add(hbox);
                }
            }
            if (listePrincipale.getChildren().get(listePrincipale.getChildren().size() -1 ) == hboxTitre) {
                listePrincipale.getChildren().remove(listePrincipale.getChildren().size() -1 );
            }
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
        
    }
    
    /**
     * Méthodes mettant les moyennes des ressources et son titre dans la 
     * vbox principale
     */
    private void ressourceMoyennes() throws Exception {
        try {
            // Charge la vue ModuleTitreSae
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hboxTitre = (HBox) root;
            Label titre = (Label) hboxTitre.getChildren().get(0);
            // Lui associe le bon titre puis l'affiche
            titre.setText("RESSOURCES");
            listePrincipale.getChildren().add(hboxTitre);
            // Verifie pour chaque ressources si ils sont calculables
            String[] cles = Modele.getRessources().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for (String cle : cles) {
                Ressource ressource = Modele.getRessources().get(cle);
                if (ressource.isCalculable()) {
                    // Charge la vue ModuleMoyenne
                    fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MM")));
                    root = fxmlloader.load();
                    HBox hbox = (HBox) root;
                    // Va chercher les champs
                    VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
                    Text type = (Text) zoneEcriture.getChildren().get(1);
                    titre = (Label) zoneEcriture.getChildren().get(0);
                    HBox zoneDroite = (HBox) hbox.getChildren().get(1);
                    Button oeuil = (Button) zoneDroite.getChildren().get(1);
                    HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
                    Text note = (Text) zoneNote.getChildren().get(0);
                    // Rempli les champs
                    type.setText("Ressource");
                    titre.setText(ressource.creerIntitule());
                    note.setText((new DecimalFormat("#.##").format(ressource.calculerMoyenne()).replace(",", "."))); 
                    int boutonId = Integer.parseInt(ressource.getIdentifiant().substring(3, 5)) - 1;
                    Button bouttonRessource = (Button) listeRessources.getChildren().get(boutonId);
                    
                    oeuil.setOnMouseEntered((event) -> secondaryButtonWhiteEntered(event));
                    oeuil.setOnMouseExited((event) -> secondaryButtonWhiteExited(event));
                    oeuil.setOnAction((event) -> {
                    eltMenuSelectionner(bouttonRessource);
                    try {
                        ressourceCliquer(ressource);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    // Met la HBox dans la liste principal
                    listePrincipale.getChildren().add(hbox);
                }
            }
            if (listePrincipale.getChildren().get(listePrincipale.getChildren().size() -1 ) == hboxTitre) {
                listePrincipale.getChildren().remove(listePrincipale.getChildren().size() -1 );
            }
        } catch (Exception e) {
            throw new Exception("application corrompu (5) : " + e.getMessage());
        }
    }
    
    /**
     * méthodes mettant les notes d'sae et son titre dans la vbox principal
     */
    private void saeMoyennes() throws Exception {
        try {
            // Charge la vue ModuleTitreSae
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hboxTitre = (HBox) root;
            // Lui associe le bon titre puis l'affiche
            Label titre = (Label) hboxTitre.getChildren().get(0);
            titre.setText("SAE");
            listePrincipale.getChildren().add(hboxTitre);
            // Vérifie pour chaque SAE si elles ont une note
            String[] cles = Modele.getSae().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for (String cle : cles) {
                Sae sae = Modele.getSae().get(cle);
                if (sae.getNote() != null) {
                    // Charge la vue ModuleMoyenne
                    fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MM")));
                    root = fxmlloader.load();
                    HBox hbox = (HBox) root;
                    // Va chercher les champs
                    VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
                    Text type = (Text) zoneEcriture.getChildren().get(1);
                    titre = (Label) zoneEcriture.getChildren().get(0);
                    HBox zoneDroite = (HBox) hbox.getChildren().get(1);
                    Button oeuil = (Button) zoneDroite.getChildren().get(1);
                    HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
                    Text note = (Text) zoneNote.getChildren().get(0);
                    // Rempli les champs
                    type.setText("SAE");
                    titre.setText(sae.creerIntitule());
                    note.setText((new DecimalFormat("#.##").format(sae.getNote())).replace(",", ".")); 
                    Button bouton = null;
                    for (Node node : listeSaes.getChildren()) {
                        bouton = (Button) node;
                        if (sae.creerIntitule().equals(bouton.getText())) {
                            break;
                        }
                    }
                    Button boutonCherche = bouton;
                    oeuil.setOnMouseEntered((event) -> secondaryButtonWhiteEntered(event));
                    oeuil.setOnMouseExited((event) -> secondaryButtonWhiteExited(event));
                    oeuil.setOnAction((event) -> {
                    eltMenuSelectionner(boutonCherche);
                    try {
                        saeCliquer(sae);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    // Met la HBox dans la liste principale
                    listePrincipale.getChildren().add(hbox);
                }
            }
            if (listePrincipale.getChildren().get(listePrincipale.getChildren().size() -1 ) == hboxTitre) {
                listePrincipale.getChildren().remove(listePrincipale.getChildren().size() -1 );
            }
        } catch (Exception e) {
            throw new Exception("application corrompu (17) : " + e.getMessage());
        }
    }
    
    // COMPETENCES
    
    /*
     * S'active quand un bouton compétence est pressé dans le menu
     */
    private void competenceCliquer(Competence competence) throws Exception {
        if (!dansModifierModalite()) {
            // Vide la vue principale
            viderZonePrincipale();
            // Ajoute le titre de la compétence
            ajouterTitreComeptence(competence);
            // Pour chaque ressource associé, les affiche
            String[] cles = Modele.getRessources().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for(String cle : cles) {
                if (competence.getListeRessources().keySet().contains(Modele.getRessources().get(cle))) {
                    Ressource ressource = Modele.getRessources().get(cle);
                    ajouterRessourceComeptence(ressource, competence.getListeRessources().get(ressource));
                }
            }
            // Pour chaque ressource associée, les affiche
            cles = Modele.getSae().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for(String cle : cles) {
                if (competence.getListeSaes().keySet().contains(Modele.getSae().get(cle))) {
                    Sae sae = Modele.getSae().get(cle);
                    ajouterSaeComeptence(sae, competence.getListeSaes().get(sae));
                }
            }
        }
    }
    
    /*
     * Ajoute le titre de la compétence
     */
    private void ajouterTitreComeptence(Competence competence) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTC")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Label titre = ((Label) ((HBox) hbox.getChildren().get(0)).getChildren().get(0));
            Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
            titre.setText(competence.creerIntitule());
            if (competence.isCalculable()) {
                note.setText((new DecimalFormat("#.##").format(competence.calculerMoyenne()).replace(",", "."))); 
                if (competence.calculerMoyenne() > 10) {
                    note.setFill(Color.LIMEGREEN);
                } else if (competence.calculerMoyenne() > 8){
                    note.setFill(Color.ORANGE);
                } else {
                    note.setFill(Color.ORANGERED);
                }
            } else {
                note.setText("Moyenne incalculable");
                diviseur.setText("");
            }
            zoneTitre.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    /*
     * Ajoute les ressources dans la compéténce
     */
    private void ajouterRessourceComeptence(Ressource ressource, Double poids) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MR")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
            Text type = (Text) zoneEcriture.getChildren().get(1);
            HBox ligneHaut = (HBox) zoneEcriture.getChildren().get(0);
            Label titre = (Label) ligneHaut.getChildren().get(1);
            Text ponderation = (Text) ((HBox) ligneHaut.getChildren().get(0)).getChildren().get(0);
            HBox zoneDroite = (HBox) hbox.getChildren().get(1);
            Button oeuil = (Button) zoneDroite.getChildren().get(1);
            HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
            Text note = (Text) zoneNote.getChildren().get(0);
            Text diviseur = (Text) zoneNote.getChildren().get(1);
            ponderation.setText(String.format("%.0f ", poids * 100) + "%");
            type.setText("Ressource");
            titre.setText(ressource.creerIntitule());
            if (ressource.isCalculable()) {
                note.setText((new DecimalFormat("#.##").format(ressource.calculerMoyenne()).replace(",", "."))); 
            } else {
                note.setText("Moyenne incalculable");
                diviseur.setText("");
            }
            int boutonId = Integer.parseInt(ressource.getIdentifiant().substring(3, 5)) - 1;
            Button bouttonRessource = (Button) listeRessources.getChildren().get(boutonId);
            listePrincipale.getChildren().add(hbox);
            oeuil.setOnMouseEntered((event) -> secondaryButtonWhiteEntered(event));
            oeuil.setOnMouseExited((event) -> secondaryButtonWhiteExited(event));
            oeuil.setOnAction((event) -> {
            eltMenuSelectionner(bouttonRessource);
            try {
                ressourceCliquer(ressource);
            } catch (Exception e) {
                e.printStackTrace();
            }
            });
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    /*
     * Ajoute les SAE dans la compétence
     */
    private void ajouterSaeComeptence(Sae sae, Double poids) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MR")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
            Text type = (Text) zoneEcriture.getChildren().get(1);
            HBox ligneHaut = (HBox) zoneEcriture.getChildren().get(0);
            Label titre = (Label) ligneHaut.getChildren().get(1);
            Text ponderation = (Text) ((HBox) ligneHaut.getChildren().get(0)).getChildren().get(0);
            HBox zoneDroite = (HBox) hbox.getChildren().get(1);
            Button oeuil = (Button) zoneDroite.getChildren().get(1);
            HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
            Text note = (Text) zoneNote.getChildren().get(0);
            Text diviseur = (Text) zoneNote.getChildren().get(1);
            ponderation.setText(String.format("%.0f ", poids * 100) + "%");
            type.setText("SAE");
            titre.setText(sae.creerIntitule());
            if (sae.getNote() != null) {
                note.setText((new DecimalFormat("#.##").format(sae.getNote()).replace(",", "."))); 
            } else {
                note.setText("Note indéfinie");
                diviseur.setText("");
            }
            Button bouton = null;
            for (Node node : listeSaes.getChildren()) {
                bouton = (Button) node;
                if (sae.creerIntitule().equals(bouton.getText())) {
                    break;
                }
            }
            Button boutonCherche = bouton;
            
            listePrincipale.getChildren().add(hbox);
            oeuil.setOnMouseEntered((event) -> secondaryButtonWhiteEntered(event));
            oeuil.setOnMouseExited((event) -> secondaryButtonWhiteExited(event));
            oeuil.setOnAction((event) -> {
            eltMenuSelectionner(boutonCherche);
            try {
                saeCliquer(sae);
            } catch (Exception e) {                e.printStackTrace();
            }
            });
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    
    // RESSOURCE NORMALE
    
    /*
     * Est appelée quand une ressource est sélectionnée
     */
    private void ressourceCliquer(Ressource ressource) throws Exception {
        if (!dansModifierModalite()) {
            // Vide la vbox principale
            viderZonePrincipale();
            // Ajoute le titre de la ressource
            ajouterTitreRessource(ressource);
            /*
             * Pour chaque évaluation associée, ajoute son cadre,
             * et s'il n'y a pas d"évaluation, ajoute un cadre informatif 
             */
            ArrayList<Evaluation> listeEvaluation = ressource.getListeEvaluations();
            if (listeEvaluation.isEmpty()){
                afficherEvaluationVide();
            } else {
                for (Evaluation eval : listeEvaluation) {
                    ajouterEvaluation(eval, ressource);
                }
            }
        }
    }
    /*
     * Ajoute le titre de la ressource
     */
    private void ajouterTitreRessource(Ressource ressource) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTR")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Label titre = (Label)((HBox) ((HBox) hbox.getChildren().get(0))
                                                   .getChildren().get(0))
                                                   .getChildren().get(0);
            Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
            Button bouton = (Button)((HBox) hbox.getChildren().get(0))
                                                .getChildren().get(1);
            bouton.setOnMouseEntered((event) -> primaryButtonEntered(event));
            bouton.setOnMouseExited((event) -> primaryButtonExited(event));
            bouton.setOnAction((event) -> {
                try {
                    modaliteCliquer(ressource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            titre.setText(ressource.creerIntitule());
            if (ressource.isCalculable()) {
                note.setText((new DecimalFormat("#.##").format(ressource.calculerMoyenne()).replace(",", ".")));  
                if (ressource.calculerMoyenne() > 10) {
                    note.setFill(Color.LIMEGREEN);
                } else if (ressource.calculerMoyenne() > 8){
                    note.setFill(Color.ORANGE);
                } else {
                    note.setFill(Color.ORANGERED);
                }
            } else {
                note.setText("Moyenne incalculable");
                diviseur.setText("");
            }
            zoneTitre.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (7) : " + e.getMessage());
        }
    }
    
    /*
     * Ajoute une évaluation
     */
    private void ajouterEvaluation(Evaluation eval, Ressource ressource) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEval")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            // Récupération de tous les éléments
            VBox colonneGauche = (VBox) hbox.getChildren().get(0);
            HBox ligneHaute = (HBox) colonneGauche.getChildren().get(0);
            Text ponderation = (Text) ((HBox) ligneHaute.getChildren().get(0)).getChildren().get(0);
            Label nomEval = (Label) ligneHaute.getChildren().get(1);
            HBox ligneBasse = (HBox) colonneGauche.getChildren().get(1);
            Text date = (Text) ligneBasse.getChildren().get(1);
            HBox colonneDroite = (HBox) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Label feedback = (Label) colonneDroite.getChildren().get(0);
            TextField noteText = (TextField) colonneDroite.getChildren().get(1);
            // Setter des éléments
            ponderation.setText(String.format("%.0f ", eval.getPoids() * 100) + "%");
            nomEval.setText(eval.getNom());
            date.setText(eval.getDate());
            Double note = eval.getNote();
            if (note != null) {
                noteText.setText(String.format("%.2f", note).replace(",","."));
            }
            noteText.setOnAction((Event) -> noteChangerEvaluation(eval, feedback, noteText, ressource));
            noteText.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue) {
                    noteChangerEvaluation(eval, feedback, noteText, ressource);
                }
            });
            listePrincipale.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (8) : " + e.getMessage() + ",");
        }
    }
    /*
     * Affichage dans le cas où il n'y a aucune évaluation
     */
    private void afficherEvaluationVide() throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEvalV")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            listePrincipale.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (9) : " + e.getMessage() + ",");
        }
    }
    /**
     * Méthode appelé lors du changement d'une note
     */
    private void noteChangerEvaluation(Evaluation eval, Label feedback, TextField noteText, Ressource ressource) {
        try {
            System.out.println(feedback.getStyleClass());
            if (feedback.getStyleClass().contains("negatif")) {
                feedback.getStyleClass().remove("negatif");
            }
            if (feedback.getStyleClass().contains("positif")) {
                feedback.getStyleClass().remove("positif");
            }
            if (eval.getNote() == null || noteText.getText().equals("") || Double.parseDouble(noteText.getText().replace(",", ".")) != eval.getNote()) {
                eval.setNote(noteText.getText().equals("") ? null : Double.parseDouble(noteText.getText().replace(",", ".")));
                Modele.sauvegarder();
                feedback.getStyleClass().add("positif");
                feedback.setText("Note sauvegardée");
                threadFeedbackBack(feedback);
            } else if (!feedback.getText().equals("") && Double.parseDouble(noteText.getText()) == eval.getNote()) {
                feedback.getStyleClass().add("positif");
                feedback.setText("Note sauvegardée");
                threadFeedbackBack(feedback);
            }
        } catch (IllegalArgumentException e) {
            // Vérifiez s'il existe déjà un thread en cours d'exécution
            if (currentThreads.containsKey(feedback) && currentThreads.get(feedback).isAlive()) {
                // Interrompez le thread en cours d'exécution
                currentThreads.get(feedback).interrupt();
            }
            feedback.getStyleClass().add("negatif");
            feedback.setText("Note invalide");
        }
        
        updateMoyenneEvaluation(ressource);
    }
    /*
     * Met à jour la moyenne suite au changement d'une note
     */
    private void updateMoyenneEvaluation(Ressource ressource) {
        HBox hbox = (HBox) zoneTitre.getChildren().get(0);
        Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
        Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
        if (ressource.isCalculable()) {
            note.setText((new DecimalFormat("#.##").format(ressource.calculerMoyenne()).replace(",", "."))); 
            diviseur.setText("/20");
            if (ressource.calculerMoyenne() > 10) {
                note.setFill(Color.LIMEGREEN);
            } else if (ressource.calculerMoyenne() > 8){
                note.setFill(Color.ORANGE);
            } else {
                note.setFill(Color.ORANGERED);
            }
        } else {
            note.setText("Moyenne incalculable");
            diviseur.setText("");
            note.setFill(Color.valueOf("#22ffd7"));
        }
    }
    
    // RESSOURCE EDITER MODALITES
    
    /*
     * Appelée lors du clic du bouton modifier une ressource
     */
    private void modaliteCliquer(Ressource ressource) throws Exception {
        /*
         *  Set ces tableaux qui permettrons de suivre quelles évluations 
         *  ont été supprimées etc.
         */
        ressourceModifier = ressource;
        evalTmpId = new ArrayList<>();
        evalTmpIdRemoved = new ArrayList<>();
        // Vide la vue principale
        viderZonePrincipale();
        // Insere le titre
        modaliteTitre(ressource);
        // Ajoute les évluations déjà existantes
        if (ressource.getListeEvaluations().size() > 0) {
            int i = 0;
            for (Evaluation eval : ressource.getListeEvaluations()) {
                modaliteElement(eval, i);
                i++;
            }
            modaliteBouton();
        } else {
            modaliteBouton();
            ajouter();
        }
        // Ajoute le bouton ajouter
        
    }
    /*
     * Ajoute le titre
     */
    private void modaliteTitre(Ressource ressource) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTRE")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            HBox hboxInterne = (HBox) hbox.getChildren().get(0);
            Label titre = (Label) hboxInterne.getChildren().get(0);
            Button boutonValider = (Button) hboxInterne.getChildren().get(1);
            Button boutonAnnuler = (Button) hboxInterne.getChildren().get(2);
            titre.setText(ressource.creerIntitule());
            boutonValider.setOnMouseEntered((event) -> primaryButtonEntered(event));
            boutonValider.setOnMouseExited((event) -> primaryButtonExited(event));
            boutonAnnuler.setOnMouseEntered((event) -> secondaryButtonEntered(event));
            boutonAnnuler.setOnMouseExited((event) -> secondaryButtonExited(event));
            boutonValider.setOnAction((event) -> {
                try {
                    sauvegarderPresser(ressource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            boutonAnnuler.setOnAction((event) -> {
                try {
                    annulerModalite(ressource);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            zoneTitre.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (10) : " + e.getMessage());
        }
    }
    
    /*
     * Ajoute un encadré évaluation
     */
    private void modaliteElement(Evaluation evaluation, int index) throws Exception {
        try {
            String desc;
            String poids;
            String date;
            if (evaluation == null) {
                desc = "";
                poids = "";
                date = "";
            } else {
                desc = evaluation.getNom();
                poids = String.format("%.0f ", evaluation.getPoids() * 100) + "%";
                date = evaluation.getDate();
            }
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEvalE")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            VBox colonne = (VBox) hbox.getChildren().get(0);
            TextField tDesc = (TextField) ((HBox) colonne.getChildren().get(0)).getChildren().get(1);
            TextField tPoids = (TextField) ((HBox) colonne.getChildren().get(1)).getChildren().get(1);
            TextField tDate = (TextField) ((HBox) colonne.getChildren().get(2)).getChildren().get(1);
            Button boutonSupprimer = (Button) ((HBox) ((HBox) hbox.getChildren().get(1))
                                                                  .getChildren().get(0))
                                                                  .getChildren().get(0);
            tDesc.setText(desc);
            tPoids.setText(poids);
            tDate.setText(date);
            boutonSupprimer.setOnMouseEntered((event) -> primaryButtonEntered(event));
            boutonSupprimer.setOnMouseExited((event) -> primaryButtonExited(event));
            if (index > -1) {
                boutonSupprimer.setOnAction((event) -> retirer(hbox, index));
            } else {
                boutonSupprimer.setOnAction((event) -> retirer(hbox));
            }
            evalTmpId.add(index);
            listePrincipale.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (11) : " + e.getMessage());
        }
    }
    
    /*
     * Ajoute le bouton ajouter
     */
    private void modaliteBouton() throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MREA")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Button bouton = (Button) hbox.getChildren().get(0);
            bouton.setOnMouseEntered((event) -> primaryButtonEntered(event));
            bouton.setOnMouseExited((event) -> primaryButtonExited(event));
            bouton.setOnAction((event) -> {
                try {
                    ajouter();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            listePrincipale.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (11) : " + e.getMessage());
        }
    }
    
    /*
     * Retire une évaluation
     */
    private void retirer(HBox hbox, int index) {
        int value = evalTmpId.remove(evalTmpId.indexOf(index));
        evalTmpIdRemoved.add(value);
        listePrincipale.getChildren().remove(hbox);
    }
    
    /*
     * Retire une évaluation
     */
    private void retirer(HBox hbox) {
        evalTmpId.remove(evalTmpId.size() - 1);
        listePrincipale.getChildren().remove(hbox);
    }
    
    /*
     * Ajoute une évaluation
     */
    private void ajouter() throws Exception {
        try {
            HBox boutonAjouter = (HBox) listePrincipale.getChildren()
                                  .remove(listePrincipale.getChildren().size() - 1);
            modaliteElement(null, -1);
            listePrincipale.getChildren().add(boutonAjouter);
        } catch (Exception e) {
            throw new Exception("application corrompu (12) : " + e.getMessage());
        }
    }
    
    /*
     * Lancée quand le bouton annuler est pressé
     */
    private void annulerModalite(Ressource ressource) throws Exception {
        evalTmpId = null;
        evalTmpIdRemoved = null;
        ressourceModifier = null;
        ressourceCliquer(ressource);
    }
    
    /*
     * Lancée quand le bouton sauvegarder est pressé
     */
    private void sauvegarderPresser(Ressource ressource) throws Exception {
        if (verifierModalite()) {
            Collections.sort(evalTmpIdRemoved, Collections.reverseOrder());
            for (int elt : evalTmpIdRemoved) {
                for (int i = 0; i < evalTmpId.size(); i++) {
                    if (evalTmpId.get(i) > elt) {
                        evalTmpId.set(i, evalTmpId.get(i) - 1);
                    }
                }
            }
            for (int index : evalTmpIdRemoved) {
                ressource.supprimerEvaluation(index);
            }
            int i = 0;
            for (int index : evalTmpId) {
                HBox racine = (HBox) listePrincipale.getChildren().get(i);
                VBox colonne = (VBox) racine.getChildren().get(0);
                TextField tDesc = (TextField) ((HBox) colonne.getChildren()
                        .get(0)).getChildren().get(1);
                TextField tPoids = (TextField) ((HBox) colonne.getChildren().get(1))
                        .getChildren().get(1);
                TextField tDate = (TextField) ((HBox) colonne.getChildren().get(2))
                        .getChildren().get(1);
                String description = tDesc.getText();
                Double ponderation = (Double.parseDouble(tPoids.getText()
                        .replaceAll("[^0-9.,]", "")) / 100);
                String date = tDate.getText();
                if (index > -1) {
                    Evaluation eval = ressource.getListeEvaluations().get(index);
                    eval.setNom(description);
                    eval.setPoids(ponderation);
                    eval.setDate(date);
                } else {
                    Evaluation eval = new Evaluation(description, ponderation, date);
                    ressource.ajouterEvaluation(eval);
                }
                i++;
                Modele.sauvegarder();
                
            }
            annulerModalite(ressource);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("INFORMATIONS INVALIDES");
            alert.setHeaderText("Assurez-vous que toutes les descriptions soient "
                    + "correctement renseignées et que la somme des poids soit "
                    + "égale à 100!");
            alert.showAndWait();
        }
    }
    
    /*
     * Vérifie si les modalités sont correctes 
     * (appelée par la méthode sauvegarder)
     */
    private boolean verifierModalite() throws Exception {
        int poids = 0;
        boolean ok = true;
        for (int i = 0; i < listePrincipale.getChildren().size() - 1 && ok; i++) {
            HBox racine = (HBox) listePrincipale.getChildren().get(i);
            VBox colonne = (VBox) racine.getChildren().get(0);
            TextField tDesc = (TextField) ((HBox) colonne.getChildren().get(0))
                    .getChildren().get(1);
            TextField tPoids = (TextField) ((HBox) colonne.getChildren().get(1))
                    .getChildren().get(1);
            if (tDesc.getText().equals("")) {
                ok = false;
            }
            String poidsTexte = tPoids.getText().replaceAll("[^0-9.,]", "");
            if (poidsTexte.equals("")) {
                ok = false;
            } else {
                int poidsTmp;
                try {
                    poidsTmp = Integer.parseInt(poidsTexte);
                    if (poidsTmp == 0) {
                        ok = false;
                    }
                } catch (NumberFormatException e) {
                    poidsTmp = 0;
                }
                poids += poidsTmp;
            }
        }
        if (poids != 100 && !evalTmpId.isEmpty()) {
            ok = false;
        }
        return ok;
    }
    
    //SAE
    
    /*
     * Appelée lors du clic sur un boutopn sae dans le menu
     */
    private void saeCliquer(Sae sae) throws Exception {
        if (!dansModifierModalite()) {
            // Vide la vue principale
            viderZonePrincipale();
            // Ajoute le titre
            ajouterTitreSae(sae);
            // Ajoute la note de la sae
            ajouterElementSae(sae);
        }
    }
    /*
     * Ajoute le titre de la sae
     */
    private void ajouterTitreSae(Sae sae) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                    EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Label titre = (Label) hbox.getChildren().get(0);
            titre.setText(sae.creerIntitule());
            zoneTitre.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (5) : " + e.getMessage());
        }
    }
    
    /*
     * Ajoute la note de la sae
     */
    private void ajouterElementSae(Sae sae) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(
                    EchangeurDeVue.getModule("MS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            TextField titre = (TextField) ((HBox) ((HBox) hbox.getChildren().get(1))
                                                              .getChildren().get(0))
                                                              .getChildren().get(1);
            titre.setOnAction((Event) -> noteChangerSae(sae));
            titre.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue) {
                    noteChangerSae(sae);
                }
            });
            Double note = sae.getNote();
            if (note != null) {
                titre.setText(String.format("%.2f", note).replace(",","."));
            }
            listePrincipale.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (6) : " + e.getMessage());
        }
    }
    /*
     * Méthode appelée dans le cas du changement d'une note
     */
    private void noteChangerSae(Sae sae) {
        Label feedback = (Label) ((HBox) ((HBox) ((HBox) listePrincipale
                                                              .getChildren().get(0))
                                                              .getChildren().get(1))
                                                              .getChildren().get(0))
                                                              .getChildren().get(0);
        TextField reponse = (TextField) ((HBox) ((HBox) ((HBox) listePrincipale
                                                              .getChildren().get(0))
                                                              .getChildren().get(1))
                                                              .getChildren().get(0))
                                                              .getChildren().get(1);
        try {
            if (sae.getNote() == null || reponse.getText().equals("") ||Double.parseDouble(reponse.getText().replace(",", ".")) != sae.getNote()) {
                System.out.println("test");
                sae.setNote(reponse.getText().equals("") ? null : Double.parseDouble(reponse.getText().replace(",", ".")));
                Modele.sauvegarder();
                if (feedback.getStyleClass().contains("negatif")) {
                    feedback.getStyleClass().remove("negatif");
                }
                feedback.getStyleClass().add("positif");
                feedback.setText("Note sauvegardée");
                threadFeedbackBack(feedback);
            } else if (!feedback.getText().equals("") && Double.parseDouble(reponse.getText()) == sae.getNote()) {
                if (feedback.getStyleClass().contains("negatif")) {
                    feedback.getStyleClass().remove("negatif");
                }
                feedback.getStyleClass().add("positif");
                feedback.setText("Note sauvegardée");
                threadFeedbackBack(feedback);
            }
        } catch (IllegalArgumentException e) {
            // Vérifiez s'il existe déjà un thread en cours d'exécution
            if (currentThreads.containsKey(feedback) && currentThreads.get(feedback).isAlive()) {
                // Interrompez le thread en cours d'exécution
                currentThreads.get(feedback).interrupt();
            }
            if (feedback.getStyleClass().contains("positif")) {
                feedback.getStyleClass().remove("positif");
            }
            feedback.getStyleClass().add("negatif");
            feedback.setText("Note invalide");
        }
    }

    private static void threadFeedbackBack(Label feedback) {
        if (currentThreads.containsKey(feedback)) {
            currentThreads.get(feedback).interrupt();
        }
        // Créez une tâche (Task) pour effectuer le travail en arrière-plan
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                // Simulez un travail long (2 secondes dans cet exemple)
                Thread.sleep(2000);
                // Vérifiez si la tâche a été interrompue (si un autre thread a été lancé)
                if (isCancelled()) {
                    return null;  // Sortir de la tâche si elle a été interrompue
                }
                
                // Mettez à jour l'interface utilisateur dans le thread de l'interface utilisateur
                javafx.application.Platform.runLater(() -> {
                    feedback.setText("");
                });
                currentThreads.remove(feedback);
                return null;
            }
        };
        // Démarrez la tâche dans un nouveau thread
            currentThreads.put(feedback, new Thread(task));
            currentThreads.get(feedback).start();
    }
}