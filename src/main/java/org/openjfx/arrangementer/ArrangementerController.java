
package org.openjfx.arrangementer;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
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
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.util.Callback;

public class ArrangementerController implements Initializable {
    
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
    private Button btnRegistrer;
    
    @FXML
    private Button btnSlett;
    
    @FXML
    private Spinner <Integer> spiTid;
    
    @FXML
    private Button btnAvbrytt;
    
    @FXML
    private ListView <Arrangement> listView;
    
    List<Arrangement> arrayListArrangement = new ArrayList<>();
    
    ObservableList<String> typeArrangement = FXCollections.observableArrayList("Kino", "Teater", "Forsamling");
    
    ObservableList<String> kino = FXCollections.observableArrayList("Kinosal 1", "Kinosal 2");

    ObservableList<String> teater = FXCollections.observableArrayList("Teatersal 1", "Teatersal 2");
    
    ObservableList<String> forsamling = FXCollections.observableArrayList("Forsamlingssal 1", "Forsamlingssal 2", "Forsamlingssal 3");
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        try {
            arrayListArrangement = ArrangementReader.readArrangementer("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/arrangementer.csv");
        } catch (IOException e) {
            System.err.println("Could not read the requested file. Cause: " + e.getCause());
        } catch (InvalidArrangementFormatException e) {
            System.err.println("The data is not formatted correctly. Message: " + e.getMessage());
        }
//        if(arrayListArrangement == null) { // some error has occurred
//            System.exit(1);
//        }

        listView.getItems().addAll(arrayListArrangement);
        
        //Implementasjon av ComboBox med sin value 
        cbxTypeArrangement.setValue("Kino");
        cbxTypeArrangement.setItems(typeArrangement);
        
        cbxSted.setValue("Kinossal 1");
        cbxSted.setItems(kino);
        
