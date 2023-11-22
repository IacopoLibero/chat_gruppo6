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
  - socket : Socket 
  - thread : MioThread
  - clients :   ArrayList

*funzioni*
  - Invio singolo (mittente, destinatario)
  - Invio in broadcast (Mittente)

**MioThread**
  - input : BufferedReader
  - output : DataOutputStream
    
*funzioni*
  - run()


**Diagramma della Classi client**
// da fare



**Messaggi client** 
  - 1 : invio singolo
  - 2 : invio broadcast
  - d : disconnessione

**Messaggio server**
  - c : un solo client collegato
  - i : inserisci nome
  - e : errore   
  - a : notifica connesione nuovo client
  - o : opzioni
  - d : notifica disconnesione client 
  