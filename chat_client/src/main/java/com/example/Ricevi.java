package com.example;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
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
                // while(in.readLine().equals("c"))
                // {
                //     System.out.println("\nnon ci sono abbastanza client connessi, aspetta");
                // }

                String action;

                do
                {
                    action = in.readLine();
                    switch (action)
                    {
                        case "i":
                        {
                            send();
                        }
                        break;
                        case "en":
                        {
                            System.out.println("\nnome già usato:\n");
                        }
                        break;
                        case "a":
                        {
                            String m = in.readLine();

                            if(m.equals("@"))
                            {
                                m = in.readLine();
                                System.out.println("\n@"+m+" si è collegato");
                            }
                        }
                        break;
                        case "e":
                        {
                            System.out.println("\nERRORE!!!!");
                        }
                        break;
                        case "c":
                        {
                            System.out.println("Ecco la lista dei contatti: \n");
                            String contatti = in.readLine();
                            System.out.println(contatti);
                        }
                        break;
                        case "d":
                        {
                            System.out.println("\n@"+in.readLine()+" si è disconnesso");
                        }
                        break;
                        case "o":
                        {
                            String selezione;
                            do{
                                System.out.println("\ncosa vuoi fare?");
                                System.out.println("\n1-invia a un solo utente\n2-invia a tutti gli utenti\nd-disconnessione");
                                selezione = input.nextLine();
                                // send(selezione);

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
                                    default:
                                    {
                                        System.out.println("Valore non valido, riprova");
                                    }
                                }
                            }while(!selezione.equals("d"));
                            action = "d";
                        }
                        default:
                        {
                            System.out.println("\nselezione non valida");
                        }
                        break;
                    }
                }while (!action.equals("d"));

                System.out.println("Sono disconnesso");

                // //broadcast disconnasione
                // send(this.getName());
                // if(in.readLine().equals("d"))
                // {
                //     System.out.println("Il server ha chiuso la connessione"); //stampo che il server ha chiuso la connessione
                //     stopThread();
                // }
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