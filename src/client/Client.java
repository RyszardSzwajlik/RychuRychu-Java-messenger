package client;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.ConnectException;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import client.socket.SocketHelper;
import client.view.MainFrame;

public class Client {
	
	public static String nickName, host, sendTo;
	
	private static MainFrame view;
	
	private static SocketHelper socket;

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		view = new MainFrame();
		
		setListeners();
		//SocketHelper socket = new SocketHelper("localhost");
	}
	
	private static void setListeners() {
		//Przycisk połącz
		view.connectButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				nickName = view.nickTextField.getText();
				host = view.adresTextField.getText();
				sendTo = "_allusers";
				
				socket = new SocketHelper(host);
				socket.sendMessage("connect="+nickName, "SERVER", "_allusers");
				MainFrame.chatListModel.addElement("Czat ogólny");
				
			}
			
		});
		
		//Przycisk rozłącz
		view.disconnectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				socket.sendMessage("disconnect="+nickName, "SERVER", "_allusers");
				MainFrame.usersListModel.clear();
				MainFrame.chatListModel.clear();
				MainFrame.addLine("Rozłączyłeś się!");
			}
		});
		
		//Przycisk wyślij
		view.sendMessageButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String message = MainFrame.newMessageTextField.getText();
				socket.sendMessage("message="+message, nickName, sendTo);
				if (sendTo!="_allusers") {
					socket.sendMessage("message="+message, nickName, nickName);
					MainFrame.newMessageTextField.setText("");
				}
			}
		});
		
		//Lista użytkowników
		view.usersListList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Object selected = view.usersListModel.get(view.usersListList.getSelectedIndex());
				if (!view.chatListModel.contains(selected)) {
					view.chatListModel.addElement(selected);
				}				
			}

			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
		});
		
		//Lista czatów
		view.chatListList.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				Object selected = view.chatListModel.get(view.chatListList.getSelectedIndex());
				sendTo = (String) selected;
				if (sendTo.equals("Czat ogólny")) {
					sendTo = "_allusers";
					view.addLine("Teraz piszesz do wszystkich użytkowników czatu");
				} else {
					view.addLine("Teraz piszesz do "+sendTo);
				}
			}

			public void mouseEntered(MouseEvent e) {}
			public void mouseExited(MouseEvent e) {}
			public void mousePressed(MouseEvent e) {}
			public void mouseReleased(MouseEvent e) {}
			
		});
	}
	
	public static void addUser(String userName, boolean show) {
		
		if (show)
			MainFrame.addLine("Przychodzi użytkownik: "+userName);
		
		MainFrame.usersListModel.addElement(userName);
		
	}
	
	public static void removeUser(String userName) {
		MainFrame.addLine("Odchodzi użytkownik: "+userName);
		MainFrame.usersListModel.removeElement(userName);
	}
	
	public static void showMessage(String message, String messageType, String author) {
		if (!author.equals("SERVER")) {
			String toView = "\nWiadomość";
			if (messageType.equals("private")) {
				toView += " prywatna";
			} else {
				toView += " publiczna";
			}
			
			toView += " od: "+author+":\n";
			
			toView += message+"\n";
			
			MainFrame.addLine(toView);		
		}
	}

}
