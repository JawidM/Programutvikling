
package org.openjfx.arrangementer;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;


public class ArrangementerController implements Initializable {

    @FXML
    private ChoiceBox<?> chbSted;

    @FXML
    private TextField txtNavn;

    @FXML
    private TextField txtFornavn;

    @FXML
    private DatePicker dpDate;

    @FXML
    private Spinner<?> spiTid;

    @FXML
    private ComboBox<?> cobTypeArrangement;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
