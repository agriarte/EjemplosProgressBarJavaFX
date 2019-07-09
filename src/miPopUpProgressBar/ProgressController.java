package miPopUpProgressBar;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ProgressIndicator;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ProgressController implements Initializable {
    @FXML
    public ProgressIndicator id_progressInd01;

    public ProgressController() {
        System.out.println("constructor progresscontroller");
        abrirSegundaventana();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

            System.out.println("initialize progresscontroller");


    }

    private void abrirSegundaventana(){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("vistaProgress.fxml"));
            //ERROR DIFICIL DE ENCONTRAR: muy importante que el controller se fije desde aqui y no en XML o dará error
            fxmlLoader.setController(this);
            Parent root1 = fxmlLoader.load();
            Stage stage = new Stage();
            stage.setScene(new Scene(root1));
            stage.show();
        } catch(Exception e) {
            e.printStackTrace();
        }

        /*
        //para actualizar Progress usar hilo de GUI o no verá en pantalla
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                //pone Progress al 30%
                id_progressInd01.setProgress(.3);
            }
        });
        //esta actualización no saldrá
        id_progressInd01.setProgress(.8);

        */

        //crear Task que actualiza Progress
        Task task = taskCreator(10);
        id_progressInd01.progressProperty().unbind();
        id_progressInd01.progressProperty().bind(task.progressProperty());
        new Thread(task).start();
    }

    //Create a New Task
    private Task taskCreator(int seconds) {
        return new Task() {

            @Override
            protected Object call() throws Exception {
                for (int i = 0; i < seconds; i++) {
                    Thread.sleep(1000);
                    updateProgress(i + 1, seconds);

                }
                return true;
            }
        };
    }
}






