package server;

import java.util.Scanner;

import javax.swing.JFrame;

import server.socket.SocketHelper;

public class Server {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("Aplikacja serwera");
		SocketHelper.startServer();
	}

}
