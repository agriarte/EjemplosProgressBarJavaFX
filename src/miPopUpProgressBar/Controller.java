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
    private Button id_btn02;

    @FXML
    private ProgressIndicator id_progressInd01;

    ProgressController progressController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("initialize controller");

        //acción botón 1
        id_btn01.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("a");
                ProgressController progressController = new ProgressController();
            }
        });

        //acción botón 2
        id_btn02.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                progressController.arrancarTarea2();
            }
        });

    }
}

