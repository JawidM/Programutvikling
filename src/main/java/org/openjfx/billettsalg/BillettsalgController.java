
package org.openjfx.billettsalg;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;


public class BillettsalgController implements Initializable {
    @FXML
    private Spinner<?> spiAntallBilletter;

    @FXML
    private TextField txtTelefonnummer;

    @FXML
    private TextField txtEpost;

    @FXML
    private TextField txtEtternavn;

    @FXML
    private TextField txtFornavn;
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
