
package org.openjfx.billettsalg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.openjfx.arrangementer.Arrangement;
import org.openjfx.arrangementer.ArrangementReader;
import org.openjfx.arrangementer.InvalidArrangementFormatException;

public class BillettsalgController implements Initializable {
    
    @FXML
    private TextField txtFornavn;

    @FXML
    private TextField txtEtternavn;

    @FXML
    private TextField txtEpost;

    @FXML
    private TextField txtTelefonNummer;
    
    @FXML
    private ComboBox cbxTypeBillett;
    
    @FXML
    private Spinner<Integer> spiAntallBilletter;
    
    @FXML
    private Button btnAvbrytt;
    
    @FXML
    private Button btnRegistrerBillett;
    
    @FXML
    private Button btnSlettBillett;

    @FXML
    private Button btnLagreOppdateringBillett;
            
    @FXML
    private Label lblPris;

    @FXML
    private ListView<Arrangement> listView;
    
    @FXML
    private ListView<Billett> listViewBillett;
    
    List<Arrangement> arrayListArrangement = new ArrayList<>();
    
    List<Billett> arrayListBillett = new ArrayList<>();
    
    ObservableList<String> typeBillett = FXCollections.observableArrayList("Voksen", "Student", "Barn", "Honnor");

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Henter data fra arrangement.csv filen
        try {
            arrayListArrangement = ArrangementReader.readArrangementer("src/main/resources/org/openjfx/arrangementer.csv");
        } catch (IOException e) {
            System.err.println("Could not read the requested file. Cause: " + e.getCause());
        } catch (InvalidArrangementFormatException e) {
            System.err.println("The data is not formatted correctly. Message: " + e.getMessage());
        }
        listView.getItems().addAll(arrayListArrangement);
        
        //henter data fra billettsalg.csv filen
        try {
            arrayListBillett = BillettReader.readBilletter("src/main/resources/org/openjfx/billettsalg.csv");
        } catch (IOException e) {
            System.err.println("Could not read the requested file. Cause: " + e.getCause());
        } catch (InvalidBillettFormatException e) {
            System.err.println("The data is not formatted correctly. Message: " + e.getMessage());
        }
        listViewBillett.getItems().addAll(arrayListBillett);
        
        SpinnerValueFactory<Integer> antallBilletterValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spiAntallBilletter.setValueFactory(antallBilletterValueFactory);
        spiAntallBilletter.setEditable(true); 
        
