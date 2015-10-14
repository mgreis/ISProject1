package is.project1.summary;

/**
 * HTML Summary Creator
 *
 * This application should run permanently, waiting for XML messages from the
 * JMS topic. This application must create an HTML file, using the XML files
 * coming from the Topic (keep one file per reading of the Crawler). For this,
 * you should use an XSL template for transforming the resulting XML file into
 * HTML. You are pretty much free to define the HTML as you want, but you should
 * include all the data collected by the Crawler. Organization of the page is
 * important. Use a web browser with a built-in XSLT engine (e.g., Firefox) to
 * apply the transformation and display the resulting HTML page. Note: Use
 * durable subscriptions to ensure that even if the HTML Summary Creator fails,
 * the Topic will keep the messages for later retrieval.
 *
 * Project #1 - IS 2015/16 – 1st Semester MEI
 *
 * Deadline: 2015-10-16
 */
import is.project1.config.Config;
import is.project1.debug.Debug;
import is.project1.xml.XmlHelper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.transform.stream.StreamSource;
import org.w3c.dom.Document;

/**
 * Receives XML messages from the WebCrawler topic and generates html pages.
 *
 * The program only stops when there is an exception.
 *
 * The user should have the permissions <code>createDurableQueue</code> and
 * <code>deleteDurableQueue</code>.
 *
 * @author Flávio J. Saraiva
 */
public class HTMLSummaryCreator implements Runnable {

    public static final String CLIENT_ID = "HTMLSummaryCreator";
    public static final String SUBSCRIPTION_NAME = "only one subscription";

    private String user;
    private String pass;
    private File dir;
    private TopicConnectionFactory connectionFactory;
    private Topic topic;

    public HTMLSummaryCreator() {
    }

    @Override
    public void run() {
        // load (according to config)
        if (!load()) {
            return;
        }

        // receive message
        final String xml;
        try {
            xml = receive();
            Debug.println(xml);
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to receive message.");
            Debug.printStackTrace(ex);
            return;
        }

        // validate message
        try {
            XmlHelper.validate(new StreamSource(new StringReader(xml)));
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to validate.");
            Debug.printStackTrace(ex);
            return;
        }

        // copy external stylesheet
        try {
            final Path xslPath = Paths.get(dir.getCanonicalPath(), "to_html.xsl");
            if (xslPath.toFile().exists()) {
                Files.delete(xslPath); // @todo replace? compare contents?
            }
            Files.copy(getClass().getResourceAsStream("/is/project1/summary/to_html.xsl"), xslPath);
            System.out.println("Wrote " + xslPath);
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to copy external stylesheet.");
            Debug.printStackTrace(ex);
            return;
        }

        // write modified xml (uses external stylesheet)
        try {
            final Document document = XmlHelper.toDocument(xml);
            document.setXmlStandalone(true);
            document.insertBefore(
                    document.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"to_html.xsl\""),
                    document.getDocumentElement());
            final String xmlResult = XmlHelper.toString(document);
            final Path resultPath = Paths.get(dir.getCanonicalPath(), document.getDocumentElement().getAttribute("timestamp") + ".xml");
            try (FileWriter writer = new FileWriter(resultPath.toFile())) {
                writer.write(xmlResult);
            }
            System.out.println("Wrote " + resultPath);
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to write xml.");
            Debug.printStackTrace(ex);
            return;
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

        // where we store our summaries
        final String summaryDir = config.getProperty(Config.PROPERTY_SUMMARY_DIR);
        Debug.format("%s: %s\n", Config.PROPERTY_SUMMARY_DIR, summaryDir);
        dir = new File(summaryDir);
        if (!dir.isDirectory() || !dir.canWrite()) {
            System.out.println("ERROR: not a writable directory.");
            try {
                Debug.format("Canonical path of summaryDir: \"%s\"\n", dir.getCanonicalPath());
            } catch (Exception ex) {
                Debug.println("failed to get the cannonical path.");
                Debug.printStackTrace(ex);
            }
            return false;
        }

        // JMS topic
        final String topicFactory = config.getProperty(Config.PROPERTY_TOPIC_FACTORY);
        final String topicName = config.getProperty(Config.PROPERTY_TOPIC_NAME);
        Debug.format("%s: %s\n", Config.PROPERTY_TOPIC_FACTORY, topicFactory);
        Debug.format("%s: %s\n", Config.PROPERTY_TOPIC_NAME, topicName);
        try {
            connectionFactory = InitialContext.doLookup(topicFactory);
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
        if (Debug.ENABLED) {
            // read sample
            return new String(Files.readAllBytes(Paths.get(getClass().getResource("/is/project1/xml/sample.xml").toURI())));
        }
        // read topic
        try (JMSContext context = connectionFactory.createContext(user, pass)) {
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
        do {
            final HTMLSummaryCreator app = new HTMLSummaryCreator();
            app.run();
        } while (!Debug.ENABLED); // infinite loop if not debugging
    }

}
