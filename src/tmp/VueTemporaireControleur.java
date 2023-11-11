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
    void openPopUp(ActionEvent event) {
        EchangeurDeVue.launchPopUp("vr");
    }

}
