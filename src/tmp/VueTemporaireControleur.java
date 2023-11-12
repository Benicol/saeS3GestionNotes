package tmp;

import controleur.EchangeurDeVue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

/** TODO comment class responsibility (SRP)
 * @author benji
 *
 */
public class VueTemporaireControleur {
    
    @FXML
    void openPopUpChoixPseudo(ActionEvent event) {
        EchangeurDeVue.launchPopUp("vcp");
    }
    @FXML
    void openPopUpExporter(ActionEvent event) {
        EchangeurDeVue.launchPopUp("ve");
    }
    @FXML
    void openPopUpImporter(ActionEvent event) {
        EchangeurDeVue.launchPopUp("vi");
    }

}
