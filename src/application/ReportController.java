package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

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
	public void initialize() {
//		timeLabel.setVisible(false);
//		fromLabel.setVisible(false);
//		toLabel.setVisible(false);
//		hostAliveLabel.setVisible(false);
//		totaltime.setVisible(false);
//		sec.setVisible(false);
//		ip.setVisible(false);
//		between.setVisible(false);
//		alive.setVisible(false);
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
	

}
