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
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
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
    /** Utiliser pour la modification des modalitées d'une évluation */
    private ArrayList<Integer> evalTmpId;
    /** Utiliser pour la modification des modalitées d'une évluation */
    private ArrayList<Integer> evalTmpIdRemoved;
    
    //Zone d'initialisation de l'applciation
    
    /**
     * Effectue les traitement suivant dans cette ordre : 
     * - Initialise selected à null
     * - Si le parametrage = null, alors lance la vue importer
     * - Met en grand la fenetre
     * - Met à jour l'utilisateur
     * - Affiche les competences
     * - Affiche les ressources
     * - Affiche les saes
     * - Affiche le message de bienvenue par défaut
     * @throws Exception 
     * 
     */
    @FXML
    void initialize() throws Exception {
        // Initialise selected à null
        selected = null;
        // Met en grand la fenetre
        EchangeurDeVue.getPrimaryStage().setMaximized(true);
        // Met à jour l'utilisateur
        boutonUtilisateur.setText(Modele.getUtilisateur().getPseudo());
        // Affiche les competences
        setVBoxListeCompetence();
        // Affiche les ressources
        setVBoxListeRessouces();
        // Affiche les saes
        setVBoxListeSaes();
        // Affiche le message de bienvenue par défaut
        FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MB")));
        Parent root = fxmlloader.load();
        HBox hbox = (HBox) root;
        Label texte = (Label) ((VBox) hbox.getChildren().get(0)).getChildren().get(0);
        texte.setText("Bienvenue " + Modele.getUtilisateur().getPseudo());
        listePrincipal.getChildren().add(hbox);
        System.out.println("tentative");

    }
    
    /* remplie la liste des compétences dans le menu à gauche */
    private void setVBoxListeCompetence() throws Exception {
        // Récupère les compétences et les tris
        String[] keys = Modele.getCompetences().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                //Charge le module
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                //Configure le module
                button.setText(Modele.getCompetences().get(key).creerIntitule());
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        competenceCliquer(Modele.getCompetences().get(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                //Ajoute le module
                listesCompetences.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (1) : " + e.getMessage());
            }
        }
    }
    
    /* remplie la liste des ressources dans le menu à gauche */
    private void setVBoxListeRessouces() throws Exception {
        // Récupère les ressources et les tris
        String[] keys = Modele.getRessources().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                //Charge le module
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                //Configure le module
                button.setText(Modele.getRessources().get(key).creerIntitule());
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        ressourceCliquer(Modele.getRessources().get(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
              //Ajoute le module
                listeRessources.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (2) : " + e.getMessage());
            }
        }
    }
    
    /* remplie la liste des saes dans le menu à gauche */
    private void setVBoxListeSaes() throws Exception {
     // Récupère les saes et les tris
        String[] keys = Modele.getSae().keySet().toArray(new String[0]);
        Arrays.sort(keys);
        for (String key : keys) {
            try {
                //Charge le module
                FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MSMB")));
                Parent root = fxmlloader.load();
                Button button = (Button) root;
                //Configure le module
                button.setText(Modele.getSae().get(key).creerIntitule());
                button.setOnAction((event) -> {
                    try {
                        eltMenuSelectionner(button);
                        saeCliquer(Modele.getSae().get(key));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
                //Ajoute le module
                listeSaes.getChildren().add(button);
            } catch (Exception e) {
                throw new Exception("application corrompu (3) : " + e.getMessage());
            }
        }
    }
    
    /* Vide la liste principal de tous ces éléments */
    private void viderListePrincipal() {
        while (!listePrincipal.getChildren().isEmpty()) {
            listePrincipal.getChildren().remove(0);
        }
    }
    
    /* Change quel bouton est visuelment séléctionner dans le menu */
    private void eltMenuSelectionner(Button button) {
        if (selected != null) {
            selected.getStyleClass().remove("side-nav-element-active");
            selected.getStyleClass().add("side-nav-element-inactive");
        }
        selected = button;
        selected.getStyleClass().remove("side-nav-element-inactive");
        selected.getStyleClass().add("side-nav-element-active");
    }
    
    //Zone Reactiviter
    
    /**
     * Méthode appelé lors de l'entree de la souris dans un bouton plein (violet)
     * Boutons utilisant cette méthode : 
     * - Afficher mes moyennes
     * - Importer
     * - Exporter
     * - Editer les modalitées (ressource séléctionnée)
     * - Ajouter (modification des modalitées)
     * - valider (modification des modalitées)
     */
    @FXML
    void boutonPleinEntree(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("primary-button-not-hover");
        bouton.getStyleClass().add("primary-button-hover");
    }
    
    /**
     * Méthode appelé lors de la sortie de la souris dans un bouton plein (violet)
     * Boutons utilisant cette méthode : 
     * - Afficher mes moyennes
     * - Importer
     * - Exporter
     * - Editer les modalitées (ressource séléctionnée)
     * - Ajouter (modification des modalitées)
     * - valider (modification des modalitées)
     */
    @FXML
    void boutonPleinSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("primary-button-hover");
        bouton.getStyleClass().add("primary-button-not-hover");
    }
    
    /**
     * Méthode appelé lors de l'entrée de la souris dans un bouton vide (blanc)
     * Boutons utilisant cette méthode : 
     * - Reinitialiser
     * - oeuil (Competences et afficher les moyennes)
     * - Annuler (modification de modalitées)
     */
    @FXML
    void boutonVideEntree(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour assombrir le bouton.
        bouton.getStyleClass().remove("secondary-button-not-hover");
        bouton.getStyleClass().add("secondary-button-hover");
    }
    
    /**
     * Méthode appelé lors de la sortie de la souris dans un bouton vide (blanc)
     * Boutons utilisant cette méthode : 
     * - Reinitialiser
     * - oeuil (Competences et afficher les moyennes)
     * - Annuler (modification de modalitées)
     */
    @FXML
    void boutonVideSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("secondary-button-hover");
        bouton.getStyleClass().add("secondary-button-not-hover");
    }
    
    /**
     * Methode appelée quand le bouton "Exporter" est appuyée
     */
    @FXML
    void exporterPresser(ActionEvent event) {
        EchangeurDeVue.echangerAvec("i", false);
        System.out.println("exporter presser");
    }
    
    /**
     * Methode appelée quand le bouton "Importer" est appuyée
     */
    @FXML
    void importerPresser(ActionEvent event) {
        System.out.println("importer presser");
    }
    
    /**
     * Methode appelée quand le bouton "Reinitialiser" est appuyée
     */
    @FXML
    void reinitialiserPresser(ActionEvent event) {
        EchangeurDeVue.launchPopUp("vr", "Réinitialisation");
        if (!Modele.isParametrageInitialise()) {
            EchangeurDeVue.echangerAvec("i", false);
        }
    }
    
    /**
     * Methode appelée lors que la souris rentre sur bouton Utilisateur
     */
    @FXML
    void utilisateurEntree(MouseEvent event) {
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
    void utilisateurSortie(MouseEvent event) {
        // On va chercher le bouton précis que la souris a survolé
        Button bouton = (Button) event.getSource();
        // On change de classe dans le css pour rendre son style originel au bouton.
        bouton.getStyleClass().remove("utilisateur-hover");
        bouton.getStyleClass().add("utilisateur-not-hover");
    }

    /**
     * Methode appelée quand le bouton "Utilisateur" est appuyée
     */
    @FXML
    void utilisateurPresser(ActionEvent event) {
        System.out.println("utilisateur presser");
    }
    
    //ZONE CALCUL MOYENNES
    
    /**
     * Methode appelée lors de l'appuit du bouton "afficher mes moyennes"
     */
    @FXML
    void afficherMesMoyennesPresser(ActionEvent event) throws Exception {
        viderListePrincipal();
        competenceMoyennes();
        ressourceMoyennes();
        saeMoyennes();
    }
    
    /**
     * méthodes mettant les moyennes des comeptenceset son titre dans la vbox principal
     */
    private void competenceMoyennes() throws Exception {
        try {
            // charge la vue ModuleTitreSae
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            //Lui associe le bon titre puis l'affiche
            Text titre = (Text) hbox.getChildren().get(0);
            titre.setText("COMPETENCES");
            listePrincipal.getChildren().add(hbox);
            //verifie pour chaque compétences si ils sont calculables
            String[] cles = Modele.getCompetences().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for (String cle : cles) {
                Competence competence = Modele.getCompetences().get(cle);
                if (competence.isCalculable()) {
                    //charge la vue ModuleMoyenne
                    fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MM")));
                    root = fxmlloader.load();
                    hbox = (HBox) root;
                    //va chercher les champs
                    VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
                    Text type = (Text) zoneEcriture.getChildren().get(1);
                    titre = (Text) zoneEcriture.getChildren().get(0);
                    HBox zoneDroite = (HBox) hbox.getChildren().get(1);
                    Button oeuil = (Button) zoneDroite.getChildren().get(1);
                    HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
                    Text note = (Text) zoneNote.getChildren().get(0);
                    //remplis les champs
                    type.setText("Competence");
                    titre.setText(competence.creerIntitule());
                    note.setText(String.format("%.2f", competence.calculerMoyenne())); 
                    int boutonId = Integer.parseInt(competence.getIdentifiant().substring(3, 4)) - 1;
                    Button bouttonCompetence = (Button) listesCompetences.getChildren().get(boutonId);
                    oeuil.setOnMouseEntered((event) -> boutonVideEntree(event));
                    oeuil.setOnMouseExited((event) -> boutonVideSortie(event));
                    oeuil.setOnAction((event) -> {
                    eltMenuSelectionner(bouttonCompetence);
                    try {
                        competenceCliquer(competence);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    //met la HBox dans la liste principal
                    listePrincipal.getChildren().add(hbox);
                }
            } 
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
        
    }
    
    /**
     * méthodes mettant les moyennes des ressources et son titre dans la vbox principal
     */
    private void ressourceMoyennes() throws Exception {
        try {
            // charge la vue ModuleTitreSae
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Text titre = (Text) hbox.getChildren().get(0);
            //Lui associe le bon titre puis l'affiche
            titre.setText("RESSOURCES");
            listePrincipal.getChildren().add(hbox);
            //verifie pour chaque ressources si ils sont calculables
            String[] cles = Modele.getRessources().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for (String cle : cles) {
                Ressource ressource = Modele.getRessources().get(cle);
                if (ressource.isCalculable()) {
                    //charge la vue ModuleMoyenne
                    fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MM")));
                    root = fxmlloader.load();
                    hbox = (HBox) root;
                    //va chercher les champs
                    VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
                    Text type = (Text) zoneEcriture.getChildren().get(1);
                    titre = (Text) zoneEcriture.getChildren().get(0);
                    HBox zoneDroite = (HBox) hbox.getChildren().get(1);
                    Button oeuil = (Button) zoneDroite.getChildren().get(1);
                    HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
                    Text note = (Text) zoneNote.getChildren().get(0);
                    //remplis les champs
                    type.setText("Ressource");
                    titre.setText(ressource.creerIntitule());
                    note.setText(ressource.calculerMoyenne().toString()); 
                    int boutonId = Integer.parseInt(ressource.getIdentifiant().substring(3, 5)) - 1;
                    Button bouttonRessource = (Button) listeRessources.getChildren().get(boutonId);
                    
                    oeuil.setOnMouseEntered((event) -> boutonVideEntree(event));
                    oeuil.setOnMouseExited((event) -> boutonVideSortie(event));
                    oeuil.setOnAction((event) -> {
                    eltMenuSelectionner(bouttonRessource);
                    try {
                        ressourceCliquer(ressource);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    //met la HBox dans la liste principal
                    listePrincipal.getChildren().add(hbox);
                }
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
            // charge la vue ModuleTitreSae
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTS")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            //Lui associe le bon titre puis l'affiche
            Text titre = (Text) hbox.getChildren().get(0);
            titre.setText("SAES");
            listePrincipal.getChildren().add(hbox);
            //verifie pour chaque saes si ils ont une note
            String[] cles = Modele.getSae().keySet().toArray(new String[0]);
            Arrays.sort(cles);
            for (String cle : cles) {
                Sae sae = Modele.getSae().get(cle);
                if (sae.getNote() != null) {
                    //charge la vue ModuleMoyenne
                    fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MM")));
                    root = fxmlloader.load();
                    hbox = (HBox) root;
                    //va chercher les champs
                    VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
                    Text type = (Text) zoneEcriture.getChildren().get(1);
                    titre = (Text) zoneEcriture.getChildren().get(0);
                    HBox zoneDroite = (HBox) hbox.getChildren().get(1);
                    Button oeuil = (Button) zoneDroite.getChildren().get(1);
                    HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
                    Text note = (Text) zoneNote.getChildren().get(0);
                    //remplis les champs
                    type.setText("Sae");
                    titre.setText(sae.creerIntitule());
                    note.setText(sae.getNote().toString()); 
                    Button bouton = null;
                    for (Node node : listeSaes.getChildren()) {
                        bouton = (Button) node;
                        if (sae.creerIntitule().equals(bouton.getText())) {
                            break;
                        }
                    }
                    Button boutonCherche = bouton;
                    oeuil.setOnMouseEntered((event) -> boutonVideEntree(event));
                    oeuil.setOnMouseExited((event) -> boutonVideSortie(event));
                    oeuil.setOnAction((event) -> {
                    eltMenuSelectionner(boutonCherche);
                    try {
                        saeCliquer(sae);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    });
                    //met la HBox dans la liste principal
                    listePrincipal.getChildren().add(hbox);
                }
            }
        } catch (Exception e) {
            throw new Exception("application corrompu (17) : " + e.getMessage());
        }
    }
    
    //COMPETENCE
    
    /*
     * S'active quand un bouton competence est cliquer dans le menu
     */
    private void competenceCliquer(Competence competence) throws Exception {
        // Vide la vue principal
        viderListePrincipal();
        //Ajoute le titre de la compétence
        ajouterTitreComeptence(competence);
        //Pour chaque ressource associé, les affiche
        String[] cles = Modele.getRessources().keySet().toArray(new String[0]);
        Arrays.sort(cles);
        for(String cle : cles) {
            if (competence.getListeRessources().keySet().contains(Modele.getRessources().get(cle))) {
                Ressource ressource = Modele.getRessources().get(cle);
                ajouterRessourceComeptence(ressource, competence.getListeRessources().get(ressource));
            }
        }
        //Pour chaque ressource associé, les affiche
        cles = Modele.getSae().keySet().toArray(new String[0]);
        Arrays.sort(cles);
        for(String cle : cles) {
            if (competence.getListeSaes().keySet().contains(Modele.getSae().get(cle))) {
                Sae sae = Modele.getSae().get(cle);
                ajouterSaeComeptence(sae, competence.getListeSaes().get(sae));
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
            Text titre = ((Text) ((HBox) hbox.getChildren().get(0)).getChildren().get(0));
            Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
            Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
            titre.setText(competence.creerIntitule());
            if (competence.isCalculable()) {
                note.setText(String.format("%.2f", competence.calculerMoyenne())); 
            } else {
                note.setText("Moyenne indisponible");
                diviseur.setText("");
            }
            listePrincipal.getChildren().add(hbox);
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
            Text titre = (Text) ligneHaut.getChildren().get(1);
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
                note.setText(ressource.calculerMoyenne().toString()); 
            } else {
                note.setText("Moyenne indisponible");
                diviseur.setText("");
            }
            int boutonId = Integer.parseInt(ressource.getIdentifiant().substring(3, 5)) - 1;
            Button bouttonRessource = (Button) listeRessources.getChildren().get(boutonId);
            listePrincipal.getChildren().add(hbox);
            oeuil.setOnMouseEntered((event) -> boutonVideEntree(event));
            oeuil.setOnMouseExited((event) -> boutonVideSortie(event));
            oeuil.setOnAction((event) -> {
            eltMenuSelectionner(bouttonRessource);
            try {
                ressourceCliquer(ressource);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            });
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    /*
     * Ajoute les saes dans la competence
     */
    private void ajouterSaeComeptence(Sae sae, Double poids) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MR")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            VBox zoneEcriture = (VBox) hbox.getChildren().get(0);
            Text type = (Text) zoneEcriture.getChildren().get(1);
            HBox ligneHaut = (HBox) zoneEcriture.getChildren().get(0);
            Text titre = (Text) ligneHaut.getChildren().get(1);
            Text ponderation = (Text) ((HBox) ligneHaut.getChildren().get(0)).getChildren().get(0);
            HBox zoneDroite = (HBox) hbox.getChildren().get(1);
            Button oeuil = (Button) zoneDroite.getChildren().get(1);
            HBox zoneNote = (HBox) zoneDroite.getChildren().get(0);
            Text note = (Text) zoneNote.getChildren().get(0);
            Text diviseur = (Text) zoneNote.getChildren().get(1);
            ponderation.setText(String.format("%.0f ", poids * 100) + "%");
            type.setText("Sae");
            titre.setText(sae.creerIntitule());
            if (sae.getNote() != null) {
                note.setText(sae.getNote().toString()); 
            } else {
                note.setText("Moyenne indisponible");
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
            
            listePrincipal.getChildren().add(hbox);
            oeuil.setOnMouseEntered((event) -> boutonVideEntree(event));
            oeuil.setOnMouseExited((event) -> boutonVideSortie(event));
            oeuil.setOnAction((event) -> {
            eltMenuSelectionner(boutonCherche);
            try {
                saeCliquer(sae);
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            });
        } catch (Exception e) {
            throw new Exception("application corrompu (4) : " + e.getMessage());
        }
    }
    
    //RESSOURCE NORMAL
    
    /*
     * Est appelé quand une ressource est séléctionner
     */
    private void ressourceCliquer(Ressource ressource) throws Exception {
        // vide la vbox principal
        viderListePrincipal();
        //ajoute le titre de la ressource
        ajouterTitreRessource(ressource);
        //pour chaque evaluation associé rajoute son cadre, si il n'y a pas d"évaluations mais un cadre informatif
        ArrayList<Evaluation> listeEvaluation = ressource.getListeEvaluations();
        if (listeEvaluation.isEmpty()){
            afficherEvaluationVide();
        } else {
            for (Evaluation eval : listeEvaluation) {
                ajouterEvaluation(eval, ressource);
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
    
    /*
     * Ajoute une evaluation
     */
    private void ajouterEvaluation(Evaluation eval, Ressource ressource) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MEval")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            //Récupération de tous les éléments
            VBox colonneGauche = (VBox) hbox.getChildren().get(0);
            HBox ligneHaute = (HBox) colonneGauche.getChildren().get(0);
            Text ponderation = (Text) ((HBox) ligneHaute.getChildren().get(0)).getChildren().get(0);
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
            noteText.setOnAction((Event) -> noteChangerEvaluation(eval, feedback, noteText, ressource));
            noteText.focusedProperty().addListener((observable, oldValue, newValue) -> {
                if (oldValue) {
                    noteChangerEvaluation(eval, feedback, noteText, ressource);
                }
            });
            if (note != null) {
                noteText.setText(note.toString());
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (8) : " + e.getMessage() + ",");
        }
    }
    /*
     * Affichage dans le cas où il n'y a aucune évalauation
     */
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
    /**
     * Méthode appelé lors du changement d'une note
     */
    private void noteChangerEvaluation(Evaluation eval, Label feedback, TextField noteText, Ressource ressource) {
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
        updateMoyenneEvaluation(ressource);
    }
    /*
     * Met à jour la moyenne suite au changement d'une note
     */
    private void updateMoyenneEvaluation(Ressource ressource) {
        HBox hbox = (HBox) listePrincipal.getChildren().get(0);
        Text note = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(0);
        Text diviseur = (Text) ((HBox) hbox.getChildren().get(1)).getChildren().get(1);
        if (ressource.isCalculable()) {
            note.setText(ressource.calculerMoyenne().toString()); 
            diviseur.setText("/20");
        } else {
            note.setText("Moyenne indisponible");
            diviseur.setText("");
        }
    }
    
    //RESSOURCE EDITER MODALITER
    
    /*
     * Appelé lors su clic du bouton modifier une ressource
     */
    private void modaliteCliquer(Ressource ressource) throws Exception {
        //set ces tableau qui permeterons de suivre qu"elles évluations on été supprimé ect
        evalTmpId = new ArrayList<>();
        evalTmpIdRemoved = new ArrayList<>();
        //vide la vue principal
        viderListePrincipal();
        //Insere le titre
        modaliteTitre(ressource);
        //ajoute les évluations déjà existantes
        int i = 0;
        for (Evaluation eval : ressource.getListeEvaluations()) {
            modaliteElement(eval, i);
            i++;
        }
        //ajoute le bouton ajouter
        modaliteBouton();
    }
    /*
     * ajoute le titre
     */
    private void modaliteTitre(Ressource ressource) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MTRE")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            HBox hboxInterne = (HBox) hbox.getChildren().get(0);
            Text titre = (Text) hboxInterne.getChildren().get(0);
            Button boutonValider = (Button) hboxInterne.getChildren().get(1);
            Button boutonAnnuler = (Button) hboxInterne.getChildren().get(2);
            titre.setText(ressource.creerIntitule());
            boutonValider.setOnMouseEntered((event) -> boutonPleinEntree(event));
            boutonValider.setOnMouseExited((event) -> boutonPleinSortie(event));
            boutonAnnuler.setOnMouseEntered((event) -> boutonVideEntree(event));
            boutonAnnuler.setOnMouseExited((event) -> boutonVideSortie(event));
            boutonValider.setOnAction((event) -> {
                try {
                    sauvegarderPresser(ressource);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            boutonAnnuler.setOnAction((event) -> {
                try {
                    annulerModalite(ressource);
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (10) : " + e.getMessage());
        }
    }
    
    /*
     * Ajoute un encadrer évluation
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
            boutonSupprimer.setOnMouseEntered((event) -> boutonPleinEntree(event));
            boutonSupprimer.setOnMouseExited((event) -> boutonPleinSortie(event));
            boutonSupprimer.setOnAction((event) -> retirer(hbox, index));
            evalTmpId.add(index);
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (11) : " + e.getMessage());
        }
    }
    
    /*
     * ajoute le bouton ajouter
     */
    private void modaliteBouton() throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MREA")));
            Parent root = fxmlloader.load();
            HBox hbox = (HBox) root;
            Button bouton = (Button) hbox.getChildren().get(0);
            bouton.setOnMouseEntered((event) -> boutonPleinEntree(event));
            bouton.setOnMouseExited((event) -> boutonPleinSortie(event));
            bouton.setOnAction((event) -> {
                try {
                    ajouter();
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            });
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (11) : " + e.getMessage());
        }
    }
    
    /*
     * Retire uen évaluation
     */
    private void retirer(HBox hbox, int index) {
        evalTmpIdRemoved.add(evalTmpId.remove(evalTmpId.indexOf(index)));
        listePrincipal.getChildren().remove(hbox);
    }
    
    /*
     * Ajoute une évluation
     */
    private void ajouter() throws Exception {
        try {
            HBox boutonAjouter = (HBox) listePrincipal.getChildren().remove(listePrincipal.getChildren().size() - 1);
            modaliteElement(null, -1);
            listePrincipal.getChildren().add(boutonAjouter);
        } catch (Exception e) {
            throw new Exception("application corrompu (12) : " + e.getMessage());
        }
    }
    
    /*
     * Lancer quand le bouton annuler est appuyé
     */
    private void annulerModalite(Ressource ressource) throws Exception {
        evalTmpId = null;
        evalTmpIdRemoved = null;
        ressourceCliquer(ressource);
    }
    
    /*
     * lancé quand le bouton sauvegarder est appuyé
     */
    private void sauvegarderPresser(Ressource ressource) throws Exception {
        if (verifierModalite()) {
            int i = 1;
            for (int index : evalTmpId) {
                HBox racine = (HBox) listePrincipal.getChildren().get(i);
                VBox colonne = (VBox) racine.getChildren().get(0);
                TextField tDesc = (TextField) ((HBox) colonne.getChildren().get(0)).getChildren().get(1);
                TextField tPoids = (TextField) ((HBox) colonne.getChildren().get(1)).getChildren().get(1);
                TextField tDate = (TextField) ((HBox) colonne.getChildren().get(2)).getChildren().get(1);
                String description = tDesc.getText();
                Double ponderation = (Double.parseDouble(tPoids.getText().replace("%", "").trim()) / 100);
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
            for (int index : evalTmpIdRemoved.reversed()) {
                ressource.supprimerEvaluation(index);
            }
            annulerModalite(ressource);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("DONNEES INVALIDES");
            alert.setHeaderText("Assurez-vous que toutes les déscription soit bien remplie "
                    + "et que la somme des poids est égal à 100!");
            alert.showAndWait();
        }
    }
    
    /*
     * Verifie si les modalités sont correctes 
     * (appelé par la méthode saucegarder)
     */
    private boolean verifierModalite() throws Exception {
        int poids = 0;
        boolean ok = true;
        for (int i = 1; i < listePrincipal.getChildren().size() - 1 && ok; i++) {
            HBox racine = (HBox) listePrincipal.getChildren().get(i);
            VBox colonne = (VBox) racine.getChildren().get(0);
            TextField tDesc = (TextField) ((HBox) colonne.getChildren().get(0)).getChildren().get(1);
            TextField tPoids = (TextField) ((HBox) colonne.getChildren().get(1)).getChildren().get(1);
            TextField tDate = (TextField) ((HBox) colonne.getChildren().get(2)).getChildren().get(1);
            if (tDesc.getText().equals("")) {
                ok = false;
            }
            String poidsTexte = tPoids.getText().replace("%", "").trim();
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
     * Appelé lors du clic sur un boutopn sae dans le menu
     */
    private void saeCliquer(Sae sae) throws Exception {
        // vide la vue principal
        viderListePrincipal();
        // ajoute le titre
        ajouterTitreSae(sae);
        //ajoute la note de la sae
        ajouterElementSae(sae);
    }
    /*
     * ajotue le titre de la sae
     */
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
    
    /*
     * ajoute la note de la sae
     */
    private void ajouterElementSae(Sae sae) throws Exception {
        try {
            FXMLLoader fxmlloader = new FXMLLoader(getClass().getResource(EchangeurDeVue.getModule("MS")));
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
                titre.setText(note.toString());
            }
            listePrincipal.getChildren().add(hbox);
        } catch (Exception e) {
            throw new Exception("application corrompu (6) : " + e.getMessage());
        }
    }
    /*
     * Methode appelé dans le cas su changement d'une note
     */
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
}
