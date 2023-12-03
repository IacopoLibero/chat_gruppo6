package com.example;
import java.net.Socket;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            Socket socket = new Socket("localhost", 4567); //creo il socket e lo connetto al server
            Ricevi cin=new Ricevi(socket);
            cin.start();
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
}
