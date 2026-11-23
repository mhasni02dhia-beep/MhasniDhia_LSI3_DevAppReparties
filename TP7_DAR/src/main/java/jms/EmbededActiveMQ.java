package jms;

import org.apache.activemq.broker.BrokerService;

/**
 * Démarrer ActiveMQ à partir d'une application Java : Embeded ActiveMQ.
 *
 * Broker embarqué : le broker tourne dans la JVM de l'application,
 * sans installation séparée d'ActiveMQ.
 */
public class EmbededActiveMQ {

    public static void main(String[] args) {
        try {
            // a. Créer un objet BrokerService pour représenter le broker ActiveMQ
            BrokerService broker = new BrokerService();

            // Configuration du broker : pas de stockage sur disque (KahaDB non nécessaire pour le TP)
            broker.setPersistent(false);

            // b. Ajouter un connecteur TCP : 0.0.0.0 = écoute sur toutes les interfaces réseau,
            //    port 61616 (protocole OpenWire utilisé par les clients JMS)
            broker.addConnector("tcp://0.0.0.0:61616");

            // c. Démarrer le broker en appelant la méthode start()
            broker.start();
            System.out.println("Broker ActiveMQ démarré sur tcp://0.0.0.0:61616");

            // Garder le broker actif tant que l'application n'est pas arrêtée
            broker.waitUntilStopped();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
