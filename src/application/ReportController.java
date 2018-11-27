package application;

import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;

public class ReportController {
	
	@FXML
	private Label timeLabel;
	@FXML
	private Label fromLabel;
	@FXML
	private Label toLabel;
	@FXML
	private Label hostAliveLabel;
	@FXML
	private Label totaltime;
	@FXML
	private Label sec;
	@FXML
	private Label ip;
	@FXML
	private Label between;
	@FXML
	private Label alive;
	@FXML
	ProgressIndicator p1 = new ProgressIndicator();
	private Task<Boolean> copyWorker;
	
	@FXML
	public void initialize() {
		timeLabel.setVisible(false);
		fromLabel.setVisible(false);
		toLabel.setVisible(false);
		hostAliveLabel.setVisible(false);
		totaltime.setVisible(false);
		sec.setVisible(false);
		ip.setVisible(false);
		between.setVisible(false);
		alive.setVisible(false);
		finishedLoad();
	}
	
	public void setTime(String time) {
		timeLabel.setText(time);
	}
	
	public void setfromAddress(String from) {
		fromLabel.setText(from);
	}
	
	public void setToAddress(String to) {
		toLabel.setText(to);
	}
	
	public void setHostAlive(String hosts) {
		hostAliveLabel.setText(hosts);
	}
	
	public void finishedLoad() {
		p1.setProgress(0);
		copyWorker = createWorker();
		copyWorker.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			@Override
			public void handle(WorkerStateEvent event) {
				p1.setVisible(false);
				timeLabel.setVisible(true);
				fromLabel.setVisible(true);
				toLabel.setVisible(true);
				hostAliveLabel.setVisible(true);
				totaltime.setVisible(true);
				sec.setVisible(true);
				ip.setVisible(true);
				between.setVisible(true);
				alive.setVisible(true);
			}
		});
		p1.progressProperty().unbind();
		p1.progressProperty().bind(copyWorker.progressProperty());
		new Thread(copyWorker).start();
	}

	public Task createWorker() {
		return new Task() {
			@Override
			protected Object call() throws Exception {
				for (int i = 0; i <= 1000; i++) {
					Thread.sleep(2);
					updateProgress(i + 0.1, 1000);
				}
				return true;
			}
		};
	}
}
