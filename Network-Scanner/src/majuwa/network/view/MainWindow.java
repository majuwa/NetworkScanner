package majuwa.network.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;

import javax.swing.AbstractAction;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.ProgressMonitor;
import javax.swing.SwingWorker;

import majuwa.network.controller.MainController;
import majuwa.network.model.AddressContainer;
import majuwa.network.model.IPAddress;

public class MainWindow extends JFrame implements ActionListener{
	private static final long serialVersionUID = 1L;
	private MainController controller;
	private DefaultListModel defaultList;
	// Menu
	private JMenuBar menuBar;
	private JMenu menuMain;
	private JMenuItem menuClose;
	private JMenuItem menuSave;

	// TextPane
	private JTextField textFrom;
	private JTextField textTo;
	private JButton btScan;
	
	//ListPanel
	private JList list;
	
	
	private ProgressMonitor progress;
	
	
	public MainWindow(MainController controller) {
		super("Java Network-Scanner");
		this.controller = controller;
		defaultList= new DefaultListModel();
		this.initializeWindow();
		pack();
		setVisible(true);
	}

	private void initializeWindow() {
		//Layout
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		//Menu
		menuBar = new JMenuBar();
		menuMain = new JMenu("Main");
		menuSave = new JMenuItem("Save");
		menuSave.setAction(new MenuAction("Save", null,
				"Click to save output.", KeyStroke.getKeyStroke("control S")));
		menuSave.setAccelerator(KeyStroke.getKeyStroke("control S"));
		menuClose = new JMenuItem("Close");
		menuClose.setAction(new MenuAction("Close",null,"Click to close.",KeyStroke.getKeyStroke("controll Q")));
		menuClose.setAccelerator(KeyStroke.getKeyStroke("controll Q"));
		menuMain.add(menuSave);
		menuMain.add(menuClose);
		menuBar.add(menuMain);
		this.setJMenuBar(menuBar);
		
		
		//AddressPane
		JPanel addressPane = new JPanel();
		addressPane.setLayout(new FlowLayout());
		JLabel lText = new JLabel("Range from");
		textFrom = new JTextField(30);
		JLabel rText = new JLabel("to");
		textTo = new JTextField(30);
		btScan = new JButton("Scan");
		btScan.addActionListener(this);
		addressPane.add(lText);
		addressPane.add(textFrom);
		addressPane.add(rText);
		addressPane.add(textTo);
		addressPane.add(btScan);
		this.add(addressPane,BorderLayout.NORTH);
		
		//ListView
		this.getList();
		list = new JList(defaultList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(200,100));
		this.add(listScroller,BorderLayout.CENTER);
		
		
	}

	private class MenuAction extends AbstractAction {
		public MenuAction(String title, ImageIcon image, String toolTipText,
				KeyStroke acceleratorKey) {
			super(title, image);
			putValue(SHORT_DESCRIPTION, toolTipText);
			putValue(SHORT_DESCRIPTION, toolTipText);
			putValue(ACCELERATOR_KEY, acceleratorKey);
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			if(arg0.equals(menuSave))
				JOptionPane.showMessageDialog(null, "Not Implemented");
			else if(arg0.equals(menuClose))
				System.exit(0);

		}
	}
	private class Task extends SwingWorker<Void,Void>{

		@Override
		protected Void doInBackground() throws Exception {
			controller.startScan(textFrom.getText(), textTo.getText());
			return null;
		}
		public void done(){
			getList();
		}
		
	}
	private void getList(){
		
		for(IPAddress ip :AddressContainer.instance())
			defaultList.addElement(ip);
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if(arg0.getSource().equals(btScan)){
			if(true){
				int length = 100;
				Task task = new Task();
				task.execute();
				this.progress = new ProgressMonitor(this,"Checking hosts","",0,length);
				progress.setProgress(0);
			}
		}
	}
	public void propertyChange(PropertyChangeEvent evt){
		if(evt.getPropertyName().equals("progress")){
			
		}
	}
	

}
