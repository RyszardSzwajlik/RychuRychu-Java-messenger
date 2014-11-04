package server.socket;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Iterator;
import java.util.Set;

public class ClientHandler implements Runnable {
	
	BufferedReader reader;
	Socket socket;
	PrintWriter writer;
	
	String messageText, sendTo, sendFrom;

	
	public ClientHandler(Socket clientSocket, PrintWriter user) {
	// new inputStreamReader and then add it to a BufferedReader
		writer = user;
		try {
			socket = clientSocket;
			InputStreamReader isReader = new InputStreamReader(socket.getInputStream());
			reader = new BufferedReader(isReader);
		} // end try
		catch (Exception ex) {
			System.err.println("Error beginning StreamReader. \n");
		} // end catch

	} // end ClientHandler()

	public void run() {
		String message;
		String[] properties;
		String[] tmpProp;

		try {
			while ((message = reader.readLine()) != null) {

				properties = message.split("&");
				System.out.println("Odebrałem: "+message);
				
				for (String property : properties) {
					
					
					tmpProp = property.split("=", 2);
					
					if (tmpProp[0].equals("connect")) {
						
						String usersList = "";
						Set keys = SocketHelper.onlineUsers.keySet();
						for (Iterator one = keys.iterator(); one.hasNext();) {
							usersList += ","+(String)one.next();
						}
						
						SocketHelper.userAdd(tmpProp[1], this);

						SocketHelper.sendMessage(tmpProp[1], "SERVER", "onlineusers="+usersList);
						
						sendTo = "_allusers";
						sendFrom = "SERVER";
						messageText = message;
						
					}
					
					if (tmpProp[0].equals("disconnect")) {
						
						SocketHelper.onlineUsers.remove(tmpProp[1]);
						
						sendTo = "_allusers";
						sendFrom= "SERVER";
						messageText = "disconnect="+tmpProp[1];
					}
					
					if (tmpProp[0].equals("sendfrom")) {
						sendFrom = tmpProp[1];
					}
					
					if (tmpProp[0].equals("sendto")) {
						sendTo = tmpProp[1];
					}
					
					if (tmpProp[0].equals("message")) {
						messageText = "message="+tmpProp[1];
					}
					
				}
				
				SocketHelper.sendMessage(sendTo, sendFrom, messageText);
				
		     } // end while
		} // end try
		catch (Exception ex) {
			System.err.println("Lost a connection. \n");
            ex.printStackTrace();
		} // end catch
	} // end run()
	
	
}
