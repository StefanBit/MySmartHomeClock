import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.concurrent.ScheduledService;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class MySmartHomeClock extends Application {
	LocalTime localTime;
	LocalDate localDate;
	ScheduledService timeService;
	TimegetterTask timegetterTask;
	@FXML
	Pane pane;
	@FXML
	Label dateLabel;
	@FXML
	Label timeLabel;
	DategetterTask dategetterTask;
	ScheduledService dateService;

	
	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception{
		FXMLLoader loader = new FXMLLoader(getClass().getResource("MySmartHomeClock.fxml"));
		Parent root=loader.load();	
		primaryStage.setScene(new Scene(root, 800, 480));
		primaryStage.show();
		System.out.println("Strt");
		
		
		}
	
	@FXML
	public void initialize() {
		System.out.println("Initialize GUI");
		timegetterTask = new TimegetterTask();
		timeService = new MyTimeScheduledService();
		dategetterTask = new DategetterTask();
		dateService = new MyDateScheduledService();
		timeService.setPeriod(Duration.seconds(1));
		dateService.setPeriod(Duration.seconds(1));
		dateService.start();
		timeService.start();
		timeLabel.textProperty().bind(timeService.lastValueProperty());
		dateLabel.textProperty().bind(dateService.lastValueProperty());

	}
	
	class MyTimeScheduledService extends ScheduledService<TimegetterTask> {
		@Override
		protected Task<TimegetterTask> createTask() {
			return new TimegetterTask();
		}
	}
	class MyDateScheduledService extends ScheduledService<DategetterTask> {
		@Override
		protected Task<DategetterTask> createTask() {
			return new DategetterTask();
		}
	}

	class TimegetterTask extends Task {
		@Override
		protected String call() throws Exception {
			return LocalTime.now().toString().substring(0, 5);
		}
	}
	class DategetterTask extends Task {
		@Override
		protected String call() throws Exception {
			DateTimeFormatter formatter=DateTimeFormatter.ofPattern("E',' dd LLL'.'");
			return LocalDate.now().format(formatter);
		}
	}

}
