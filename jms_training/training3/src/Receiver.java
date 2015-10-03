
/**
 * JMS Training Part (doesn’t count for evaluation)
 *
 * 3. Write code that sends text messages to multiple subscribers at once.
 */
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Receiver {

    private final TopicConnectionFactory factory;
    private final Topic topic;

    public Receiver() throws NamingException {
        this.factory = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.topic = InitialContext.doLookup("jms/topic/PlayTopic");
    }

    private String receive() throws JMSException {
        try (TopicConnection connection = factory.createTopicConnection("user", "pass")) {
            TopicSession session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            connection.start();
            TextMessage message = (TextMessage) session.createSubscriber(topic).receive();
            return message.getText();
        }
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Receiver r = new Receiver();
        String msg = r.receive();
        System.out.println("Message: " + msg);
    }

}
