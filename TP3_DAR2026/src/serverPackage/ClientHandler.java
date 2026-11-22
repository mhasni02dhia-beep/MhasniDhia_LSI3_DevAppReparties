
package serverPackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientHandler implements Runnable {

    private Socket socket;
    private int numero;

    public ClientHandler(Socket socket, int numero) {
        this.socket = socket;
        this.numero = numero;
    }

    @Override
    public void run() {

        try {

            PrintWriter sortie = new PrintWriter(
                    socket.getOutputStream(), true);

            sortie.println("Vous etes le client numero : " + numero);

            System.out.println(
                    "Client " + numero + " traité par "
                    + Thread.currentThread().getName());

            socket.close();

        } catch (IOException e) {

            System.out.println("Erreur : " + e.getMessage());
        }
    }
}
