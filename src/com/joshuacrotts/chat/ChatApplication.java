package com.joshuacrotts.chat;

/**
 * @author joshuacrotts
 */
public class ChatApplication {

    public static void main(String[] args) {
        if(args.length < 1)
        {
            System.err.println("Error: java ChatApplication <port>");
            System.exit(1);
        }
        
        int port = Integer.parseInt(args[0]);
        
        ChatServer server = new ChatServer(port);
        server.execute();
    }
    
}
