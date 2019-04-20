package com.joshuacrotts.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;

/**
 *
 * @author joshuacrotts
 */
public class ReadThread extends Thread{
    
    private BufferedReader reader;
    private Socket socket;
    private ChatClient client;
    
    public ReadThread(Socket socket, ChatClient client)
    {
        this.socket = socket;
        this.client = client;
        
        try
        {
            InputStream input = socket.getInputStream();
            this.reader = new BufferedReader(new InputStreamReader(input));
        }
        catch(IOException e)
        {
            System.err.println("Error with retrieving input stream: "+e.getMessage());
            e.printStackTrace();
            
        }
    }
    
    @Override
    public void run()
    {
        while(true)
        {
            try
            {
                String response = this.reader.readLine();
                System.out.println("\n" + response);
                
                if(client.getUserName() != null)
                {
                    System.out.println("[" + client.getUserName() + "]");
                }
            }
            catch(IOException e)
            {
                System.err.println("Error reading from the server: " + e.getMessage());
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
    
}
