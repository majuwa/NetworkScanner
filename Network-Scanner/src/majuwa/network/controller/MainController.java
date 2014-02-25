package majuwa.network.controller;

import java.io.IOException;
import java.net.UnknownHostException;

import majuwa.network.model.Calculate;
import majuwa.network.model.Scan;
import majuwa.network.view.MainWindow;

public class MainController {
	public MainController(){
		new MainWindow(this);
	}
	public void startScan(String from,String to) throws UnknownHostException, IOException{
		new Calculate(from, to);
		Scan s = new Scan();
		s.scan();
		
	}

}
