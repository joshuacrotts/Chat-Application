package com.joshuacrotts.chat;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * This class represents the chat client of the program.
 * 
 * @author joshuacrotts
 */
public class ChatClient {
    
    private String hostname;
    private int port;
    private String userName;
    
    public ChatClient(String hostname, int port)
    {
        this.hostname = hostname;
        this.port = port;
    }
    
    public void execute()
    {
        try
        {
            Socket socket = new Socket(hostname, port);
            System.out.println("Connected to the chat server!");
            
            new ReadThread(socket, this).start();
            new WriteThread(socket, this).start();
        }
        catch(UnknownHostException e)
        {
            System.err.println("Error: Server not found. " + e.getMessage());
        }
        catch(IOException e)
        {
            System.err.println("I/O Error: " + e.getMessage());
        }
    }
    
    public void setUserName(String usr)
    {
        this.userName = usr;
    }
    
    public String getUserName()
    {
        return this.userName;
    }
    
    public static void main(String[] args)
    {
        if(args.length < 2) return;
        
        
        String hostName = args[0];
        int port = Integer.parseInt(args[1]);
        
        ChatClient c = new ChatClient(hostName, port);
        c.execute();
    }
}
