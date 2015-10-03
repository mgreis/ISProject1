
/**
 * JMS Training Part (doesn’t count for evaluation)
 *
 * 4. In the previous code, what happens to the messages that arrive at the
 * topic, before the subscriber actually makes the subscription? Assume now,
 * that a client subscribes a topic, leaves and then subscribes again. We want
 * to know what happens to the messages that enter the topic, while this client
 * is out. Will it receive the messages or not? To ensure that the client
 * receives these messages, which changes do you need to do? Write a new client
 * with these properties and try the code to see if it works. You should check
 * this message:
 * http://eai-course.blogspot.pt/2012/09/a-few-variations-over-jms.html
 */
import javax.jms.DeliveryMode;
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
            session.createPublisher(topic).publish(message, DeliveryMode.NON_PERSISTENT, 4/* default priority level */, 60 * 1000/* TTL=1 minute */);
        }
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Sender s = new Sender();
        s.send("Hello Receiver!");
    }
}
