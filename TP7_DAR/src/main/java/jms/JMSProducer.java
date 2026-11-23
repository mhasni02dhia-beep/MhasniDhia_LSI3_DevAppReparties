package jms;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

// 3. Importer la classe ActiveMQConnectionFactory
import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * Producteur JMS : publie le message "Hello World! From: [NomDeLExpéditeur]"
 * sur le Topic "DTopic".
 */
public class JMSProducer {

    // Nom de l'expéditeur : à remplacer par votre nom et prénom
    private static final String NOM_EXPEDITEUR = "Yassine";

    public static void main(String[] args) {
        try {
            // 2. Créer un objet ConnectionFactory
            // 4. Configurer la ConnectionFactory avec l'URL du broker :
            //    protocole TCP + adresse IP de la machine du broker + port 61616
            //    (remplacer localhost par l'adresse IP si le broker est sur une autre machine)
            ConnectionFactory connectionFactory =
                    new ActiveMQConnectionFactory("tcp://localhost:61616");

            // 5. Créer une Connection (createConnection) puis la démarrer (start)
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // 6. Créer une Session :
            //    - 1er paramètre true : session transactionnelle
            //    - 2e paramètre : accusé de réception AUTO_ACKNOWLEDGE
            Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);

            // 7. Créer un objet Topic pour définir la destination du message
            Destination destination = session.createTopic("DTopic");

            // 8. Créer un MessageProducer à partir de la session sur la destination
            MessageProducer producer = session.createProducer(destination);
            //    Le message doit être non persistant
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // 9. Créer le message "Hello World! From: [NomDeLExpéditeur]" (TextMessage)
            String text = "Hello World! From: " + NOM_EXPEDITEUR;
            TextMessage message = session.createTextMessage(text);

            // Envoi du message vers le broker (sans attendre de réponse)
            System.out.println("Sent message: " + message.hashCode()
                    + " : " + Thread.currentThread().getName());
            producer.send(message);

            // Session transactionnelle : l'envoi n'est effectif qu'après commit()
            session.commit();

            // 10. Fermer la session
            session.close();

            // 11. Fermer la connexion
            connection.close();

        } catch (Exception e) {
            System.out.println("Caught: " + e);
            e.printStackTrace();
        }
    }
}
