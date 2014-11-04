package client.socket;

import client.socket.SocketHelper;
import client.Client;


public class IncomingReader implements Runnable {

    public void run() {
        String[] data;
        String stream;
		String[] tmpProp;
        
        try {
        	String[] properties;
        	String sentMessage = "";
			String sentFrom = "";
			String messageType = "private";
			
            while ((stream = SocketHelper.reader.readLine()) != null) {
            	data = stream.split("=");
            	
            	System.out.println("Klient "+Client.nickName+" odbiera:"+stream);
            	
            	properties = stream.split("&");
				System.out.println("Odebrałem: "+stream);
				
				
				
				for (String property : properties) {
					
					
					tmpProp = property.split("=", 2);
					
					if (tmpProp[0].equals("connect")) {
						Client.addUser(tmpProp[1], true);
					}
					
					if (tmpProp[0].equals("disconnect")) {
						Client.removeUser(tmpProp[1]);
					}
					
					if (tmpProp[0].equals("messagetype")) {
						messageType = tmpProp[1];
					}
					
					if (tmpProp[0].equals("message")) {
						sentMessage = tmpProp[1];
					}
					
					if (tmpProp[0].equals("sendfrom")) {
						sentFrom = tmpProp[1];
					}
					
					if (tmpProp[0].equals("onlineusers")) {
						String[] onlineUsersArray = tmpProp[1].split(",");
						for (String one : onlineUsersArray) {
							Client.addUser(one, false);
						}
					}
					
				}
				System.out.println("wyświetlam");
	            Client.showMessage(sentMessage, messageType, sentFrom);
            }
            
            
       }catch(Exception ex) {
    	   
       }
       
    }
       
}
