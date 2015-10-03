
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

public class Sender {

    private final TopicConnectionFactory factory;
    private final Topic topic;

    public Sender() throws NamingException {
        this.factory = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.topic = InitialContext.doLookup("jms/topic/PlayTopic");
    }

    private void send(String text) throws JMSException {
        try (TopicConnection connection = factory.createTopicConnection("user", "pass")) {
            TopicSession session = connection.createTopicSession(false/* not a transacted session */, TopicSession.AUTO_ACKNOWLEDGE);
            TextMessage message = session.createTextMessage();
            message.setText(text);
            connection.start();
            session.createPublisher(topic).publish(message);
        }
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Sender s = new Sender();
        s.send("Hello Receiver!");
    }
}
