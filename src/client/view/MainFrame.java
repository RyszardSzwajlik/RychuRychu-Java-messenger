package client.view;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;

public class MainFrame extends JFrame {
	
	public static JTextField adresTextField, nickTextField, newMessageTextField;
	public static JButton connectButton, disconnectButton, sendMessageButton;
	public static JTextArea console;
	public static JList usersListList, chatListList;
	public static DefaultListModel usersListModel, chatListModel;
	
	public static void addComponentsToPane(Container pane) {

		JButton button;
        pane.add(generateConnectionPanel(), BorderLayout.PAGE_START);
         
        //generateConsolePanel
        console = new JTextArea("Witaj w Rychu Rychu!");
        console.setPreferredSize(new Dimension(400, 400));
        console.setEditable(false);
        console.setBackground(Color.getHSBColor(206, 9, 246));
        pane.add(console, BorderLayout.CENTER);
         
        pane.add(generateUsersPanel(), BorderLayout.LINE_START);
         
        pane.add(generateSendingPanel(), BorderLayout.PAGE_END);
         
        pane.add(generateChatsPanel(), BorderLayout.LINE_END);
    }
	
	public MainFrame() {
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//this.setSize(640, 480);
		this.setTitle("Rychu Rychu - klient");
		
		addComponentsToPane(this.getContentPane());
		
		this.pack();
		this.setLocationRelativeTo(null);
		
		this.setVisible(true);
	}
	
	private static JPanel generateConnectionPanel() {
		
		JPanel connectionPanel = new JPanel();
		
		connectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
		connectionPanel.setBackground(Color.ORANGE);
		
		connectionPanel.add(new JLabel("Twój nick:"));
		
		nickTextField = new JTextField();
		nickTextField.setPreferredSize(new Dimension(150, 20));
		
		connectionPanel.add(nickTextField);
		
		connectionPanel.add(new JLabel("Adres serwera:"));
		
		adresTextField = new JTextField();
		adresTextField.setPreferredSize(new Dimension(150, 20));
		
		connectButton = new JButton("Połącz");
		disconnectButton = new JButton("Rozłącz");
		
		adresTextField.setText("localhost");
		
		connectionPanel.add(adresTextField);
		connectionPanel.add(connectButton);
		connectionPanel.add(disconnectButton);
		
		return connectionPanel;
	}
	
	private static JPanel generateSendingPanel() {
		
		JPanel connectionPanel = new JPanel();
		
		connectionPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
		
		
//		connectionPanel.setBackground(Color.ORANGE);
		
		connectionPanel.add(new JLabel("Treść wiadomości:"));
		
		newMessageTextField = new JTextField();
		sendMessageButton = new JButton("Wyślij");

		newMessageTextField.setPreferredSize(new Dimension(550, 20));
		
		connectionPanel.add(newMessageTextField);
		connectionPanel.add(sendMessageButton);
		
		return connectionPanel;
	}
	
	private static JPanel generateUsersPanel() {
		
		JPanel connectionPanel = new JPanel();
		
		connectionPanel.setLayout(new FlowLayout());

		connectionPanel.setPreferredSize(new Dimension(200, 1));
		
		connectionPanel.setBackground(Color.getHSBColor(206, 10, 246));
		
		usersListModel = new DefaultListModel();
		
		usersListList = new JList(usersListModel);
		usersListList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		usersListList.setPreferredSize(new Dimension(180, 300));
		
		
		connectionPanel.add(new JLabel("Lista użytkowników:"));
		connectionPanel.add(usersListList);
		return connectionPanel;
	}
	
private static JPanel generateChatsPanel() {
		
		JPanel connectionPanel = new JPanel();
		
		connectionPanel.setLayout(new FlowLayout());
		
		connectionPanel.setPreferredSize(new Dimension(200, 1));
		
		connectionPanel.setBackground(Color.getHSBColor(206, 10, 246));
		
		connectionPanel.add(new JLabel("Lista czatów:"));
		

		String[] items = {"Czat ogólny"};
		
		chatListModel = new DefaultListModel();
		
		chatListList = new JList(chatListModel);
		chatListList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		chatListList.setPreferredSize(new Dimension(180, 300));
		
		connectionPanel.add(chatListList);
		
		return connectionPanel;
	}

	public static void addLine(String tekst) {
		console.setText(console.getText()+"\n"+tekst);
	}
	
}
