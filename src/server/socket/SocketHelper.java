package server.socket;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;



public class SocketHelper {
	//public static ArrayList clientOutputStreams;
    public static HashMap<String, ClientHandler> onlineUsers;
    
    public static void startServer() {
		//clientOutputStreams = new ArrayList();
		onlineUsers = new HashMap<String, ClientHandler>();  
		
		try {
			ServerSocket serverSocket = new ServerSocket(5000);
			System.out.println("Serwer zastartował, oczekiwanie na klientów");
			while (true) {
				Socket clientSocket = serverSocket.accept();
				System.out.println("Jest klient");
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());
				//clientOutputStreams.add(writer);
				
				Thread listener = new Thread(new ClientHandler(clientSocket, writer));
				listener.start();
			} // end while
			
		} catch (Exception ex) {
			System.err.println("Błąd podczas nawiązywania połączenia");
		}		
	}
    
    public static void userAdd(String userName, ClientHandler handler) {
		onlineUsers.put(userName, handler);
		System.out.println("Podłączył się "+userName);
	}
    
    public void userRemove(String userName) {
    	onlineUsers.remove(userName);		
	}
    
    public static void sendMessage(String userName, String sendFrom, String message) {
    	System.out.println("Wysyłam wiad. "+message+" do: "+userName);
    	if (userName.equals("_allusers")) {
    		
			Set keys = SocketHelper.onlineUsers.keySet();
			for (Iterator one = keys.iterator(); one.hasNext();) {
				PrintWriter writer = SocketHelper.onlineUsers.get(one.next()).writer;
				writer.println("messagetype=public&sendfrom="+sendFrom+"&"+message);
                writer.flush();
			}
    		
    	} else {
    		
    		if (userExists(userName)) {
    			PrintWriter writer = (PrintWriter) onlineUsers.get(userName).writer;
    			writer.println("messagetype=private&sendfrom="+sendFrom+"&"+message);
                writer.flush();
    		}
    		
    	}
    }
    
    public static boolean userExists(String username) {
    	if (onlineUsers.containsKey(username))
    		return true;
    	else
    		return false;
    }
    
}
