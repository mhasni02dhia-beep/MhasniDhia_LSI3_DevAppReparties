package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Consommateur JMS : s'abonne au Topic "DTopic" et reçoit les messages
 * de manière asynchrone grâce à un MessageListener.
 */
public class JMSConsumer {

    public static void main(String[] args) throws Exception {
        // 5. Créer un objet ConnectionFactory avec l'URL tcp://localhost:61616
        //    pour établir la connexion au serveur JMS
        ConnectionFactory connectionFactory =
                new ActiveMQConnectionFactory("tcp://localhost:61616");

        // 6. Créer une connexion à partir de la ConnectionFactory
        Connection connection = connectionFactory.createConnection();

        // 7. Créer une session : contexte de création des producteurs et consommateurs
        //    (non transactionnelle, accusé de réception automatique)
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

        // 8. Créer la destination : un Topic (même nom que celui du producteur)
        Destination destination = session.createTopic("DTopic");

        // 9. Créer un MessageConsumer associant la session et la destination
        MessageConsumer consumer = session.createConsumer(destination);

        // 10. Démarrer la connexion : sans start(), aucun message n'est délivré
        connection.start();

        // 11. Mettre en place un JMS Listener pour la réception asynchrone des messages :
        //     onMessage() est appelée par JMS dès qu'un message est disponible
        consumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                try {
                    if (message instanceof TextMessage) {
                        TextMessage textMessage = (TextMessage) message;
                        String text = textMessage.getText();
                        System.out.println("Received: " + text);
                    } else {
                        System.out.println("Received: " + message);
                    }
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            }
        });

        System.out.println("Consumer en attente de messages sur le Topic DTopic...");
        // Le programme reste en attente (le listener s'exécute dans son propre thread)
    }
}
