package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Ricevi extends Thread
{
    Scanner input = new Scanner(System.in); //creo scanner
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
                do
                {
                    System.out.println("\nnon ci sono abbastanza client connessi, aspetta");
                }
                while(in.readLine().equals("c"));

                do
                {
                    switch (in.readLine()) 
                    {
                        case "i":
                        {
                            send();
                        }
                        break;
                        case "en":
                        {
                            System.out.println("\nnome già usato:\n");
                            send();
                        }
                        break;
                        case "a":
                        {
                            if(in.readLine().equals("@"))
                            {
                                System.out.println("\n@"+in.readLine()+" si è collegato");
                            }
                        }
                        break;
                        case "e":
                        {
                            System.out.println("\nERRORE!!!!");
                        }
                        break;
                        case "d":
                        {
                            System.out.println("\n@"+in.readLine()+" si è disconnesso");
                        }
                        case "o":
                        {
                            System.out.println("\ncosa vuoi fare?");
                            System.out.println("\n1-invia a un solo utente\n2-invia a tutti gli utenti\nd-disconnessione");
                            String selezione=input.nextLine();
                            while (selezione!="1"||selezione!="2"||selezione!="d") 
                            {
                                System.out.println("\nvalore non valido, reinserisci:");
                                selezione=input.nextLine();
                            }
                            send(selezione);
                            switch (selezione) 
                            {
                                case "1":
                                {
                                    System.out.println("\ninserisci nome: ");
                                    String nome=input.nextLine();
                                    System.out.println("\ninserisci messaggio: ");
                                    String mess=input.nextLine();
                                    send(mess);
                                    send(nome);
                                }
                                break;
                                case "2":
                                {
                                    System.out.println("\ninserisci nome: ");
                                    String nome=input.nextLine();
                                    System.out.println("\ninserisci messaggio: ");
                                    String mess=input.nextLine();
                                    send(nome);
                                    send(mess);
                                }
                                break;
                                case "d":
                                {
                                    send("d");
                                    System.out.println("\nti sei disconnesso");
                                }
                                break;
                            }
                        }
                        default:
                        {
                            System.out.println("\nselezione non valida");
                        }
                        break;
                    }
                }while (!in.readLine().equals("d"));

                //broadcast disconnasione
                send(this.getName());
                if(in.readLine().equals("d"))
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
    public void send()
    {
        System.out.println("inserisci il tuo nome: ");
        String nome = input.nextLine();
        Invia cout=new Invia(socket, nome);
        cout.start();
    }
    public void send(String x)
    {
        Invia cout=new Invia(socket, x);
        cout.start();
    }
}
