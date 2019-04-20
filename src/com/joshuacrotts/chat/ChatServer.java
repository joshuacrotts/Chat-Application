package com.joshuacrotts.chat;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author joshuacrotts
 */
public class ChatServer {
    private int port;
    private Set<String> userNames = new HashSet<String>();
    private Set<UserThread> userThreads = new HashSet<UserThread>();
    
    public ChatServer(int port)
    {
        this.port = port;
    }
    
    public void execute()
    {
        try
        {
            //Opens a new server on the specified port
            ServerSocket serverSocket = new ServerSocket(port);
            
            //Listens for newly-connecting users
            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("New user connected.");
                
                UserThread user = new UserThread(socket, this);
                userThreads.add(user);
                user.start();
            }
        } catch(IOException e)
        {
            System.err.println("Error in the server: "+e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Delivers a message from one user to all other users (excludes
     * the sender for obvious reasons).
     * 
     * @param message
     * @param excludeUser 
     */
    public void broadcast(String message, UserThread excludeUser)
    {
        for(UserThread user : userThreads)
        {
            if(user != excludeUser)
            {
                user.sendMessage(message);
            }
        }
    }
    
    /**
     * Adds a new user to the server. If they already exist,
     * they are not added, and false is returned.
     * @param userName
     * @return 
     */
    public boolean addUserName(String userName)
    {
        if(userNames.contains(userName))
        {
            System.err.println("Error: user is already on the server.");
            return false;
        }
        else
            userNames.add(userName);
        
        return true;
    }
    
    
    public boolean removeUser(String userName, UserThread user)
    {
        boolean removed = userNames.remove(userName);
        
        if(removed)
        {
            userThreads.remove(user);
            System.out.println(userName + " has left the chat.");
            return true;
        }
        
        return false;
    }
    
    public Set<String> getUsers()
    {
        if(this.hasUsers())
            return this.userNames;
        
        return null;
    }
    
    public boolean hasUsers()
    {
        return !this.userNames.isEmpty();
    }
    
}
