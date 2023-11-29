# chat_gruppo6

**Tecnologie utilizzate**
  - Socket
  - Thread


**casi d'uso del client**
 - Connessione client: Mi connetto al server
 - Disconnessione client: Avverto il server della disconnessione
 - Invio messaggio / ricezione messaggio

**Casi d'uso del server**
  - Connessione server: Avverto connessione stabilita
  - Disconnessione di un client: Avverto tutti i client della disconnessione di uno di loro
  - Invio in broadcast: invio a tutti i client
  - invio singolo: invio ad un determinato client


**Diagramma delle Classi server**
<br>

**App**
  - server : ServerSocket  
  - s : Socket 
  - m : MioThreadServer
  - clients :   ArrayList<'MioThreadServer'>

**MioThread**
  - input : BufferedReader
  - output : DataOutputStream
  - s : socket
  - cl : ArrayList<'MioThreadServer'>
    
*funzioni*
  - run()
  - broadcast (String nameT, String message)
  - onlyone (String message, String nome)
  - collegamento (String nome)
  - isName (String nome)
  - elimina (String nomeThread)


**Diagramma della Classi client**
<br>

**App**
  - socket : Socket 
  - cin : Ricevi

**Invia**
  - out : DataOutputStream
  - socket : Socket
  - mess : String
    
*funzioni*
  - run()
  - send(String mess)

**Ricevi**
  - input : Scanner
  - socket : Socket
  - exit : boolean
  - in : BufferedReader
  - cout : Invia
    
*funzioni*
  - run()
  - stopThread()
  - send()
  - send(String x)



**Messaggi client** 
  - 1 : invio singolo
  - 2 : invio broadcast
  - d : disconnessione

**Messaggio server**
  - c : un solo client collegato
  - i : inserisci nome
  - e : errore   
  - @ : notifica connesione nuovo client
  - o : opzioni
  - d : notifica disconnesione client 
  - & : invio messaggio da altri client
  