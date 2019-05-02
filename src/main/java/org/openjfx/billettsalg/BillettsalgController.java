
package org.openjfx.billettsalg;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.openjfx.arrangementer.AlertHelper;
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
    private Spinner<Integer> spiAntallBilletter;
    
    @FXML
    private Button btnAvbrytt;

    @FXML
    private ListView<Arrangement> listView;
    
    @FXML
    private ListView<Billett> listViewBillett;
    
    List<Arrangement> arrayListArrangement = new ArrayList<>();
    
    List<Billett> arrayListBillett = new ArrayList<>();

    @Override
    
    public void initialize(URL url, ResourceBundle rb) {
        
        //Henter data fra arrangement.csv filen
        try {
            arrayListArrangement = ArrangementReader.readArrangementer("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/arrangementer.csv");
        } catch (IOException e) {
            System.err.println("Could not read the requested file. Cause: " + e.getCause());
        } catch (InvalidArrangementFormatException e) {
            System.err.println("The data is not formatted correctly. Message: " + e.getMessage());
        }
        listView.getItems().addAll(arrayListArrangement);
        
        //henter data fra billettsalg.csv filen
        try {
            arrayListBillett = BillettReader.readBilletter("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/billettsalg.csv");
        } catch (IOException e) {
            System.err.println("Could not read the requested file. Cause: " + e.getCause());
        } catch (InvalidBillettFormatException e) {
            System.err.println("The data is not formatted correctly. Message: " + e.getMessage());
        }
        listViewBillett.getItems().addAll(arrayListBillett);
        
        SpinnerValueFactory<Integer> antallBilletterValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 50, 1);
        spiAntallBilletter.setValueFactory(antallBilletterValueFactory);
        spiAntallBilletter.setEditable(true);  
    }  
    
    @FXML
    private void registrerBillett(ActionEvent event) throws IOException {
    
        if (validateFornavn() && validateEtternavn() && validateEpost() && validateTelefonNummer()) {
            Billett nyBillett = new Billett(listView.getSelectionModel().getSelectedItems().toString(), txtFornavn.getText(), txtEtternavn.getText(), 
            txtEpost.getText(), Integer.parseInt(txtTelefonNummer.getText()), spiAntallBilletter.getValue());

            String sb = getCsvLine(nyBillett);

            File file = new File("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/billettsalg.csv");
            BufferedWriter w = new BufferedWriter(new FileWriter(file, true));
            w.append(sb.toString());
            w.close();

            arrayListBillett.add(nyBillett);
            listViewBillett.getItems().add(nyBillett);
        }
        //validering av registrering skjema
//        Window owner = txtFornavn.getScene().getWindow();   
//        if(txtFornavn.getText().isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn fornavn");
//            return;
//        }
//
//        else if(txtEtternavn.getText().isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn etternavn");
//            return;
//        }
//
//        else if((txtEpost.getText()).isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn epost");
//            return;
//        }
//        
//        else if((txtTelefonNummer.getText()).isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn telefonnummer");
//            return;
//        }
        
        //LocalDate dato = dpDate.getValue();
        //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-mm-yyyy");
         
    }  
    
    @FXML
    private void avbrytt(ActionEvent event){
        Stage stage = (Stage) btnAvbrytt.getScene().getWindow();
        stage.close();
    }
            
    private String getCsvLine(Billett nyBillett){
        StringBuilder sb = new StringBuilder();
        sb.append(nyBillett.getArrangementInfo() + ",");
        sb.append(nyBillett.getFornavn()+ ",");
        sb.append(nyBillett.getEtternavn()+ ",");
        sb.append(nyBillett.getEpost()+ ",");
        sb.append(nyBillett.getTelefonNummer()+ ",");
        sb.append(nyBillett.getAntallBilletter()+ ",");
        sb.append(System.lineSeparator()); //new line
        return sb.toString();
    }
    
    private boolean validateFornavn(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(txtFornavn.getText());
        if(m.find() && m.group().equals(txtFornavn.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate fornavn");
                alert.setHeaderText(null);
                alert.setContentText("Vennlist tast inn gyldig fornavn");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateEtternavn(){
        Pattern p = Pattern.compile("[a-zA-Z]+");
        Matcher m = p.matcher(txtEtternavn.getText());
        if(m.find() && m.group().equals(txtEtternavn.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate etternavn");
                alert.setHeaderText(null);
                alert.setContentText("Vennlist tast inn gyldig etternavn");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateEpost(){
        Pattern p  = Pattern.compile("[a-zA-Z0-9][a-zA-Z0-9._]*@[a-zA-Z0-9]+([.][a-zA-Z]+)+");
        Matcher m = p.matcher(txtEpost.getText());
        if (m.find() && m.group().equals(txtEpost.getText())){
            return true;
        }
        else{
            Alert alert = new Alert(AlertType.WARNING);
            alert.setTitle("Validate epost");
            alert.setHeaderText(null);
            alert.setContentText("Vennlist tast inn gyldig epost");
            alert.showAndWait();
            return false;
        }
    }
    
    private boolean validateTelefonNummer(){
        Pattern p = Pattern.compile("[0-9]*8");
        Matcher m = p.matcher(txtTelefonNummer.getText());
        if(m.find() && m.group().equals(txtTelefonNummer.getText())){
            return true;
        }else{
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Validate telefonnummer");
                alert.setHeaderText(null);
                alert.setContentText("Vennlist tast inn gyldig telefonnummer");
                alert.showAndWait();
            
            return false;            
        }
    }
}
