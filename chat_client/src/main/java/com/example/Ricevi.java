package com.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Questa classe rappresenta un thread per la ricezione dei messaggi dal server.
 */
public class Ricevi extends Thread {
    Scanner in = new Scanner(System.in); // Creo un oggetto Scanner per leggere l'input da tastiera
    Socket socket;

    /**
     * Costruttore della classe Ricevi.
     * @param socket il socket per la connessione al server
     */
    public Ricevi(Socket socket) {
        this.socket = socket;
    }

    /**
     * Metodo che viene eseguito quando il thread viene avviato.
     */
    public void run() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String message;

            // Legge i messaggi inviati dal server e li stampa sulla console
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}