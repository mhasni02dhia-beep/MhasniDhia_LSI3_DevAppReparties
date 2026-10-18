
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

            int choix;
            int x;
            int y;
            int resultat;

            while (true) {

                // Recevoir le choix de l'opération
                choix = in.readInt();

                if (choix == 0) {
                    System.out.println("Fin de communication");
                    break;
                }

                // Recevoir les deux entiers
                x = in.readInt();
                y = in.readInt();

                // Calcul côté serveur
                switch (choix) {

                    case 1:
                        resultat = x + y;
                        break;

                    case 2:
                        resultat = x - y;
                        break;

                    case 3:
                        resultat = x * y;
                        break;

                    case 4:
                        if (y == 0) {
                            System.out.println(
                                "Erreur : division par zéro"
                            );
                            resultat = 0;
                        } else {
                            resultat = x / y;
                        }
                        break;

                    default:
                        resultat = 0;
                        break;
                }

                // Envoyer le résultat au client
                out.writeInt(resultat);
                out.flush();

                System.out.println("Premier entier reçu : " + x);
                System.out.println("Deuxième entier reçu : " + y);
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