        //cbxTypeBillett.setValue("Velg type billett");
        cbxTypeBillett.setItems(typeBillett);
    }  
    
    @FXML
    private void registrerBillett(ActionEvent event) throws IOException {
    
        if (validateFornavn() && validateEtternavn() && validateEpost() && validateTelefonNummer() && validateTypeBillett()) {
            Billett nyBillett = new Billett(listView.getSelectionModel().getSelectedItems().toString(), txtFornavn.getText(), txtEtternavn.getText(), 
            txtEpost.getText(), Integer.parseInt(txtTelefonNummer.getText()), (String)cbxTypeBillett.getValue(), spiAntallBilletter.getValue());

            String sb = getCsvLine(nyBillett);

            File file = new File("src/main/resources/org/openjfx/billettsalg.csv");
            BufferedWriter w = new BufferedWriter(new FileWriter(file, true));
            w.append(sb.toString());
            w.close();

            arrayListBillett.add(nyBillett);
            listViewBillett.getItems().add(nyBillett);
        }
    }  
    
    @FXML
    private void slettBillett (ActionEvent event) throws IOException {
        listViewBillett.getItems().removeAll(listViewBillett.getSelectionModel().getSelectedItems());
        StringBuilder sb2 = new StringBuilder();
        
        for(Billett billett : listViewBillett.getItems()){
            sb2.append(getCsvLine(billett));
        }
        
        File file = new File("src/main/resources/org/openjfx/billettsalg.csv");
        BufferedWriter w = new BufferedWriter(new FileWriter(file, false));
        w.write(sb2.toString());
        w.close();
    }
    
    @FXML
    void oppdaterBillett(ActionEvent event) throws InvalidBillettFormatException {
//        String[] splitArr = listViewBillett.getSelectionModel().getSelectedItems().toString().split("]");
//        String arrangementInfo = splitArr[0] + "] ";
//        String[] split = listViewBillett.getSelectionModel().getSelectedItems().toString().split(",");
//        System.out.println(split[1] + " -- " + split[2] + " " + split[3] + " " + split[4] + " "+ split[5] + " " + split[6] + " " + split[7]);
//        if(split.length != 12) {
//            throw new InvalidBillettFormatException("Must use comma , to separate the three data fields");
//        }
//        String fornavn = split[6].split(":")[1];
//        txtFornavn.setText(fornavn);
//        
//        String etternavn = split[7].split(":")[1];
//        txtEtternavn.setText(etternavn);
//        
//        String epost = split[8].split(":")[1];
//        txtEpost.setText(epost);
//        
//        String telefonNummer = split[9].split(":")[1];
//        txtTelefonNummer.setText(telefonNummer);
//        
//        String typeBillett = split[10].split(":")[1];
//        cbxTypeBillett.setValue(typeBillett);
//        
//        String antallBilletter = split[11].split(":")[1];
//        cbxTypeBillett.setValue(antallBilletter);
//        antallBilletter = antallBilletter.replace("\n", "");
//        antallBilletter = antallBilletter.replace("]", "");
        
//        String[] split = listViewBillett.getSelectionModel().getSelectedItems().toString().split(",");
//        if(split.length != 12) {
//            
//            throw new InvalidBillettFormatException("Must use comma , to separate the three data fields");
//        }
//        
//        String arrangement = split[0].split(":")[1];
//        String fornavn = split[1].split(":")[1];
//        txtFornavn.setText(fornavn);
//        
//        String etternavn = split[2].split(":")[1];
//        txtEtternavn.setText(etternavn);
//        
//        String epost = split[3].split(":")[1];
//        txtEpost.setText(epost);
//        
//        String telefonNummer = split[4].split(":")[1];
//        txtTelefonNummer.setText(telefonNummer);
//        
//        String typeBillett = split[5].split(":")[1];
//        cbxTypeBillett.setValue(typeBillett);
//        
//        String antallBilletter = split[6].split(":")[1];
//        cbxTypeBillett.setValue(antallBilletter);
//        antallBilletter = antallBilletter.replace("\n", "");
//        antallBilletter = antallBilletter.replace("]", "");
//        
//        btnRegistrerBillett.setVisible(false);
//        btnSlettBillett.setVisible(false); 
//        btnLagreOppdateringBillett.setVisible(true); 
    }
    
    @FXML
    void lagreOppdateringBillett(ActionEvent event) {

    }
    
    @FXML
    private void avbrytt(ActionEvent event) {
        Stage stage = (Stage) btnAvbrytt.getScene().getWindow();
        stage.close();
    }
     
    @FXML
    private void typeBillettValg() {
        double pris = 0;
        if ("Student".equals(cbxTypeBillett.getValue())) {
            pris += 80 * spiAntallBilletter.getValue();
        }
        else if ("Barn".equals(cbxTypeBillett.getValue())) {
            pris += 50 * spiAntallBilletter.getValue();
        }
        else if ("Honnor".equals(cbxTypeBillett.getValue())) {
            pris += 60 * spiAntallBilletter.getValue();
        }
        else {
            pris += 120 * spiAntallBilletter.getValue();
        }
        lblPris.setText("Pris: "+Double.toString(pris));  
    }
    
    private String getCsvLine(Billett nyBillett) {
        StringBuilder sb = new StringBuilder();
        sb.append(nyBillett.getArrangementInfo() + ",");
        sb.append(nyBillett.getFornavn()+ ",");
        sb.append(nyBillett.getEtternavn()+ ",");
        sb.append(nyBillett.getEpost()+ ",");
        sb.append(nyBillett.getTelefonNummer()+ ",");
        sb.append(nyBillett.getTypeBillett()+ ",");
        sb.append(nyBillett.getAntallBilletter()+ ",");
        sb.append(System.lineSeparator()); //new line
        return sb.toString();
    }
    
    private boolean validateFornavn() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(txtFornavn.getText());
        if(m.find() && m.group().equals(txtFornavn.getText())){
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate fornavn");
            alert.setHeaderText(null);
            alert.setContentText("Vennlist tast inn gyldig fornavn");
            alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateEtternavn() {
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(txtEtternavn.getText());
        if(m.find() && m.group().equals(txtEtternavn.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate etternavn");
            alert.setHeaderText(null);
            alert.setContentText("Vennlist tast inn gyldig etternavn");
            alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateEpost() {
        Pattern p  = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txtEpost.getText());
        if (m.find() && m.group().equals(txtEpost.getText())){
            return true;
        }
        else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate epost");
            alert.setHeaderText(null);
            alert.setContentText("Vennlist tast inn gyldig epost");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean validateTelefonNummer() {
        Pattern p = Pattern.compile("[0-9]{8}");
        Matcher m = p.matcher(txtTelefonNummer.getText());
        if(m.find() && m.group().equals(txtTelefonNummer.getText())) {
            return true;
        } else {
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate telefonnummer");
            alert.setHeaderText(null);
            alert.setContentText("Vennlist tast inn gyldig telefonnummer");
            alert.showAndWait();
            
            return false;            
        }
    }
    private boolean validateTypeBillett(){
        
        if(cbxTypeBillett.getValue() != null){
            return true;
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Validate dato");
            alert.setHeaderText(null);
            alert.setContentText("Vennlist velg type billett");
            alert.showAndWait();
            
            return false;            
        }
    }
}
