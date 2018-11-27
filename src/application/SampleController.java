package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.MissingFormatArgumentException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SampleController extends Window {

	@FXML
	private TextField ipFrom, ipTo, hostName;
	@FXML
	private ListView<String> hostNameCol, ipCol, pingCol;
	@FXML
	private Button startBtn;
	private Thread sercher;
	private int totalScanned;
	private long startTime, elapsedTime;

	@FXML
	public void initialize() {
		LoadMe();
	}

	void LoadMe() {
		try {
			ipFrom.setText(InetAddress.getLocalHost().getHostAddress());
			hostName.setText(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void addToListView(ListView<String> listView, String value) {
		listView.getItems().add(value);
	}

	public void clearListView(ListView<String> listview) {
		listview.getItems().clear();
	}

	@FXML
	public void startClick(ActionEvent event) {
		startBtn.setDisable(true);
		startTime = System.currentTimeMillis();
		sercher = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					InetAddress.getByName(ipFrom.getText());
					InetAddress.getByName(ipTo.getText());
				} catch (MissingFormatArgumentException fe) {
					fe.printStackTrace();
				} catch (UnknownHostException e) {
					e.printStackTrace();
				}
			}
		});
		clearListView(hostNameCol);
		clearListView(ipCol);
		clearListView(pingCol);
		sercher.setName("Network searching thread");
		sercher.start();

		Node source = (Node) event.getSource();
		Stage stage = (Stage) source.getScene().getWindow();

		Find(stage);
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void Find(Stage stage) {
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Error alert");
		alert.setHeaderText(null);
		int lastFrom = ipFrom.getText().lastIndexOf(".");
		int lastTo = ipTo.getText().lastIndexOf(".");
		String from = ipFrom.getText().substring(lastFrom + 1);
		String to = ipTo.getText().substring(lastTo + 1);
		int fromInt = Integer.parseInt(from);
		int toInt = Integer.parseInt(to);
		int result = 0;
		totalScanned = 0;
		if (ipFrom.getText().trim().isEmpty() || ipTo.getText().trim().isEmpty() || ipFrom.getText() == null
				|| ipTo.getText() == null) {
			alert.setContentText("IP Address(es) cannot be empty.");
			alert.showAndWait();
		} else if (fromInt > toInt || fromInt < 0 || fromInt > 255 || toInt < 0 || toInt > 255) {
			alert.setContentText("Wrong input!");
			alert.showAndWait();
		} else {
			for (int i = Integer.parseInt(from); i <= Integer.parseInt(to); i++) {
				long currentTime = System.currentTimeMillis();
				try {
					String address = ipTo.getText().substring(0, lastTo + 1);
					InetAddress ip = InetAddress.getByName(address + i);
					if (ip.isReachable(100)) { // Try for one tenth of a second
						currentTime = System.currentTimeMillis() - currentTime;
						addToListView(hostNameCol, ip.getHostName());
						addToListView(ipCol, ip.getHostAddress());
						addToListView(pingCol, (currentTime / 1000.0) + "ms");
						result++;
					}
				} catch (UnknownHostException e) {
					e.printStackTrace();
				} catch (SocketException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
				totalScanned++;
			}
			elapsedTime = System.currentTimeMillis() - startTime;
			nextScene(result,stage);
		}
	}

	public void nextScene(int result, Stage oldStage) {
		try {

			FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
			Stage stage = new Stage();
			stage.setWidth(400.0);
			stage.setScene(new Scene((Parent) loader.load()));
			stage.show();
			stage.setResizable(false);
			stage.setTitle("IP Scanner");
			ReportController report = loader.getController();
			setAddress(report);
			setHost(report, result);
			setDuration(report);
			setTotalScan(report);
			sentStage(report, oldStage);
		} catch (IOException e) {
		}
	}

	public void setAddress(ReportController report) {
		report.setfromAddress(ipFrom.getText());
		report.setToAddress(ipTo.getText());
	}

	public void setHost(ReportController report, int hosts) {
		report.setHostAlive(hosts + "");
	}
	
	public void sentStage(ReportController report,Stage oldStage){
		report.setOldStage(oldStage);
	}

	public void setDuration(ReportController report) {
		double duration = elapsedTime / 1000.0;
		report.setTime(duration + "");
	}

	public void setTotalScan(ReportController report) {
		report.setTotal(totalScanned + "");
	}

}
