package com.example;

import java.io.DataOutputStream;
import java.net.Socket;

public class Invia extends Thread
{
    Socket socket;
    String mess;

    public Invia(Socket s, String mess)
    {
        this.socket = s;
        this.mess = mess;
    }

    public void run()
    {
        send(mess);
    }

    public void send(String mess)
    {
        try
        {
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());//creo bufferedreader che invia al server
            out.writeBytes(mess+'\n');
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
        this.interrupt();
    }
}
