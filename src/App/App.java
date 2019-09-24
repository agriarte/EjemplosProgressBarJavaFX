package App;

import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class App extends Application {


    //https://stackoverflow.com/questions/57626268/updating-two-or-more-progressbar-with-countdownlatch
    //Respuesta Slaw

    private static final int TASK_COUNT = 6;

    private static List<Task<?>> createTasks() {
        Random random = new Random();
        return IntStream.range(0, TASK_COUNT)
                .mapToObj(i -> new MockTask(random.nextInt(4_000) + 1_000))
                .collect(Collectors.toList());
    }

    private static boolean isTerminalState(Worker.State state) {
        return state == Worker.State.SUCCEEDED
                || state == Worker.State.CANCELLED
                || state == Worker.State.FAILED;
    }

    // Assumes none of the tasks are completed yet. May wish to validate that.
    private static void onTasksComplete(Runnable action, List<? extends Task<?>> tasks) {
        // local variables must be effectively final when used in lambda expressions
        int[] count = new int[]{tasks.size()};
        for (Task<?> task : tasks) {
            task.stateProperty().addListener((observable, oldState, newState) -> {
                if (isTerminalState(newState) && --count[0] == 0) {
                    action.run(); // invoked on FX thread
                }
            });
        }
    }

    @Override
    public void start(Stage primaryStage) {
        List<Task<?>> tasks = createTasks();

        VBox root = new VBox(10);
        root.setPadding(new Insets(15));
        root.setAlignment(Pos.CENTER);

        for (Task<?> task : tasks) {
            ProgressBar progressBar = new ProgressBar();
            progressBar.setMaxWidth(Double.MAX_VALUE);
            // In a real application you'd probably need to unbind when the task completes
            progressBar.progressProperty().bind(task.progressProperty());
            root.getChildren().add(progressBar);
        }

        primaryStage.setScene(new Scene(root, 500, -1));
        primaryStage.setTitle("Concurrency");
        primaryStage.show();

        onTasksComplete(() -> {
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.initOwner(primaryStage);
            alert.setHeaderText(null);
            alert.setContentText("All tasks have completed.");
            alert.show();
        }, tasks);

        ExecutorService executor = Executors.newFixedThreadPool(tasks.size(), r -> {
            // use daemon threads
            Thread thread = new Thread(r);
            thread.setDaemon(true);
            return thread;
        });
        tasks.forEach(executor::execute);
        executor.shutdown();
    }

    private static class MockTask extends Task<Void> {

        private final int iterations;

        MockTask(int iterations) {
            this.iterations = iterations;
        }

        @Override
        protected Void call() throws Exception {
            updateProgress(0, iterations);
            for (int i = 0; i < iterations; i++) {
                Thread.sleep(1L);
                updateProgress(i + 1, iterations);
            }
            return null;
        }

    }

}