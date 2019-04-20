package com.joshuacrotts.chat;

import java.io.Console;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

/** This class is responsible for reading in user input
 *  and sending it to the server.
 * 
 *  Runs forever until the user types "Q".
 *
 * @author joshuacrotts
 */
public class WriteThread extends Thread{
    private PrintWriter writer;
    private Socket socket;
    private ChatClient client;
    
    public WriteThread(Socket socket, ChatClient c)
    {
        this.socket = socket;
        this.client = c;
        
        try
        {
            OutputStream output = this.socket.getOutputStream();
            this.writer = new PrintWriter(output, true);
        }
        catch(IOException e)
        {
            System.err.println("Error retrieving output stream: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    @Override
    public void run()
    {
        Console console = System.console();
        
        String userName = console.readLine("\nEnter your name: ");
        this.client.setUserName(userName);
        this.writer.println(userName);
        
        String text;
        
        do 
        {
            text = console.readLine("[" + userName + "]");
            this.writer.println(text);
        } while(!text.equals("Q"));
        
        try
        {
            this.socket.close();
        }
        catch(IOException e)
        {
            System.err.println("Error writing to the server: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    
}
