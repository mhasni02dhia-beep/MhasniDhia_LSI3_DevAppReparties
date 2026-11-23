
package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Serveur {

    private static final int PORT = 1234;

    // Compteur des clients connectés
    private static final AtomicInteger compteurClients =
        new AtomicInteger(0);

    // Compteur global des opérations calculées
    private static int totalOperations = 0;

    // Méthode synchronisée pour protéger
    // l'accès concurrent au compteur
    public static synchronized int incrementerCompteur() {

        totalOperations++;

        return totalOperations;
    }

    public static void main(String[] args) {

        try (ServerSocket serveur = new ServerSocket(PORT)) {

            System.out.println("==============================");
            System.out.println(" SERVEUR CALCULATRICE DEMARRE ");
            System.out.println("==============================");
            System.out.println("Port : " + PORT);
            System.out.println("En attente des clients...");
            System.out.println();

            while (true) {

                // Attendre une nouvelle connexion
                Socket socket = serveur.accept();

                // Attribuer un numéro au client
                int numero = compteurClients.incrementAndGet();

                System.out.println(
                    "Nouveau client numéro : " + numero
                );

                System.out.println(
                    "Adresse IP : " + socket.getRemoteSocketAddress()
                );

                // Créer un gestionnaire pour ce client
                ClientHandler handler =
                    new ClientHandler(socket, numero);

                // Créer et démarrer son thread
                Thread thread = new Thread(handler);

                thread.start();
            }

        } catch (IOException e) {

            System.out.println(
                "Erreur serveur : " + e.getMessage()
            );
        }
    }
}
