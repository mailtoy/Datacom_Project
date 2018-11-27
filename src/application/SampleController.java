package application;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.MissingFormatArgumentException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;

public class SampleController extends Window {

	@FXML
	private Label label1;
	@FXML
	private Label label2;
	@FXML
	private TextField ipFrom;
	@FXML
	private TextField ipTo;
	@FXML
	private TextField hostName;
	@FXML
	private Button btnUse;
	@FXML
	private ListView<String> add;
	@FXML
	private Label label3;
	@FXML
	private Button btnStart;
	private Thread sercher;
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

	public void Add(String m) {
		add.getItems().add(m);
	}
	
	@FXML
	public void StartClick(ActionEvent event) {
		startTime = System.currentTimeMillis();
		sercher = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					// IPAddress from = IPAddress.Parse( ipFrom.Text );
					try {
						InetAddress from = InetAddress.getByName(ipFrom.getText());
						InetAddress to = InetAddress.getByName(ipTo.getText());
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
				} catch (MissingFormatArgumentException fe) {

				}
			}
		});
		add.getItems().clear();
		sercher.setName("Network searching thread");
		sercher.start();
		add.getItems().add("-->> >>> Please wait while processing is done <<< <<--");
		Find();
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public void Find() {
		Add("-- >>> >> >>> Found station <<< << <<< -- ");
		int lastFrom = ipFrom.getText().lastIndexOf(".");
		int lastTo = ipTo.getText().lastIndexOf(".");
		String from = ipFrom.getText().substring(lastFrom + 1);
		String to = ipTo.getText().substring(lastTo + 1);

		int result = 0;
		for (int i = Integer.parseInt(from); i <= Integer.parseInt(to); i++) {
			long currentTime = System.currentTimeMillis();
			try {
				String address = ipTo.getText().substring(0, lastTo + 1);
//				System.out.println(ipTo.getText().substring(0, lastT + 1) + i);
				InetAddress ip = InetAddress.getByName(address + i);
				if (ip.isReachable(100)) { // Try for one tenth of a second
					currentTime = System.currentTimeMillis() - currentTime;
//					System.out.printf("Address %s is reachable\n", he);
					Add(ip.getHostName() + " Ping:" + currentTime/1000.0);
					result += 1;
				}
			} catch (Exception e) {}
		}
		Add("All done search retrieved " + result + " working stations.");
		elapsedTime = System.currentTimeMillis() - startTime;
		nextScene(result);
	}

	public void nextScene(int result) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Report.fxml"));
			Stage stage = new Stage();
			stage.setScene(new Scene((Parent) loader.load()));
			stage.show();
			stage.setResizable(false);
//			Stage ScanStage = (Stage) btnStart.getScene().getWindow();
			ReportController report = loader.getController();
			setAddress(report);
			setHost(report, result);
			setDuration(report);
			// ScanStage.close();
		} catch (IOException e) {}
	}
	
	public void setAddress(ReportController report) {
		report.setfromAddress(ipFrom.getText());
		report.setToAddress(ipTo.getText());
	}
	
	public void setHost(ReportController report, int hosts) {
		report.setHostAlive(hosts + "");
	}
	
	public void setDuration(ReportController report) {
		double duration = elapsedTime/1000.0;
		report.setTime(duration + "");
	}
}
