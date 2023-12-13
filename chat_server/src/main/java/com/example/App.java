package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class App {
    /**
     * Avvia il server di chat.
     *
     * @param args gli argomenti della riga di comando
     */
    public static void main(String[] args) {
        try {
            System.out.println("Server in avvio!");
            ServerSocket server = new ServerSocket(4567); // crea socket su cui ricevere
            ArrayList<MioThreadServer> clients = new ArrayList<>();

            do {
                Socket s = server.accept(); // accetta connessione
                BufferedReader input = new BufferedReader(new InputStreamReader(s.getInputStream()));
                DataOutputStream output = new DataOutputStream(s.getOutputStream());
                MioThreadServer m = new MioThreadServer(s, clients); // crea thread
                String name;

                do {
                    name = input.readLine();
                    if (isName(name, clients)) {
                        output.writeBytes("n\n");
                    }
                } while (isName(name, clients) == true);
                output.writeBytes("y\n");
                m.setName(name);
                clients.add(m);

                m.start(); // start processo

            } while (true);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("errore durante l'istanza del server");
            System.exit(1);
        }
    }

    /**
     * Controlla se il nome utente è già presente nella lista dei client connessi.
     *
     * @param name    il nome utente da controllare
     * @param clients la lista dei client connessi
     * @return true se il nome utente è già presente, false altrimenti
     */
    public static boolean isName(String name, ArrayList<MioThreadServer> clients) {
        for (MioThreadServer client : clients) {
            if (client.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
