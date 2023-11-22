package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;

public class MioThreadServer extends Thread
{

    Socket s;
    ArrayList cl;

    public MioThreadServer(Socket s,ArrayList cl)
    {
        this.s = s;
        this.cl=cl;
    }

    public void run()
    {
        try 
        {
            System.out.println("Un client si è collegato");

            

            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream())); //istanza per ricevere dati dal client
            DataOutputStream output = new DataOutputStream(s.getOutputStream()); //istanza per inviare dati al client
            String nome = input.readLine(); //riceve dati
            System.out.println(nome);
            for (Object object : cl) {
                
            }
            //output.writeBytes("1\n"); //invia dati
            
            s.close(); //chiude socket
        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
    public void broadcast()
}
