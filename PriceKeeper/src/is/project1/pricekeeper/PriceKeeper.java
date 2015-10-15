package is.project1.pricekeeper;

/**
 * Price Keeper
 *
 * The purpose of this application is to keep track of the smartphone prices, as
 * the Web Crawler sends them. Keeping the prices in memory is sufficient for
 * the sake of this assignment. The Stats Producer must listen to two different
 * destinations: the topic where the Crawler sends its messages (a durable
 * subscription is necessary here) and a queue where the price requester(s) ask
 * for prices.
 *
 * Project #1 - IS 2015/16 – 1st Semester MEI
 *
 * Deadline: 2015-10-16
 */
import is.project1.config.Config;
import is.project1.debug.Debug;
import is.project1.xml.XmlHelper;
import java.io.StringReader;
import java.util.Properties;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Queue;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.transform.stream.StreamSource;

/**
 * Receives XML messages from the WebCrawler topic and receives search requests
 * from the PriceKeeper queue.
 *
 * The xml data received from the WebCrawler is kept as is in memory. The search
 * requests get a filtered version of the xml or an empty body if I don't have
 * data yet.
 *
 * The program does not stop.
 *
 * The user should have the permissions <code>createDurableQueue</code> and
 * <code>deleteDurableQueue</code>.
 *
 * @author Flávio J. Saraiva
 */
public class PriceKeeper implements Runnable {

    public static final String CLIENT_ID = "PriceKeeper";
    public static final String SUBSCRIPTION_NAME = "only one subscription";

    private String user;
    private String pass;
    // to receive data
    private TopicConnectionFactory topicConnectionFactory;
    private Topic topic;
    // to receive requests
    private ConnectionFactory queueConnectionFactory;
    private Queue queue;

    // data
    private volatile String data;

    public PriceKeeper() {
    }

    @Override
    public void run() {
        // load config
        if (!load()) {
            return;
        }

        // handle async requests
        try (AsyncRequestHandler requestHandler = new AsyncRequestHandler(
                /* lambda expression */() -> data,
                queueConnectionFactory.createContext(user, pass))) {
            requestHandler.start(queue);

            // receive data from topic
            while (true) {
                final String xml;
                try {
                    xml = receive();
                    Debug.println(xml);
                } catch (Exception ex) {
                    System.out.println("ERROR: Failed to receive data from topic.");
                    Debug.printStackTrace(ex);
                    continue; // try again
                }

                // validate message
                // @xxx doesn't check timestamp
                try {
                    XmlHelper.validate(new StreamSource(new StringReader(xml)));
                } catch (Exception ex) {
                    System.out.println("ERROR: Failed to validate.");
                    Debug.printStackTrace(ex);
                    continue; // try again
                }

                // replace data
                this.data = xml;
            }
        }
    }

    /**
     * Load everything according to the config file.
     *
     * @return True if it succeeded.
     */
    private boolean load() {
        // load config
        final Properties config;
        try {
            config = Config.load(Config.defaultProperties());
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to read config file.");
            Debug.printStackTrace(ex);
            return false;
        }

        // credentials
        user = config.getProperty(Config.PROPERTY_USER);
        pass = config.getProperty(Config.PROPERTY_PASS);
        Debug.format("%s: %s\n", Config.PROPERTY_USER, user);
        Debug.format("%s: %s\n", Config.PROPERTY_PASS, pass);

        // JMS Queue
        final String queueFactory = config.getProperty(Config.PROPERTY_QUEUE_FACTORY);
        final String queueName = config.getProperty(Config.PROPERTY_QUEUE_NAME);
        Debug.format("%s: %s\n", Config.PROPERTY_QUEUE_FACTORY, queueFactory);
        Debug.format("%s: %s\n", Config.PROPERTY_QUEUE_NAME, queueName);
        try {
            queueConnectionFactory = InitialContext.doLookup(queueFactory);
        } catch (NamingException ex) {
            System.out.println("ERROR: Failed to get queue factory.");
            Debug.printStackTrace(ex);
            return false;
        }
        try {
            queue = InitialContext.doLookup(queueName);
        } catch (NamingException ex) {
            System.out.println("ERROR: Failed to get queue.");
            Debug.printStackTrace(ex);
            return false;
        }

        // JMS topic
        final String topicFactory = config.getProperty(Config.PROPERTY_TOPIC_FACTORY);
        final String topicName = config.getProperty(Config.PROPERTY_TOPIC_NAME);
        Debug.format("%s: %s\n", Config.PROPERTY_TOPIC_FACTORY, topicFactory);
        Debug.format("%s: %s\n", Config.PROPERTY_TOPIC_NAME, topicName);
        try {
            topicConnectionFactory = InitialContext.doLookup(topicFactory);
        } catch (NamingException ex) {
            System.out.println("ERROR: Failed to get topic factory.");
            Debug.printStackTrace(ex);
            return false;
        }
        try {
            topic = InitialContext.doLookup(topicName);
        } catch (NamingException ex) {
            System.out.println("ERROR: Failed to get topic.");
            Debug.printStackTrace(ex);
            return false;
        }

        return true; // all ok
    }

    /**
     * Receive a XML message from the WebCrawler topic using jms 2.0.
     *
     * If debug is enabled, it returns the sample message.
     *
     * @return The xml message.
     * @throws Exception If something went wrong.
     */
    private String receive() throws Exception {
        // read topic
        try (JMSContext context = topicConnectionFactory.createContext(user, pass)) {
            context.setClientID(CLIENT_ID);
            final JMSConsumer consumer = context.createDurableConsumer(topic, SUBSCRIPTION_NAME);
            return consumer.receiveBody(String.class);
        }
    }

    /**
     * Starting point of the console app.
     *
     * @param args ignored.
     * @throws Exception if anything bad happens
     */
    public static void main(String[] args) throws Exception {
        Debug.main(args);
        final PriceKeeper app = new PriceKeeper();
        app.run();
    }

}
