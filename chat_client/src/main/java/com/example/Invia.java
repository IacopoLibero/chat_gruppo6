package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Invia extends Thread
{
    Socket socket;

    public Invia(Socket s)
    {
        this.socket = s;
    }

    public void run()
    {
        try 
        { 
            Scanner input = new Scanner(System.in); //creo scanner
            
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());//creo bufferedreader che invia al server
            out.writeBytes(getName()+'\n');
            
            input.close(); //chiudo scanner
        } 
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
}
