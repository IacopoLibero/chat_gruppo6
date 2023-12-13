package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Questa classe rappresenta un thread del server di chat.
 * Ogni istanza di questa classe gestisce la comunicazione con un singolo client.
 */
public class MioThreadServer extends Thread {

    Socket s;
    ArrayList<MioThreadServer> cl;

    /**
     * Costruttore della classe MioThreadServer.
     * @param s il socket associato al client
     * @param cl la lista dei thread del server
     */
    public MioThreadServer(Socket s, ArrayList<MioThreadServer> cl) {
        this.s = s;
        this.cl = cl;
    }

    /**
     * Metodo che viene eseguito quando il thread viene avviato.
     * Gestisce la comunicazione con il client e le azioni richieste.
     */
    public void run() {
        try {
            BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream())); // istanza per ricevere dati dal client
            DataOutputStream output = new DataOutputStream(s.getOutputStream()); // istanza per inviare dati al client

            broadcast(1, " si e' collegato"); // invia un messaggio di connessione a tutti i client

            String action;

            do {
                action = input.readLine(); // legge l'azione inviata dal client

                switch (action) {
                    case "1":
                        String message = input.readLine(); // legge il messaggio inviato dal client
                        broadcast(0, message); // invia il messaggio a tutti i client
                        break;
                    case "2":
                        String[] sendMessage = input.readLine().split(":"); // legge il messaggio e il destinatario inviati dal client
                        if (onlyOne(sendMessage[0], sendMessage[1]) == false) { // verifica se il destinatario esiste
                            output.writeBytes("Il contatto non esiste\n"); // invia un messaggio di errore al client
                        }
                        break;
                    case "l":
                        output.writeBytes(printList() + "\n"); // invia la lista dei contatti al client
                        break;
                    case "d":
                        output.writeBytes("Disconnessione\n"); // invia un messaggio di disconnessione al client
                        removeClient(this.getName()); // rimuove il client dalla lista dei client
                        this.s.close(); // chiude la connessione con il client
                        break;
                    default:
                        System.out.println("Azione non valida\n"); // stampa un messaggio di errore per un'azione non valida
                        break;
                }
            } while (this.s.isClosed() == false); // continua finché la connessione con il client è aperta
        } catch (Exception e) {
            System.out.println(e.getMessage()); // stampa l'eccezione in caso di errore
        }
    }

    /**
     * Verifica se esiste un solo destinatario nella lista dei client e invia un messaggio privato a quel destinatario.
     * @param name il nome del destinatario
     * @param message il messaggio da inviare
     * @return true se il destinatario esiste, false altrimenti
     */
    public boolean onlyOne(String name, String message) {
        for (MioThreadServer client : cl) {
            if (client.getName().equals(name)) { // verifica se il destinatario esiste nella lista dei client
                try {
                    DataOutputStream clientOutputStream = new DataOutputStream(client.s.getOutputStream());
                    clientOutputStream.writeBytes(this.getName() + " (privato): " + message + "\n"); // invia un messaggio privato al destinatario
                } catch (Exception e) {
                    System.out.println(e.getMessage()); // stampa l'eccezione in caso di errore
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Invia un messaggio a tutti i client tranne al mittente.
     * @param num il numero identificativo del messaggio
     * @param message il messaggio da inviare
     */
    public void broadcast(Integer num, String message) {
        String send = "";
        if (num == 0) {
            send = "[" + this.getName() + "]" + " ha scritto: " + message; // formatta il messaggio per la trasmissione a tutti i client
        } else {
            send = "[" + this.getName() + "]" + message; // formatta il messaggio per la trasmissione a tutti i client
        }
        for (MioThreadServer client : cl) {
            if (!client.s.equals(this.s)) { // verifica se il client corrente è diverso dal client a cui inviare il messaggio
                try {
                    DataOutputStream clientOutputStream = new DataOutputStream(client.s.getOutputStream());
                    clientOutputStream.writeBytes(send + "\n"); // invia il messaggio a tutti i client tranne al mittente
                } catch (IOException e) {
                    e.printStackTrace(); // stampa l'eccezione in caso di errore
                }
            }
        }
    }

    /**
     * Restituisce la lista dei contatti registrati.
     * @return la lista dei contatti registrati
     */
    public String printList() {
        if (cl.size() == 1) {
            return "Non ci sono contatti registrati"; // restituisce un messaggio se non ci sono contatti registrati
        } else {
            String contatti = "Ecco la lista dei contatti:\n[\n";
            for (MioThreadServer client : cl) {
                if (!client.getName().equals(this.getName())) {
                    contatti += "   - " + client.getName() + "\n"; // formatta la lista dei contatti
                }
            }
            contatti += "]";

            return contatti;
        }
    }

    /**
     * Rimuove un client dalla lista dei client.
     * @param name il nome del client da rimuovere
     */
    public void removeClient(String name) {
        for (MioThreadServer client : cl) {
            if (client.getName().equals(name)) { // verifica se il client corrente è quello da rimuovere
                cl.remove(client); // rimuove il client dalla lista dei client
                break;
            }
        }
    }
}