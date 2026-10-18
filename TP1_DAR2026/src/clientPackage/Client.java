
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
            Socket socket = new Socket("192.168.1.184", 5000);

            System.out.println("Je suis un client connecté");

            // Création des flux de communication
            DataInputStream in =
                new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            int choix;
            int x;
            int y;

            do {
                System.out.println("\n--- CALCULATRICE ---");
                System.out.println("1. Addition");
                System.out.println("2. Soustraction");
                System.out.println("3. Multiplication");
                System.out.println("4. Division");
                System.out.println("0. Quitter");
                System.out.print("Votre choix : ");

                choix = scanner.nextInt();

                // Envoyer le choix au serveur
                out.writeInt(choix);
                out.flush();

                if (choix == 0) {
                    break;
                }

                if (choix < 1 || choix > 4) {
                    System.out.println("Choix invalide");
                    continue;
                }

                System.out.print("Saisir le premier entier : ");
                x = scanner.nextInt();

                System.out.print("Saisir le deuxième entier : ");
                y = scanner.nextInt();

                // Envoyer les deux entiers au serveur
                out.writeInt(x);
                out.writeInt(y);
                out.flush();

                // Recevoir le résultat
                int resultat = in.readInt();

                System.out.println("Résultat reçu : " + resultat);

            } while (choix != 0);

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
