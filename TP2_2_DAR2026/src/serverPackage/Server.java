
package serverPackage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;

import clientPackage.Operation;
import clientPackage.Resultat;

public class Server {

    public static void main(String[] args) {

        try {
            InetAddress adresse =
                InetAddress.getByName("192.168.31.142");

            ServerSocket serverSocket = new ServerSocket();

            serverSocket.bind(
                new InetSocketAddress(adresse, 5000)
            );

            System.out.println("Serveur en attente d'un client...");
            System.out.println("Adresse IP : "
                + adresse.getHostAddress());
            System.out.println("Port : 5000");

            Socket socket = serverSocket.accept();

            System.out.println("Un client est connecté.");

            ObjectOutputStream out =
                new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            ObjectInputStream in =
                new ObjectInputStream(socket.getInputStream());

            while (true) {

                Object objet = in.readObject();

                if (objet == null) {
                    break;
                }

                Operation operation = (Operation) objet;

                double x = operation.getX();
                double y = operation.getY();
                char operateur = operation.getOperateur();

                double resultat = 0;
                boolean succes = true;
                String message = "Calcul effectué";

                switch (operateur) {

                    case '+':
                        resultat = x + y;
                        break;

                    case '-':
                        resultat = x - y;
                        break;

                    case '*':
                        resultat = x * y;
                        break;

                    case '/':
                        if (y == 0) {
                            succes = false;
                            message = "Erreur : division par zéro.";
                        } else {
                            resultat = x / y;
                        }
                        break;

                    default:
                        succes = false;
                        message = "Erreur : opérateur invalide.";
                        break;
                }

                Resultat reponse =
                    new Resultat(succes, resultat, message);

                out.writeObject(reponse);
                out.flush();

                System.out.println(
                    x + " " + operateur + " " + y
                    + (succes ? " = " + resultat : " : " + message)
                );
            }

            in.close();
            out.close();
            socket.close();
            serverSocket.close();

            System.out.println("Serveur arrêté.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur serveur : " + e.getMessage());
        }
    }
}
