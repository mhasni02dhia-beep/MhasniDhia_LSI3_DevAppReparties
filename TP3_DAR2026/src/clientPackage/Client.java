
package clientPackage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {

        String adresseIP = "localhost";
        int port = 1234;

        try {

            Socket socket = new Socket();

            InetAddress adresse =
                    InetAddress.getByName(adresseIP);

            InetSocketAddress destination =
                    new InetSocketAddress(adresse, port);

            socket.connect(destination);

            System.out.println("Connexion réussie !");

            BufferedReader entree = new BufferedReader(
                    new InputStreamReader(
                            socket.getInputStream()));

            String message = entree.readLine();

            System.out.println("Serveur : " + message);

            socket.close();

        } catch (IOException e) {

            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
