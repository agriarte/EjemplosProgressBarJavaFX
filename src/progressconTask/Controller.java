package progressconTask;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    @FXML
    private Button id_boton1;
    @FXML
    private Button id_boton2;

    @FXML
    private ProgressBar id_progressbar1;
    @FXML
    private ProgressIndicator id_progressindicator1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        id_progressbar1.indeterminateProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if(newValue){
                    System.out.println("Calculating Time");
                }
                else{
                    System.out.println("In Progress");

                }
            }
        });

        id_progressbar1.progressProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if(newValue.doubleValue()==1){
                    System.out.println("Work Done");

                }
            }
        });

        id_boton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("progress");
                Task task = taskCreator(10);
                id_progressbar1.progressProperty().unbind();
                id_progressbar1.progressProperty().bind(task.progressProperty());
                new Thread(task).start();

                //evento fin tarea boton 1
                task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        System.out.println("fin tarea boton progressBar");
                    }
                });
            }
        });

        id_boton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("progress");
                Task task = taskCreator(10);
                id_progressindicator1.progressProperty().unbind();
                id_progressindicator1.progressProperty().bind(task.progressProperty());
                new Thread(task).start();

                //evento fin tarea boton 2
                task.addEventHandler(WorkerStateEvent.WORKER_STATE_SUCCEEDED, new EventHandler() {
                    @Override
                    public void handle(Event event) {
                        System.out.println("fin tarea boton progressInd");
                    }
                });
            }
        });

    }

    //Create a New Task
    private Task taskCreator(int seconds){
        return new Task() {

            @Override
            protected Object call() throws Exception {
                for(int i=0; i<seconds;i++){
                    Thread.sleep(1000);
                    updateProgress(i+1, seconds);

                }
                return true;
            }
        };
    }
}
