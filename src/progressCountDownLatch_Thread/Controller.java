package progressCountDownLatch_Thread;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ProgressIndicator;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;

public class Controller implements Initializable {
    @FXML
    private Button id_boton1;


    @FXML
    private ProgressBar id_progressbar1;
    @FXML
    private ProgressIndicator id_progressindicator1;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        // NO FUNCIONA
        //ejercicio donde CountDownLatch detiene el flujo de programa esperando que todas las tareas terminen.
        //await() espera que terminen tareas
        //countdown() abre el pestillo de una tarea finalizada
        //new ContDownLatch(n) genera un objeto de control con n pestillos. Hasta que no se abren todos no continua
        int nTareas = 2;
        CountDownLatch countDownLatch = new CountDownLatch(nTareas);

        id_progressbar1.setProgress(0.0);
        id_boton1.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {

                System.out.println("iniciando Threads");

                Thread hiloProgressBar = new Thread(new bg_Thread1(countDownLatch));
                hiloProgressBar.start();

                Thread hiloProgressIndicate = new Thread(new bg_Thread2(countDownLatch));
                hiloProgressIndicate.start();


                //ultimo hilo, se crea nuevo hilo de ejecución para que no congele el hilo principal
                Task<Void> task = new Task<Void>() {
                    @Override
                    public Void call() throws InterruptedException {
                        //flujo se queda aqui esperando que finalicen tareas para abrir paso. Mientras tanto el hilo
                        //principal sigue corriendo
                        try {
                            countDownLatch.await();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        System.out.println("fin de procesos");
                        return null;
                    }
                };
                new Thread(task).start();

                System.out.println("Este texto sale antes que el fin de progress porque está en el hilo principal");
            }
        });


    }

    class bg_Thread1 extends Thread {

        private CountDownLatch latch;

        public bg_Thread1(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            for (int i = 0; i < 100; i++) {
                id_progressbar1.setProgress((float) i / 99);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }
    }

    class bg_Thread2 extends Thread {

        private CountDownLatch latch;

        public bg_Thread2(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public void run() {
            for (int i = 0; i < 40; i++) {

                id_progressindicator1.setProgress((float) i / 39);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            latch.countDown();
        }
    }
}
