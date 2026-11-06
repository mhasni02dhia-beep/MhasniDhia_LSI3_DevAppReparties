package clientPackage;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public static void main(String[] args) {

        // Remplacer par l'IP réelle du serveur si nécessaire

        try {
            Socket socket = new Socket("192.168.31.142", 5000);

            System.out.println("Client connecté au serveur.");

            // Créer les flux d'objets
            ObjectOutputStream out =
                new ObjectOutputStream(socket.getOutputStream());
            out.flush();

            ObjectInputStream in =
                new ObjectInputStream(socket.getInputStream());

            Scanner scanner = new Scanner(System.in);

            Pattern pattern = Pattern.compile(
                "^\\s*(-?\\d+(?:[.,]\\d+)?)\\s*([+*/-])\\s*(-?\\d+(?:[.,]\\d+)?)\\s*$"
            );

            while (true) {

                System.out.print(
                    "Saisir une opération (ex. 25 + 55) ou 0 pour quitter : "
                );

                String expression = scanner.nextLine().trim();

                if (expression.equals("0")) {
                    break;
                }

                // Valider la syntaxe avant l'envoi
                Matcher matcher = pattern.matcher(expression);

                if (!matcher.matches()) {
                    System.out.println(
                        "Expression incorrecte. Exemple : 25 + 55"
                    );
                    continue;
                }

                double x;
                double y;

                try {
                    x = Double.parseDouble(
                        matcher.group(1).replace(',', '.')
                    );

                    y = Double.parseDouble(
                        matcher.group(3).replace(',', '.')
                    );
                } catch (NumberFormatException e) {
                    System.out.println("Nombre invalide.");
                    continue;
                }

                char operateur = matcher.group(2).charAt(0);

                // Créer l'objet à envoyer
                Operation operation =
                    new Operation(x, operateur, y);

                // Envoyer l'objet au serveur
                out.writeObject(operation);
                out.flush();

                // Recevoir l'objet résultat
                Resultat resultat = (Resultat) in.readObject();

                if (resultat.isSucces()) {
                    System.out.println(
                        "Résultat reçu : " + resultat.getValeur()
                    );
                } else {
                    System.out.println(resultat.getMessage());
                }
            }

            // Fermer la connexion
            out.close();
            in.close();
            socket.close();
            scanner.close();

            System.out.println("Connexion fermée.");

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}
