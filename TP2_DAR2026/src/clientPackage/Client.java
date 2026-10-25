
package clientPackage;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Client {

    public static void main(String[] args) {
        System.out.println("Je suis un client pas encore connecté...");

        try {
            Socket socket = new Socket("192.168.31.142", 5000);

            System.out.println("Je suis un client connecté");

            DataInputStream in =
                new DataInputStream(socket.getInputStream());

            DataOutputStream out =
                new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            Pattern pattern = Pattern.compile(
                "^\\s*(-?\\d+(?:[.,]\\d+)?)\\s*([+*/-])\\s*(-?\\d+(?:[.,]\\d+)?)\\s*$"
            );

            while (true) {
                System.out.println(
                    "\nSaisir une opération (exemple : 25 + 55)"
                );
                System.out.println("Opérateurs : +  -  *  /");
                System.out.println("Saisir 0 pour quitter");
                System.out.print("Expression : ");

                String expression = scanner.nextLine().trim();

                if (expression.equals("0")) {
                    out.writeInt(0);
                    out.flush();
                    break;
                }

                Matcher matcher = pattern.matcher(expression);

                if (!matcher.matches()) {
                    System.out.println(
                        "Syntaxe incorrecte ! Exemple valide : 25 + 55"
                    );
                    System.out.println(
                        "Aucune donnée n'a été envoyée au serveur."
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

                // Identifier l'opération
                String operateur = matcher.group(2);
                int choix;

                switch (operateur) {
                    case "+":
                        choix = 1;
                        break;
                    case "-":
                        choix = 2;
                        break;
                    case "*":
                        choix = 3;
                        break;
                    case "/":
                        choix = 4;
                        break;
                    default:
                        System.out.println("Opérateur invalide.");
                        continue;
                }

                // Envoyer uniquement une expression valide
                out.writeInt(choix);
                out.writeDouble(x);
                out.writeDouble(y);
                out.flush();

                // Recevoir la réponse du serveur
                boolean succes = in.readBoolean();

                if (succes) {
                    double resultat = in.readDouble();
                    System.out.println("Résultat reçu : " + resultat);
                } else {
                    System.out.println(in.readUTF());
                }
            }

            scanner.close();
            in.close();
            out.close();
            socket.close();

            System.out.println("Connexion fermée");

        } catch (IOException e) {
            System.out.println("Erreur client : " + e.getMessage());
        }
    }
}
