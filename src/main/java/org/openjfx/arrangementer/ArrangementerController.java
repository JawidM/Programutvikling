
package org.openjfx.arrangementer;

import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;

public class ArrangementerController implements Initializable {
    ArrayList<Arrangement> arrangementTabell = new ArrayList();
    
    ObservableList<String> typeArrangement = FXCollections
            .observableArrayList("Kino", "Teater", "Forsamling");
    
    ObservableList<String> kino = FXCollections
            .observableArrayList("Kinosal 1", "Kinosal 2");

    ObservableList<String> teater = FXCollections
            .observableArrayList("Teatersal 1", "Teatersal 2");
    
    ObservableList<String> forsamling = FXCollections
            .observableArrayList("Forsamlingssal 1", "Forsamlingssal 2", "Forsamlingssal 3");
    
    @FXML
    private ComboBox cbxTypeArrangement;
    
    @FXML
    private ComboBox cbxSted;
    
    @FXML
    private TextField txtNavn;

    @FXML
    private TextField txtArtist;

    @FXML
    private DatePicker dpDate;
    
    @FXML
    private Label label;

    @FXML
    private Spinner<Integer> spiTid;
    
    @FXML
    void registrer(ActionEvent event) {
        
//        //andre måten å bruke:
//        LocalDate value = dpDate.getValue();
//        String typeArrangementString = String.valueOf(cbxTypeArrangement);
//        String sted = String.valueOf(cbxSted);
//        Arrangement nyArrangement = new Arrangement(typeArrangementString, sted, 
//                txtNavn.getText(), txtArtist.getText(), value);
//        arrangementTabell.add(nyArrangement);

        //første måten min er her:   
        LocalDate value = dpDate.getValue();
        Arrangement nyArrangement = new Arrangement((String)cbxTypeArrangement.getValue(), 
                (String)cbxSted.getValue(),
                txtNavn.getText(), txtArtist.getText(), value, spiTid.getValue());
        
        arrangementTabell.add(nyArrangement);
        
        for (Arrangement i : arrangementTabell){
            label.setText(i.toString());
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Implementasjon av ComboBox med sin value 
        cbxTypeArrangement.setValue("Kino");
        cbxTypeArrangement.setItems(typeArrangement);
        
        cbxSted.setValue("Kinossal 1");
        cbxSted.setItems(kino);
        
        //Implementasjon av Spinner med sine value kl 08.00 til 20.00
        SpinnerValueFactory<Integer> tidValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, 12);
        spiTid.setValueFactory(tidValueFactory);
        spiTid.setEditable(true);
    }  
    
    @FXML
    private void typeArrangementValg(){
        if (cbxTypeArrangement.getValue().equals("Kino")){
            cbxSted.setValue("Kinosal 1");
            cbxSted.setItems(kino);
        }
        else if (cbxTypeArrangement.getValue().equals("Teater")){
            cbxSted.setValue("Teatersal 1");
            cbxSted.setItems(teater);
        }
        else {
            cbxSted.setValue("Forsamlingssal 1");
            cbxSted.setItems(forsamling);
        }
        
    }
    
}
