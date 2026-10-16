
package clientPackage;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        System.out.println("Je suis un client pas encore connecté...");

        try {
            // Première étape : se connecter au serveur
            Socket socket = new Socket("192.168.1.184", 5000);

            System.out.println("Je suis un client connecté");

            // Dernière étape : fermer la connexion
            socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
