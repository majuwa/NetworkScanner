package majuwa.network.view;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.util.Observable;
import java.util.Observer;

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
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingWorker;

import majuwa.network.controller.MainController;
import majuwa.network.model.AddressContainer;
import majuwa.network.model.Configuration;
import majuwa.network.model.IPAddress;

public class MainWindow extends JFrame implements ActionListener, Observer {
	private static final long serialVersionUID = 1L;
	private MainController controller;
	private DefaultListModel defaultList;
	// Menu
	private JMenuBar menuBar;
	private JMenu menuMain;
	private JMenu menuAbout;
	private JMenuItem menuBtClose;
	private JMenuItem menuBtSave;
	private JMenuItem menuBtAbout;

	// TextPane
	private JTextField textFrom;
	private JTextField textTo;
	private JButton btScan;

	// ListPanel
	private JList list;

	private JProgressBar progress;

	public MainWindow(MainController controller) {
		super("Java Network-Scanner");
		this.controller = controller;
		defaultList = new DefaultListModel();
		this.initializeWindow();
		pack();
		setVisible(true);
	}

	private void initializeWindow() {
		// Layout
		this.setLayout(new BorderLayout());
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);

		// MainMenu

		menuMain = new JMenu("Main");
		menuBtSave = new JMenuItem("Save");
		menuBtSave.setAction(new MenuAction("Save", null,
				"Click to save output.", KeyStroke.getKeyStroke("control S")));
		menuBtSave.setAccelerator(KeyStroke.getKeyStroke("control S"));
		menuBtClose = new JMenuItem("Close");
		menuBtClose.setAction(new MenuAction("Close", null, "Click to close.",
				KeyStroke.getKeyStroke("controll Q")));
		menuBtClose.setAccelerator(KeyStroke.getKeyStroke("control Q"));
		menuMain.add(menuBtSave);
		menuMain.add(menuBtClose);

		// AboutMenu
		menuAbout = new JMenu("About");
		menuBtAbout = new JMenuItem("Save");
		menuBtAbout
				.setAction(new MenuAction("About", null, "Show Infos", null));
		menuAbout.add(menuBtAbout);

		// MenuBar
		menuBar = new JMenuBar();
		menuBar.add(menuMain);
		menuBar.add(menuAbout);
		this.setJMenuBar(menuBar);

		// AddressPane
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
		this.add(addressPane, BorderLayout.NORTH);

		// ListView
		list = new JList(defaultList);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setLayoutOrientation(JList.VERTICAL);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(200, 100));
		this.add(listScroller, BorderLayout.CENTER);
		
		progress = new JProgressBar();
		progress.setIndeterminate(true);
		progress.setVisible(false);
		this.add(progress,BorderLayout.SOUTH);

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
			if (arg0.getSource().equals(menuBtSave))
				JOptionPane.showMessageDialog(null, "Not Implemented");
			else if (arg0.getSource().equals(menuBtClose))
				System.exit(0);
			else if (arg0.getSource().equals(menuBtAbout))
				showLicence();

		}
	}

	private class Task extends SwingWorker<Void, Void> {

		@Override
		protected Void doInBackground() throws Exception {
			System.out.println("Test");
			controller.startScan(textFrom.getText(), textTo.getText());
			return null;
		}

		public void done() {
			setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			progress.setVisible(false);
			JOptionPane.showMessageDialog(null, "All IPs scanned","Finish",JOptionPane.INFORMATION_MESSAGE);
		}

	}

	private void getList() {

		for (IPAddress ip : AddressContainer.instance())
			defaultList.addElement(ip);
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		if (arg0.getSource().equals(btScan)) {
			if (true) {
				setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				progress.setVisible(true);
				Task task = new Task();
				task.execute();
			}
		}
	}
	private void showLicence() {
		String owner = "Copyright (C) 2014  majuwa";
		String licence = "This program is free software: you can redistribute it and/or modify \n"
				+ "it under the terms of the GNU General Public License as published by \n"
				+ "the Free Software Foundation, either version 3 of the License. \n"
				+ "\n"
				+ "This program is distributed in the hope that it will be useful, \n"
				+ "but WITHOUT ANY WARRANTY; without even the implied warranty of \n"
				+ "MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the \n"
				+ "GNU General Public License for more details. \n"
				+ " \n"
				+ "You should have received a copy of the GNU General Public License\n"
				+ "along with this program.  If not, see http://www.gnu.org/licenses/.\n";
		JOptionPane.showMessageDialog(this, Configuration.instance()
				.getAppName() + "\n" + owner + "\n" + licence,"Licence",JOptionPane.INFORMATION_MESSAGE);
	}

	@Override
	public void update(Observable o, Object arg) {
		// TODO Auto-generated method stub
		if (arg instanceof IPAddress) {
			IPAddress tmp = (IPAddress) arg;
			if (!defaultList.contains(tmp)) {
				if (tmp.getStatus()
						|| Configuration.instance().getShowAllHosts())
					defaultList.addElement(tmp);
			}
		}
	}

}
