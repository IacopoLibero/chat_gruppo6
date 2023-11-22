package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Ricevi extends Thread
{
    Socket socket;
    protected boolean exit = false;

    public Ricevi(Socket socket)
    {
        this.socket = socket;
    }

    public void run()
    {
        try 
        {
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //creo bufferedreader che riceve dal server
            while(!exit)
            {
                String messaggio = in.readLine(); //leggo messaggio
                System.out.println(messaggio); //stampo messaggio
                
                if(messaggio.equals("d")) //se il messaggio è exit
                {
                    System.out.println("Il server ha chiuso la connessione"); //stampo che il server ha chiuso la connessione
                    stopThread();
                }
            }
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
    public void stopThread()
    {
        exit = true;
    }
}
