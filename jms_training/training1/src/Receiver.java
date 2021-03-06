
/**
 * JMS Training Part (doesn’t count for evaluation)
 *
 * 1. Run the example available at:
 * http://eai-course.blogspot.pt/2015/03/java-message-service-20-with-wildfly-8.html
 */
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSRuntimeException;
import javax.naming.InitialContext;
import javax.naming.NamingException;

public class Receiver {

    private ConnectionFactory cf;
    private Destination d;

    public Receiver() throws NamingException {
        this.cf = InitialContext.doLookup("jms/RemoteConnectionFactory");
        this.d = InitialContext.doLookup("jms/queue/PlayQueue");
    }

    private String receive() {
        String msg = null;
        try (JMSContext jcontex = cf.createContext("user", "pass");) {
            JMSConsumer mc = jcontex.createConsumer(d);
            msg = mc.receiveBody(String.class);
        } catch (JMSRuntimeException re) {
            re.printStackTrace();
        }
        return msg;
    }

    public static void main(String[] args) throws NamingException {
        Receiver r = new Receiver();

        String msg = r.receive();
        System.out.println("Message: " + msg);
    }

}
