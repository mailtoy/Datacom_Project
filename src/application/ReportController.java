package application;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ReportController {
	
	@FXML
	private Label timeLabel, fromLabel, toLabel, totalLabel, hostAliveLabel;
	
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
	
	public void setTotal(String total) {
		totalLabel.setText(total);
	}
	
}