        //Implementasjon av Spinner med sine value kl 08.00 til 20.00
        SpinnerValueFactory<Integer> tidValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, 12);
        spiTid.setValueFactory(tidValueFactory);
        spiTid.setEditable(true);
        
        dpDate.setEditable(false);
        LocalDate minDate = LocalDate.now();
        restrictDatePicker(dpDate, minDate);
    } 
    
    @FXML
    private void registrer(ActionEvent event) throws IOException {
        
        if (validateNavn() && validateArtist() && validateDate()){
            
            Arrangement nyArrangement = new Arrangement((String)cbxTypeArrangement.getValue(), (String)cbxSted.getValue(), 
                    txtNavn.getText(), txtArtist.getText(), dpDate.getValue(), spiTid.getValue());

            //legger til data inn i arrangementer.csv filen min
            String sb = getCsvLine(nyArrangement);

            File file = new File("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/arrangementer.csv");
            BufferedWriter w = new BufferedWriter(new FileWriter(file, true));
            w.append(sb.toString());
            w.close();

            arrayListArrangement.add(nyArrangement);
            listView.getItems().add(nyArrangement); 
        }   
        
        //validering av registrering skjema med annen måte
//        Window owner = txtNavn.getScene().getWindow();   
//        if(txtNavn.getText().isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn arrangements navnet");
//            return;
//        }
//
//        else if(txtArtist.getText().isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn navnet til artisten eller lederen");
//            return;
//        }
//
//        else if(((TextField)dpDate.getEditor()).getText().isEmpty()){
//            AlertHelper.showAlert(Alert.AlertType.ERROR, owner, "Error!", "Vennligst tast inn datoen for arrangementet");
//            return;
//        }
    } 
    
    @FXML
    private void slett (ActionEvent event) throws IOException {
        listView.getItems().removeAll(listView.getSelectionModel().getSelectedItems());
        StringBuilder sb2 = new StringBuilder();
        
        for(Arrangement arrangement:listView.getItems()){
            sb2.append(getCsvLine(arrangement));
        }
        
        File file = new File("/Users/jawidmohammadi/Documents/GitHub/Programutvikling/src/main/resources/org/openjfx/arrangementer.csv");
        BufferedWriter w = new BufferedWriter(new FileWriter(file, false));
        w.write(sb2.toString());
        w.close();
    }
    
    @FXML
    private void oppdater(ActionEvent event) throws InvalidArrangementFormatException{
        
        
        
        String[] split = listView.getSelectionModel().getSelectedItems().toString().split(",");
        
        if(split.length != 6) {
            
            throw new InvalidArrangementFormatException("Must use comma , to separate the three data fields");

        }
        String typeArrangement = split[0].split(":")[1];
        cbxTypeArrangement.setValue(typeArrangement);
        
        String sted = split[1].split(":")[1];
        cbxSted.setValue(sted);
        
        String navn = split[2].split(":")[1];
        txtNavn.setText(navn);
        
        String artist = split[3].split(":")[1];
        txtArtist.setText(artist);
        
        String date = split[4].split(":")[1];
        LocalDate localDate = LocalDate.parse(date);
        dpDate.setValue(localDate);
        
        String tid = split[5].split(":")[1];
        spiTid.getValueFactory().setValue(Integer.parseInt(tid));
        
        btnRegistrer.setVisible(false);
        btnSlett.setVisible(false);
        
//        SpinnerValueFactory<Integer> tidenValueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(8, 20, Integer.parseInt(tid));
//        spiTid.setValueFactory(tidenValueFactory);
//        spiTid.setEditable(true);
        
        
        
//        
//        String str = listView.getSelectionModel().getSelectedItems();
//        cbxTypeArrangement.setValue(listView.getSelectionModel().getSelectedItems());
//        cbxSted.setValue(listView.getSelectionModel().getSelectedItems());
        

        
    }
    
    @FXML
    private void avbrytt(ActionEvent event) {
        
        Stage stage = (Stage) btnAvbrytt.getScene().getWindow();
        stage.close();
    }
    
    private String getCsvLine(Arrangement nyArrangement){
        StringBuilder sb = new StringBuilder();
        sb.append(nyArrangement.getTypeArrangement() + ",");
        sb.append(nyArrangement.getSted()+ ",");
        sb.append(nyArrangement.getNavn()+ ",");
        sb.append(nyArrangement.getArtist()+ ",");
        sb.append(nyArrangement.getDato()+ ",");
        sb.append(nyArrangement.getTid()+ ",");
        sb.append(System.lineSeparator()); //new line
        return sb.toString();
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
    
    private void restrictDatePicker(DatePicker datePicker, LocalDate minDate) {
        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            @Override
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override
                    public void updateItem(LocalDate item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item.isBefore(minDate)) {
                            setDisable(true);
                            setStyle("-fx-background-color: #ffc0cb;");
                        }else {

                        }
                    }
                };
            }
        };
        datePicker.setDayCellFactory(dayCellFactory);
    }
    
    private boolean validateNavn(){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(txtNavn.getText());
        if(m.find() && m.group().equals(txtNavn.getText())){
            return true;
        }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate arrangements navnet");
                alert.setHeaderText(null);
                alert.setContentText("Vennlist tast inn gyldig navn til arrangementet");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateArtist(){
        Pattern p = Pattern.compile("[a-zA-Z ]+");
        Matcher m = p.matcher(txtArtist.getText());
        if(m.find() && m.group().equals(txtArtist.getText())){
            return true;
        }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate artist");
                alert.setHeaderText(null);
                alert.setContentText("Vennlist tast inn gyldig artist eller leder navn");
                alert.showAndWait();
            
            return false;            
        }
    }
    
    private boolean validateDate(){
        
        if(dpDate.getValue() != null){
            return true;
        }else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Validate dato");
                alert.setHeaderText(null);
                alert.setContentText("Vennlist velg datoen");
                alert.showAndWait();
            
            return false;            
        }
    }
}
