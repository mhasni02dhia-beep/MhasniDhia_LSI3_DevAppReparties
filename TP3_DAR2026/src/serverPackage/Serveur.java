
package serverPackage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;

public class Serveur {

    private static final int PORT = 1234;

    private static AtomicInteger compteur = new AtomicInteger(0);

    public static void main(String[] args) {

        try (ServerSocket serveur = new ServerSocket(PORT)) {

            System.out.println("Serveur démarré sur le port " + PORT);

            while (true) {

                Socket socket = serveur.accept();

                int numero = compteur.incrementAndGet();

                System.out.println("Client numéro : " + numero);
                System.out.println("Adresse IP : "
                        + socket.getRemoteSocketAddress());

                ClientHandler handler =
                        new ClientHandler(socket, numero);

                Thread thread = new Thread(handler);
                thread.start();
            }

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
