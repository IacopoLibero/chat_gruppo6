# chat_gruppo6

**Tecnologie utilizzate**
  - Socket
  - Thread
  - GIT
  - Maven
  - java


**casi d'uso del client**
 - Connessione client: Mi connetto al server
 - Disconnessione client: Avverto il server della disconnessione
 - Invio messaggio / ricezione messaggio
 - chiudere connessione
 - richiesta contatti

**Casi d'uso del server**
  - Connessione server: Avverto connessione stabilita
  - Disconnessione di un client: Avverto tutti i client della disconnessione di uno di loro
  - Invio in broadcast: invio a tutti i client
  - invio singolo: invio ad un determinato client
  - chiudere connessione
  - invia lista contatti


**Diagramma delle Classi server**
<br>

**App**
  - server : ServerSocket
  - s : Socket
  - m : MioThreadServer
  - clients :   ArrayList<>

*funzioni*
  - isName (String nome)

**MioThread**
  - input : BufferedReader
  - output : DataOutputStream
  - s : socket
  - cl : ArrayList<'MioThreadServer'>

*funzioni*
  - run()
  - broadcast (Integer num, String message)
  - onlyone (String name, String message)
  - printList()
  - removeClient(String name)


**Diagramma della Classi client**
<br>

**App**
  - socket : Socket
  - reader : BufferedReader
  - outputStream : DataOutputStream
  - in : Scanner
  - recieve : Ricevi

*funzioni*
  - sendMessages(Socket socket)

**Ricevi**
  - in : Scanner
  - socket : Socket
  - reader : BufferedReader

*funzioni*
  - run()



**Messaggi client**
  - 1 : invio broadcast
  - 2 : invio singolo 
  - l : richiesta lista contatti
  - d : disconnessione

**Messaggio server**
  - "il contatto non esiste"
  - "disconnessione" 
  - "azione non valida" => selezione sbagliata
  - nomeThread+ (privato):+ messaggio => messaggio singolo
  - [nomeThread]+ha scritto+messaggio => messaggio broadcast
  - "Ecco la lista dei contatti:+ cl