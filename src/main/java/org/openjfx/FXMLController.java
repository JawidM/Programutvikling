package org.openjfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class FXMLController implements Initializable{
    
    @Override
    public void initialize (URL url, ResourceBundle rb) {

    }
    //litt informajson
    @FXML
    private void registrereArrangementer(ActionEvent event) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/arrangementer.fxml"));
            Scene scene = new Scene(root);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Registrering av Arrangementer");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    
    @FXML
    private void registrereBilletter(ActionEvent event) throws IOException {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/org/openjfx/billettsalg.fxml"));
            Scene scene = new Scene(root);

            Stage stage = new Stage(StageStyle.DECORATED);
            stage.setTitle("Registrering av Billetter");
            stage.setScene(scene);
            stage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
        

    }
    
      
}
