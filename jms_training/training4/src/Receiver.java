
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
            connection.setClientID("training4");
            TopicSession session = connection.createTopicSession(false, TopicSession.AUTO_ACKNOWLEDGE);
            connection.start();
            // @xxx tive de adicionar no ficheiro de configuração:
            // <permission type="createDurableQueue" roles="guest"/>
            // <permission type="deleteDurableQueue" roles="guest"/>
            TextMessage message = (TextMessage) session.createDurableSubscriber(topic, "Receiver").receive();
            return message.getText();
        }
    }

    public static void main(String[] args) throws NamingException, JMSException {
        Receiver r = new Receiver();
        String msg = r.receive();
        System.out.println("Message: " + msg);
    }

}
