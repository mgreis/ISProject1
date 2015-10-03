
/**
 * JMS Training Part (doesn’t count for evaluation)
 *
 * 2. Assume now that the sender needs to receive a reply, but you do not want
 * to configure a dedicated queue for that. Which mechanism could you use? Write
 * the necessary code, sending a reply with a set of key-values. (HINT for the
 * sender: use the createTemporaryQueue() and a TextMessage in the JMSContext;
 * then, use the setJMSReplyTo() in the message).
 */
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Receiver {

    private final ConnectionFactory factory;
    private final Destination queue;

    public Receiver() throws NamingException {
        this.factory = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.queue = InitialContext.doLookup("jms/queue/PlayQueue");
    }

    private String receiveAndReply(String reply) throws JMSException {
        try (JMSContext context = factory.createContext("user", "pass")) {
            TextMessage message = (TextMessage) context.createConsumer(queue).receive();
            context.createProducer().send(message.getJMSReplyTo(), reply);
            return message.getText();
        }
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Receiver r = new Receiver();
        String msg = r.receiveAndReply("Hello Sender!");
        System.out.println("Message: " + msg);
    }

}
