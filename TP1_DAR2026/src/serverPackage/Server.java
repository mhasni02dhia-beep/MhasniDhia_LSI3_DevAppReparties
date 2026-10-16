
package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            // Première étape : créer le serveur
            ServerSocket serverSocket = new ServerSocket(5000);

            System.out.println(
                "Je suis un serveur en attente de la connexion d'un client"
            );

            // Deuxième étape : attendre la connexion
            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecté");

            // Création des flux de communication
            DataInputStream in =
                new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

            // Recevoir les entiers envoyés par le client
            int x;

            while (true) {
                x = in.readInt();

                // Si le client saisit 0, arrêter la communication
                if (x == 0) {
                    System.out.println("Fin de communication");
                    break;
                }

                // Calcul côté serveur
                int resultat = x * 5;

                // Envoyer le résultat au client
                out.writeInt(resultat);
                out.flush();

                System.out.println("Entier reçu : " + x);
                System.out.println("Résultat : " + resultat);
            }

            // Fermer les flux et les sockets
            in.close();
            out.close();
            socket.close();
            serverSocket.close();

            System.out.println("Serveur arrêté");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
