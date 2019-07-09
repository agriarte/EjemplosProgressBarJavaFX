package miPopUpProgressBar;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;


public class Controller implements Initializable {
    @FXML
    private Button id_btn01;

    @FXML
    private ProgressIndicator id_progressInd01;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize controller");
        id_btn01.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("a");
                ProgressController progressController = new ProgressController();

                /*try {
                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vistaProgress.fxml"));
                    Parent root1 = (Parent) fxmlLoader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root1));
                    stage.show();
                } catch(Exception e) {
                    e.printStackTrace();
                }*/


            }
        });
    }
}

