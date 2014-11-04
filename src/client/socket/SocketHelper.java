package client.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class SocketHelper {
	public final int PORT = 5000;
	public Socket clientSocket;
	public static BufferedReader reader;
    public static PrintWriter writer;
    public static boolean isConnected = false;
    
    public void sendMessage(String msg, String from, String to) {
    	if (isConnected) {
    		writer.println("sendfrom="+from+"&sendto="+to+"&"+msg);
    		writer.flush();
    	}
	}
	
	public SocketHelper(String host) {
		
		try {
			try {
				
				isConnected = true;
				clientSocket = new Socket(host, PORT);
				
			} catch (java.net.ConnectException e) {
				
				isConnected = false;
				
			} finally {
				
				
			}
			InputStreamReader streamreader = new InputStreamReader(clientSocket.getInputStream());
            reader = new BufferedReader(streamreader);
            writer = new PrintWriter(clientSocket.getOutputStream());

    		listenThread();
			
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void listenThread() {
		Thread IncomingReader = new Thread(new IncomingReader());
		IncomingReader.start();
	}
}
