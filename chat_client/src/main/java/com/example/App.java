package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

/**
 * Questa classe rappresenta l'applicazione client per il chatroom.
 * Si connette al server, permette all'utente di inserire il proprio nome,
 * inviare messaggi in broadcast o privati, richiedere la lista dei contatti
 * e disconnettersi dal server.
 */
public class App {
    /**
     * Il metodo principale che avvia l'applicazione client.
     *
     * @param args gli argomenti della riga di comando
     */
    public static void main(String[] args) {
        try {
            // Creazione del socket per la connessione al server
            Socket socket = new Socket("localhost", 4567);
            Scanner in = new Scanner(System.in);
            BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
            String read;

            // Inserimento del nome dell'utente
            do {
                System.out.println("Inserisci il tuo nome:");
                String nome = in.nextLine();
                outputStream.writeBytes(nome + "\n");
                read = reader.readLine();

                if (read.equals("n")) {
                    System.out.println("Nome non disponibile, riprova\n");
                }
            } while (read.equals("n"));

            // Avvio del thread per la ricezione dei messaggi
            Ricevi recieve = new Ricevi(socket);
            recieve.start();

            // Invio dei messaggi
            sendMessages(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Metodo per l'invio dei messaggi.
     *
     * @param socket il socket per la connessione al server
     */
    public static void sendMessages(Socket socket) {
        try {
            Scanner in = new Scanner(System.in);
            DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());

            while (true) {
                System.out.println("\nInserisci 1 per mandare in broadcast\nInserisci 2 per mandare ad una sola persona\nInserisci l per la lista dei contatti\nd per disconnetterti");

                String messageToSend = in.nextLine();

                // Invio di un messaggio in broadcast
                if (messageToSend.equals("1")) {
                    outputStream.writeBytes("1\n");
                    System.out.println("Inserisci il messaggio che vuoi inviare a tutti");
                    String message = in.nextLine();
                    outputStream.writeBytes(message + "\n");
                }
                // Invio di un messaggio privato
                else if (messageToSend.equals("2")) {
                    outputStream.writeBytes("2\n");
                    System.out.println("Inserisci il nome della persona a cui vuoi inviare un messaggio");
                    String nome = in.nextLine();
                    System.out.println("Ora scrivi il messaggio che vuoi inviare");
                    String messaString = in.nextLine();
                    outputStream.writeBytes(nome + ":" + messaString + "\n");
                }
                // Richiesta della lista dei contatti
                else if (messageToSend.equals("l")) {
                    outputStream.writeBytes("l\n");
                    try {
                        socket.wait(1000);
                    } catch (Exception e) {
                        System.out.println(e.getMessage());
                    }
                }
                // Disconnessione dal server
                else if (messageToSend.equals("d")) {
                    outputStream.writeBytes("d\n");
                    break;
                }
            }

            in.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}