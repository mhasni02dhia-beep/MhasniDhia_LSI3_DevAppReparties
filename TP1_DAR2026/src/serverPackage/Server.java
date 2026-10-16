
package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            // Première étape : créer le serveur
            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println(
                "Je suis un serveur en attente la connexion d'un client"
            );

            // Deuxième étape : attendre la connexion
            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecté");

            // Dernière étape : fermer les sockets
            socket.close();
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
