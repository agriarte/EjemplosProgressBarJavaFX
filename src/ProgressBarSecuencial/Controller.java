package ProgressBarSecuencial;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

public class Controller implements Initializable {

    @FXML
    public ProgressBar idProgressBar01;
    @FXML
    private Button idBtn01;
    @FXML
    private Button idBtn02;
    @FXML
    private Button idBtn03;
    @FXML
    private Button idBtn04;
    @FXML
    private Button idBtn05;
    @FXML
    private Button idBtn06;
    @FXML
    private Button idBtn07;
    @FXML
    private ProgressBar idProgressBar02;
    @FXML
    private ProgressBar idProgressBar03;
    @FXML
    private ProgressBar idProgressBar04;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        idBtn01.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("hola task");
                Task task = HacerTask();
                new Thread(task).start();

                task.setOnSucceeded(event3 -> System.out.println("evento fin de task..."));
            }
        });

        //evento por lambda
        idBtn02.setOnAction(event -> {
            System.out.println("hola class");

            HacerTareaCallable tareaCall = new HacerTareaCallable();

            //new Thread(tareaCall).start();
            Thread tareaCallable = new Thread(tareaCall);
            tareaCallable.start();
        });

        idBtn03.setOnAction(event -> {
            System.out.println("btn03");

            ProgresoBarra miProgreso01 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar01.progressProperty().unbind();
            idProgressBar01.progressProperty().bind(miProgreso01.progressProperty());

            Thread miCallProgreso01 = new Thread(miProgreso01);
            miCallProgreso01.start();
        });

        idBtn04.setOnAction(event -> {
            System.out.println("btn4");

            //Iniciar los 4 Progress

            //para Progress01
            ProgresoBarra miProgreso01 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar01.progressProperty().unbind();
            idProgressBar01.progressProperty().bind(miProgreso01.progressProperty());

            Thread miCallProgreso01 = new Thread(miProgreso01);
            miCallProgreso01.start();

            //para Progress02
            ProgresoBarra miProgreso02 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar02.progressProperty().unbind();
            idProgressBar02.progressProperty().bind(miProgreso02.progressProperty());

            Thread miCallProgreso02 = new Thread(miProgreso02);
            miCallProgreso02.start();

            //para Progress03
            ProgresoBarra miProgreso03 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar03.progressProperty().unbind();
            idProgressBar03.progressProperty().bind(miProgreso03.progressProperty());

            Thread miCallProgreso03 = new Thread(miProgreso03);
            miCallProgreso03.start();

            //para Progress04
            ProgresoBarra miProgreso04 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar04.progressProperty().unbind();
            idProgressBar04.progressProperty().bind(miProgreso04.progressProperty());

            Thread miCallProgreso04 = new Thread(miProgreso04);
            miCallProgreso04.start();
        });

        idBtn05.setOnAction(event -> {
            System.out.println("btn5 Semaphore");
            //Iniciar los 4 Progress secuencialmente con Semaphore. Creo una tarea adicional final para imprimir
            // aviso de fin de tareas. Hasta que no acaba uno no empieza el siguiente:

            //asigno permisos para un solo proceso a la vez
            //en cada clase acquire() retiene el flujo en esa clase y no se libera hasta ejecutar el release();
            Semaphore miSemaforo = new Semaphore(1);

            //para Progress01
            ProgresoBarraSemaforo miProgreso01 = new ProgresoBarraSemaforo(miSemaforo);

            //asociar la clase con el progreso
            idProgressBar01.progressProperty().unbind();
            idProgressBar01.progressProperty().bind(miProgreso01.progressProperty());

            Thread miCallProgreso01 = new Thread(miProgreso01);
            miCallProgreso01.start();

            //para Progress02
            ProgresoBarraSemaforo miProgreso02 = new ProgresoBarraSemaforo(miSemaforo);

            //asociar la clase con el progreso
            idProgressBar02.progressProperty().unbind();
            idProgressBar02.progressProperty().bind(miProgreso02.progressProperty());

            Thread miCallProgreso02 = new Thread(miProgreso02);
            miCallProgreso02.start();

            //para Progress03
            ProgresoBarraSemaforo miProgreso03 = new ProgresoBarraSemaforo(miSemaforo);

            //asociar la clase con el progreso
            idProgressBar03.progressProperty().unbind();
            idProgressBar03.progressProperty().bind(miProgreso03.progressProperty());

            Thread miCallProgreso03 = new Thread(miProgreso03);
            miCallProgreso03.start();

            //para Progress04
            ProgresoBarraSemaforo miProgreso04 = new ProgresoBarraSemaforo(miSemaforo);

            //asociar la clase con el progreso
            idProgressBar04.progressProperty().unbind();
            idProgressBar04.progressProperty().bind(miProgreso04.progressProperty());

            Thread miCallProgreso04 = new Thread(miProgreso04);
            miCallProgreso04.start();

            //ultimo hilo
            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    miSemaforo.acquire();
                    System.out.println("fin de procesos");
                    miSemaforo.release();
                    return null;
                }
            };

            new Thread(task).start();
            //Thread thread = new Thread(task);
            //thread.start();
        });

        idBtn06.setOnAction(event -> {
            System.out.println("btn6 Iniciciando CountDownLatch");
            //Iniciar los 4 Progress secuencialmente con CountDownLatch
            CountDownLatch countDownLatch = new CountDownLatch(4);

            //para Progress01
            ProgresoBarraCountDownLatch miProgreso01 = new ProgresoBarraCountDownLatch(countDownLatch);

            //asociar la clase con el progreso
            idProgressBar01.progressProperty().unbind();
            idProgressBar01.progressProperty().bind(miProgreso01.progressProperty());

            Thread miCallProgreso01 = new Thread(miProgreso01);
            miCallProgreso01.start();

            //para Progress02
            ProgresoBarraCountDownLatch miProgreso02 = new ProgresoBarraCountDownLatch(countDownLatch);

            //asociar la clase con el progreso
            idProgressBar02.progressProperty().unbind();
            idProgressBar02.progressProperty().bind(miProgreso02.progressProperty());

            Thread miCallProgreso02 = new Thread(miProgreso02);
            miCallProgreso02.start();

            //para Progress03
            ProgresoBarraCountDownLatch miProgreso03 = new ProgresoBarraCountDownLatch(countDownLatch);

            //asociar la clase con el progreso
            idProgressBar03.progressProperty().unbind();
            idProgressBar03.progressProperty().bind(miProgreso03.progressProperty());

            Thread miCallProgreso03 = new Thread(miProgreso03);
            miCallProgreso03.start();

            //para Progress04
            ProgresoBarraCountDownLatch miProgreso04 = new ProgresoBarraCountDownLatch(countDownLatch);

            //asociar la clase con el progreso
            idProgressBar04.progressProperty().unbind();
            idProgressBar04.progressProperty().bind(miProgreso04.progressProperty());

            Thread miCallProgreso04 = new Thread(miProgreso04);
            miCallProgreso04.start();


            //ultimo hilo, se crea nuevo hilo de ejecución para que no congele el hilo principal
            Task<Void> task = new Task<Void>() {
                @Override
                public Void call() throws InterruptedException {
                    //flujo se queda aqui esperando que finalicen tareas para abrir paso
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
        });
        idBtn07.setOnAction(event -> {
            //1. Create a task(Runnable Object) to execute
            //2. Create Executor Pool using Executors
            //3. Pass tasks to Executor Pool
            //4. Shutdown the Executor Pool
            //ejemplo con Thread Pools
            //Paso 1: crear una cola de Threads indicando num. max de hilos en ejecución
            ExecutorService pool = Executors.newFixedThreadPool(1);

            //Iniciar los 4 Progress

            //para Progress01
            ProgresoBarra miProgreso01 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar01.progressProperty().unbind();
            idProgressBar01.progressProperty().bind(miProgreso01.progressProperty());

            Thread miCallProgreso01 = new Thread(miProgreso01);

            //algunas pruebas de cazar evento de empieza y acaba Task
            miProgreso01.setOnSucceeded(event1 -> {
                System.out.println("cazado fin de evento Progreso01");
            });
            miProgreso01.setOnRunning(event2 -> {
                System.out.println("cazado running evento Progreso01. Solo se imprime una vez");
            });


            //para Progress02
            ProgresoBarra miProgreso02 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar02.progressProperty().unbind();
            idProgressBar02.progressProperty().bind(miProgreso02.progressProperty());

            Thread miCallProgreso02 = new Thread(miProgreso02);


            //para Progress03
            ProgresoBarra miProgreso03 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar03.progressProperty().unbind();
            idProgressBar03.progressProperty().bind(miProgreso03.progressProperty());

            Thread miCallProgreso03 = new Thread(miProgreso03);


            //para Progress04
            ProgresoBarra miProgreso04 = new ProgresoBarra();

            //asociar la clase con el progreso
            idProgressBar04.progressProperty().unbind();
            idProgressBar04.progressProperty().bind(miProgreso04.progressProperty());

            Thread miCallProgreso04 = new Thread(miProgreso04);


            //Paso 3.  Ejecutar desde pool los objetos Task o Thread
            pool.execute(miProgreso01);
            pool.execute(miProgreso02);
            pool.execute(miProgreso03);
            pool.execute(miProgreso04);

            // pool shutdown ( Step 4)
            pool.shutdown();

            System.out.println("este texto sale antes del FINAL de los Task porque está en el hilo principal");
        });

    }

    //crear Task
    private Task HacerTask() {
        return new Task() {
            @Override
            protected Object call() throws Exception {
                System.out.println("dentro del Task");
                return null;
            }
        };
    }
}


