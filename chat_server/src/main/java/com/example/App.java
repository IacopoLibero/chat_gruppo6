package com.example;

import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class App 
{
    public static void main(String[] args) 
    {

        try 
        {
            System.out.println("Server in avvio!");
            ServerSocket server = new ServerSocket(4567); //crea socket su cui ricevere
            ArrayList clients=new ArrayList<MioThreadServer>();
            do 
            {
                Socket s = server.accept(); //accetta connessione
                MioThreadServer m = new MioThreadServer(s,clients); //crea thread
                clients.add(m);
                m.start(); //start processo

            } 
            while (true);
        } 
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }   
}
