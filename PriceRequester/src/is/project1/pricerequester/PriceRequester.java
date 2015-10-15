package is.project1.pricerequester;

import is.project1.config.Config;
import is.project1.debug.Debug;
import is.project1.xml.XmlHelper;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Properties;
import java.util.Scanner;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.TemporaryQueue;
import javax.jms.TextMessage;
import javax.naming.InitialContext;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

public class PriceRequester implements Runnable {

    public static final String CLIENT_ID = "HTMLSummaryCreator";
    public static final String SUBSCRIPTION_NAME = "only one subscription";

    private String user;
    private String pass;
    private ConnectionFactory connectionFactory;
    private Destination queue;

    public PriceRequester() {

    }

    @Override
    public void run() {
        // load (according to config)
        if (!load()) {
            return;
        }

        //user input loop
        while (true) {
            // get a reference for the priceKeeper search from console
            final String reference;
            reference = this.consoleInterface();
            // receive message
            final String xml;
            try {
                xml = sendAndReceive(reference);
                Debug.println(xml);
            } catch (Exception ex) {
                System.out.println("ERROR: Failed to receive message.");
                Debug.printStackTrace(ex);
                return;
            }

            // validate message
            // @xxx doesn't check timestamp
            try {
                XmlHelper.validate(new StreamSource(new StringReader(xml)));
            } catch (Exception ex) {
                System.out.println("ERROR: Failed to validate.");
                Debug.printStackTrace(ex);
                return;
            }

            // write to console (uses stylesheet)
            try {

                // get search template
                // https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner.html
                final InputStream xslStream = getClass().getResourceAsStream("/is/project1/pricerequester/to_text.xsl");
                final String xsl = new Scanner(xslStream, "UTF-8").useDelimiter("\\A").next();

                //transform xml to plain text
                final StringWriter writer = new StringWriter();
                TransformerFactory.newInstance()
                        .newTransformer(new StreamSource(new StringReader(xsl)))
                        .transform(new StreamSource(new StringReader(xml)), new StreamResult(writer));

                //print to screen
                System.out.println(writer.toString());

            } catch (Exception ex) {
                System.out.println("ERROR: Failed to write results.");
                Debug.printStackTrace(ex);
                return;
            }
        }
    }

    //Console interface that returns the reference to be searched
    private String consoleInterface() {

        Scanner keyboard = new Scanner(System.in);
        System.out.print("Welcome to the Price Requester\n\nPlease insert reference:");
        String reference = keyboard.nextLine();
        return reference;

    }

    private String sendAndReceive(String text) throws JMSException {
        try (JMSContext context = connectionFactory.createContext(user, pass)) {
            TemporaryQueue tmpQueue = context.createTemporaryQueue();
            TextMessage message = context.createTextMessage(text);
            message.setJMSReplyTo(tmpQueue);
            context.createProducer().send(queue, message); // send
            return context.createConsumer(tmpQueue).receiveBody(String.class);
        }
    }

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

        // JMS queue
        final String queueFactory = config.getProperty(Config.PROPERTY_QUEUE_FACTORY);
        final String queueName = config.getProperty(Config.PROPERTY_QUEUE_NAME);
        Debug.format("%s: %s\n", Config.PROPERTY_QUEUE_FACTORY, queueFactory);
        Debug.format("%s: %s\n", Config.PROPERTY_QUEUE_NAME, queueName);
        try {
            connectionFactory = InitialContext.doLookup(queueFactory);
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to get queue factory.");
            Debug.printStackTrace(ex);
            return false;
        }
        try {
            queue = InitialContext.doLookup(queueName);
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to get queue.");
            Debug.printStackTrace(ex);
            return false;
        }

        return true; // all ok
    }

    public static void main(String[] args) throws Exception {
        Debug.processMainArgs(args);
        final PriceRequester app = new PriceRequester();
        app.run();
    }

}
