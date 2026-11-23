
package clientPackage;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

import calculatricePackage.Operation;
import calculatricePackage.Resultat;

public class Client {

    public static void main(String[] args) {

        // Adresse du serveur
        String adresseIP = "localhost";

        // Port du serveur
        int port = 1234;

        // Possibilité de donner l'IP en argument
        if (args.length > 0) {
            adresseIP = args[0];
        }

        try (
            Socket socket = new Socket(adresseIP, port);

            Scanner clavier = new Scanner(System.in)
        ) {

            System.out.println("==============================");
            System.out.println(" CLIENT CALCULATRICE ");
            System.out.println("==============================");

            System.out.println("Connexion réussie !");
            System.out.println("Serveur : " + adresseIP);
            System.out.println("Port : " + port);

            // Créer le flux de sortie en premier
            ObjectOutputStream sortie =
                new ObjectOutputStream(socket.getOutputStream());

            sortie.flush();

            // Créer le flux d'entrée
            ObjectInputStream entree =
                new ObjectInputStream(socket.getInputStream());

            boolean continuer = true;

            while (continuer) {

                System.out.println();
                System.out.println("----- Nouvelle opération -----");

                System.out.print("Donner le premier nombre : ");
                double x = clavier.nextDouble();

                System.out.print("Donner l'opérateur (+, -, *, /) : ");
                char operateur = clavier.next().charAt(0);

                System.out.print("Donner le deuxième nombre : ");
                double y = clavier.nextDouble();

                // Créer l'objet Operation
                Operation operation =
                    new Operation(x, operateur, y);

                // Envoyer l'opération au serveur
                sortie.writeObject(operation);
                sortie.flush();

                // Recevoir le résultat
                Resultat resultat =
                    (Resultat) entree.readObject();

                System.out.println();
                System.out.println("----- Résultat -----");

                if (resultat.isSucces()) {

                    System.out.println(
                        "Résultat : " + resultat.getValeur()
                    );

                } else {

                    System.out.println(
                        "Erreur : " + resultat.getMessage()
                    );
                }

                System.out.print(
                    "Voulez-vous effectuer une autre opération ? (o/n) : "
                );

                String reponse = clavier.next();

                if (!reponse.equalsIgnoreCase("o")) {
                    continuer = false;
                }
            }

            // Envoyer null pour signaler la déconnexion
            sortie.writeObject(null);
            sortie.flush();

            System.out.println();
            System.out.println("Déconnexion du serveur.");

            // Fermer les flux
            entree.close();
            sortie.close();

        } catch (IOException | ClassNotFoundException e) {

            System.out.println(
                "Erreur client : " + e.getMessage()
            );
        }
    }
}
