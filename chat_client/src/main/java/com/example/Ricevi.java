package com.example;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.ArrayList;

public class Ricevi extends Thread
{
    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())); //creo bufferedreader che riceve dal server
}
