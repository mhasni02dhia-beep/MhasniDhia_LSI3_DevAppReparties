
package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) {
        System.out.println("Je suis un client pas encore connecté...");

        try {
            // Première étape : se connecter au serveur
            Socket socket = new Socket("localhost", 5000);

            System.out.println("Je suis un client connecté");

            // Création des flux de communication
            DataInputStream in =
                new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            int x;

            // Saisie successive des entiers
            do {
                System.out.print("Saisir un entier (0 pour quitter) : ");
                x = scanner.nextInt();

                // Envoyer l'entier au serveur
                out.writeInt(x);
                out.flush();

                if (x != 0) {
                    // Recevoir et afficher le résultat
                    int resultat = in.readInt();

                    System.out.println("Résultat reçu : " + resultat);
                }

            } while (x != 0);

            // Fermer la connexion
            scanner.close();
            in.close();
            out.close();
            socket.close();

            System.out.println("Connexion fermée");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
