import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MySmartHomeClock extends Application {
	Label time;
	LocalTime localTime;
	ScheduledService s;
	TimegetterTask t;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {

		t = new TimegetterTask();
		s = new MyScheduledService();
		s.setPeriod(Duration.seconds(1));
		s.start();
		time = new Label();
		StackPane root = new StackPane();
		root.getChildren().add(time);
		primaryStage.setScene(new Scene(root, 640, 480));
		time.setTextFill(Color.BLACK);
		time.setStyle(""
				+ "-fx-font-size:120; "
				+ "-fx-max-width: 640; "
				+ "-fx-max-height:480; "
				+ "-fx-alignment: center; "
				+ "-fx-text-fill: white; "
				+ "-fx-border-color:red; "
				+ "-fx-background-color: black; ");
		
		//time.setFont(new Font("Arial", 60));
		primaryStage.show();
		time.textProperty().bind(s.lastValueProperty());
	}

	class MyScheduledService extends ScheduledService<TimegetterTask> {
		@Override
		protected Task<TimegetterTask> createTask() {
			return new TimegetterTask();
		}
	}

	class TimegetterTask extends Task {
		@Override
		protected String call() throws Exception {
			return LocalTime.now().toString().substring(0, 5);
		}
	}
}
