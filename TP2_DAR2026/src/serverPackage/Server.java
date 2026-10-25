
package serverPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

    public static void main(String[] args) {
        try {
            InetAddress adresse =
                InetAddress.getByName("192.168.31.142");

            // Créer le serveur sur l'adresse IP et le port 5000
            ServerSocket serverSocket = new ServerSocket();

            serverSocket.bind(
                new InetSocketAddress(adresse, 5000)
            );

            System.out.println("Serveur en attente...");
            System.out.println("Adresse IP : "
                + adresse.getHostAddress());
            System.out.println("Port : 5000");

            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecté");

            DataInputStream in =
                new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

            int choix;
            double x;
            double y;
            double resultat;

            while (true) {

                choix = in.readInt();

                if (choix == 0) {
                    System.out.println("Fin de communication");
                    break;
                }

                x = in.readDouble();
                y = in.readDouble();

                boolean succes = true;
                resultat = 0;

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
                            succes = false;
                        } else {
                            resultat = x / y;
                        }
                        break;

                    default:
                        succes = false;
                        break;
                }

                out.writeBoolean(succes);

                if (succes) {
                    out.writeDouble(resultat);

                    System.out.println("Premier nombre : " + x);
                    System.out.println("Deuxième nombre : " + y);
                    System.out.println("Résultat : " + resultat);
                } else {
                    out.writeUTF(
                        choix == 4 && y == 0
                        ? "Erreur : division par zéro."
                        : "Erreur : opération invalide."
                    );
                }

                out.flush();
            }

            in.close();
            out.close();
            socket.close();
            serverSocket.close();

            System.out.println("Serveur arrêté");

        } catch (IOException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
