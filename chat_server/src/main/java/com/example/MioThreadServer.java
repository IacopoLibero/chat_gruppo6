package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class MioThreadServer extends Thread
{

    Socket s;
    ArrayList<MioThreadServer> cl;

    public MioThreadServer(Socket s,ArrayList<MioThreadServer> cl)
    {
        this.s = s;
        this.cl=cl;
        
    }

    public void run()
    {
        try 
        {
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream())); //istanza per ricevere dati dal client
            DataOutputStream output = new DataOutputStream(s.getOutputStream()); //istanza per inviare dati al client
            while (cl.size()<=2) 
            {
                output.writeBytes("c\n");
                System.out.println("1");
                this.wait(5000);
            }  
            do
            {
                output.writeBytes("i\n"); //inserisci nome
                String nome = input.readLine(); //riceve dati
                
                if(isName(nome) == false)
                {
                    cl.get(cl.size()-1).setName(nome);
                    output.writeBytes("a\n"); //connessione effettuata
                    collegamento(nome);
                    break;
                }
                else
                {
                    output.writeBytes("en\n"); //refused
                }
            }while(true);

            do
            {
                output.writeBytes("o\n");
            
                switch (input.readLine()) 
                {
                    case "1":
                    {
                        String nome =input.readLine();
                        String mess = input.readLine();
                        onlyone(mess,nome);
                    }
                    break;
                    case "2":
                    {
                        String nome =input.readLine();
                        String mess = input.readLine();
                        broadcast(nome, mess);
                    }
                    break;
                    case "d":
                    {
                        String nome=input.readLine();
                        broadcast(nome, "d\n");
                        elimina(nome);
                        output.writeBytes("d\n");

                    }
                    default:
                    {
                        output.writeBytes("e\n");
                    }
                    break;
                }
            }while (!input.readLine().equals("d"));

        }
        catch (Exception e) 
        {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }
    
    public void broadcast(String nameT, String message) 
    {
        for (Object client : cl) 
        {
            try 
            {
                Socket clientSocket = (Socket) client;
                DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                clientOutput.writeBytes( "&\n");
                clientOutput.writeBytes( nameT+"\n");
                clientOutput.writeBytes( message+"\n");
            } 
            catch (Exception e) 
            {
                System.out.println("Errore durante l'invio del messaggio al client");
            }
        }
    }


    public void onlyone(String message,String nome) 
    {
        try 
        {
            for (int i = 0; i < cl.size(); i++) 
            {
                if(cl.get(i).getName().equals(nome))
                {
                    Socket clientSocket = (Socket) cl.get(i).s;
                    DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                    clientOutput.writeBytes( "&\n");
                    clientOutput.writeBytes( nome+"\n");
                    clientOutput.writeBytes( message+"\n");
                }
            }
            
        } 
        catch (Exception e) 
        {
            System.out.println("Errore durante l'invio del messaggio al client");
        }
    }

    public void collegamento(String nome)
    {
        for (Object client : cl) 
        {
            try 
            {
                Socket clientSocket = (Socket) client;
                DataOutputStream clientOutput = new DataOutputStream(clientSocket.getOutputStream());
                clientOutput.writeBytes( "@\n");
                clientOutput.writeBytes( nome+"\n");
            } 
            catch (Exception e) 
            {
                System.out.println("Errore durante l'invio del messaggio al client");
            }
        }
    }

    public boolean isName(String nome) 
    {
        boolean is = false;
        for (int i = 0; i < cl.size(); i++) 
        {
            //se è presente un nome uguale
            if(cl.get(i).getName().equals(nome))
            {   
                return true;
            }
        }
        return false;
    }

    public void elimina(String nomeThread) 
    {
        for (int i = 0; i < cl.size(); i++) {

            MioThreadServer thread = cl.get(i);
            if (thread.getName().equals(nomeThread)) 
            {
                cl.remove(i);
                break;
            }
        }
    }
}