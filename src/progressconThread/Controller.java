package progressconThread;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

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
        id_progressbar1.setProgress(0.0);
        id_boton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Thread hiloProgressBar= new Thread(new bg_Thread1());
                //hilo.setDaemon(true);
                hiloProgressBar.start();
            }
        });

        id_boton2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Thread hiloProgressIndicate = new Thread(new bg_Thread2());
                hiloProgressIndicate.start();
            }
        });



    }

    class bg_Thread1 implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                id_progressbar1.setProgress((float)i/99);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);

            }
        }
    }

    class bg_Thread2 implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                id_progressindicator1.setProgress((float)i/99);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(i);

            }
        }
    }
}