class HacerTareaCallable extends Task<Void> {
    @Override
    protected Void call() throws Exception {
        System.out.println("dentro de Callable");
        return null;
    }
}

class ProgresoBarra extends Task<Void> {

    private int total = 100;
    private int iterador = 0;

    @Override
    protected Void call() throws Exception {
        System.out.println("dentro call Progress");


        this.updateProgress(0, total);

        for (iterador = 0; iterador <= total; iterador++) {
            this.updateProgress(iterador, total);
            Thread.sleep(20);
        }
        return null;
    }
}


class ProgresoBarraSemaforo extends Task<Void> {

    private int total = 100;
    private int iterador = 0;
    private Semaphore miSemaphore;

    public ProgresoBarraSemaforo(Semaphore miSemaphore) {
        this.miSemaphore = miSemaphore;
    }

    @Override
    protected Void call() throws Exception {
        System.out.println("esperando semaforo");
        miSemaphore.acquire();
        System.out.println("semaforo abierto");
        this.updateProgress(0, total);
        System.out.println("semaforo abierto2");
        for (iterador = 0; iterador <= total; iterador++) {
            this.updateProgress(iterador, total);
            Thread.sleep(20);
        }
        miSemaphore.release();
        return null;
    }
}

class ProgresoBarraCountDownLatch extends Task<Void> {

    private int total = 100;
    private int iterador = 0;
    private CountDownLatch latch;

    public ProgresoBarraCountDownLatch(CountDownLatch latch) {
        this.latch = latch;
    }

    @Override
    protected Void call() throws Exception {

        this.updateProgress(0, total);

        for (iterador = 0; iterador <= total; iterador++) {
            this.updateProgress(iterador, total);
            Thread.sleep(20);
        }
        latch.countDown();
        return null;
    }
}



