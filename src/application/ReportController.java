package application;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class ReportController {
	
	
	private Stage oldStage;
	
	@FXML
	private Button btnClose;

	public void btnClose() {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Scan.fxml"));
			this.oldStage.close();
			Stage stage = new Stage();
			stage.setScene(new Scene((Parent) loader.load()));
			stage.show();
			Stage closeStage = (Stage) btnClose.getScene().getWindow();
			closeStage.close();
		} catch (IOException e) {
		}
	}

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

	public void setOldStage(Stage stage){
		this.oldStage = stage;
	}
}
