package application;

import java.awt.Container;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.MissingFormatArgumentException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
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

	private Container components = null;

	@FXML
	public void initialize() {
		LoadMe();
	}

	void LoadMe() {
		try {
			hostName.setText(InetAddress.getLocalHost().getHostName());
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	public void Add(String m) {
		add.getItems().add(m);
	}

//	private void UseClick(ActionEvent event)
//   {
//     //When pressing the use use button
//   
//     InetAddress ip = InetAddress.getByName( InetAddress.getLocalHost().getHostName());
//     
//     ipFrom = ip.getAddress();
//     ipTo = ip.AddressList[0].ToString();
//
//   }

	Thread sercher;

	@FXML
	public void StartClick(ActionEvent event) {
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

	void Find() {
		Add("-- >>> >> >>> Found station <<< << <<< -- ");
		int lastF = ipFrom.getText().lastIndexOf(".");
		int lastT = ipTo.getText().lastIndexOf(".");
		String frm = ipFrom.getText().substring(lastF + 1);
		String tto = ipTo.getText().substring(lastT + 1);
		
		int result = 0;

		for (int i = Integer.parseInt(frm); i <= Integer.parseInt(tto); i++) {
			try {
				String address = ipTo.getText().substring(0, lastT + 1);
				System.out.println(ipTo.getText().substring(0, lastT + 1) + i);
				InetAddress he = InetAddress.getByName(address + i);

		        if (he.isReachable(100)) { // Try for one tenth of a second
		            System.out.printf("Address %s is reachable\n", he);
		            Add(he.getHostName());
					result += 1;	
		        }
			}
			catch (Exception e) {
			}
		}
		 Add("All done search retrieved "+ result +" working stations.");
	}
}
