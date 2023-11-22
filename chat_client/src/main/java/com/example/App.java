package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class App 
{
    public static void main(String[] args) 
    {
        try 
        { 
            
            Socket socket = new Socket("localhost", 4567); //creo il socket e lo connetto al server
            Invia cout=new Invia(socket);
            //crea ricevi
            cout.start();
            
        } 
        catch (Exception e)
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }  
}
