package com.joshuacrotts.chat;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/** This UserThread class reads in all messages sent from 
 *  client to client, and broadcasts messages to other users.
 *
 * @author joshuacrotts
 */
public class UserThread extends Thread{
    
    private Socket socket;
    private ChatServer server;
    private PrintWriter writer;
    
    public UserThread(Socket socket, ChatServer server)
    {
        this.socket = socket;
        this.server = server;
    }
    
    @Override
    public void run()
    {
        try
        {
            InputStream input = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            
            OutputStream output = socket.getOutputStream();
            writer = new PrintWriter(output, true);
            
            this.printUsers();
            
            String userName = reader.readLine();
            server.addUserName(userName);
            
            String serverMsg = "New uer connected: " + userName;
            server.broadcast(serverMsg, this);
            
            String clientMsg;
            
            do 
            {
                
                clientMsg = reader.readLine();
                serverMsg = "[" + userName + "]: "+clientMsg;
                server.broadcast(serverMsg, this);
                
            } while(!clientMsg.equalsIgnoreCase("Q"));
            
            server.removeUser(userName, this);
            socket.close();
            
            serverMsg = userName + " has quit the chat.";
            server.broadcast(serverMsg, this);
            
        }catch(Exception e)
        {
            System.err.println("Error in UserThread: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    private void printUsers()
    {
        if(server.hasUsers())
        {
            this.writer.println("Connected users: "+server.getUsers());
        }
        else
        {
            this.writer.println("No other users are connected.");
        }
    }
    
    public void sendMessage(String msg)
    {
        this.writer.println(msg);
    }
    
}
