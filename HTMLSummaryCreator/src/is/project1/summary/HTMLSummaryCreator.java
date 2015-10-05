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
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;
import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

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
    public static final String SUBSCRIPTION_NAME = "one";
    public static final String DEFAULT_USER = "user";
    public static final String DEFAULT_PASS = "pass";
    public static final String DEFAULT_DIR = ".";
    public static final String DEFAULT_FACTORY = "jms/RemoteConnectionFactory";
    public static final String DEFAULT_TOPIC = "IS/Project1/WebCrawlerTopic";

    private final String user;
    private final String pass;
    private final File dir;
    private final TopicConnectionFactory topicFactory;
    private final Topic topic;

    public HTMLSummaryCreator() throws IOException, NamingException {
        final Properties config = Config.load();
        user = config.getProperty("user", DEFAULT_USER);
        pass = config.getProperty("pass", DEFAULT_PASS);
        dir = new File(config.getProperty("dir", DEFAULT_DIR));
        if (!(dir.isDirectory() && dir.canWrite())) {
            throw new SecurityException("\"" + dir.getCanonicalPath() + "\" is not a writable directory");
        }
        this.topicFactory = InitialContext.doLookup(config.getProperty("topicFactory", DEFAULT_FACTORY));
        this.topic = InitialContext.doLookup(config.getProperty("topic", DEFAULT_TOPIC));
    }

    @Override
    public void run() {
        try {
            final String xml = new String(Files.readAllBytes(Paths.get(getClass().getResource("/is/project1/xml/sample.xml").toURI())));
            //final String xml = this.receive();
            // parse
            final Document document = DocumentBuilderFactory.newInstance()
                    .newDocumentBuilder()
                    .parse(new InputSource(new StringReader(xml)));
            // validate
            final StreamSource schemaSource = new StreamSource(getClass().getResourceAsStream("/is/project1/xml/schema.xsd"));
            SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                    .newSchema(schemaSource)
                    .newValidator()
                    .validate(new DOMSource(document));
            // transform
            final Path xslPath = Paths.get(dir.getCanonicalPath(), "to_html.xsl");
            if (!xslPath.toFile().exists()) {
                Files.copy(getClass().getResourceAsStream("/is/project1/summary/to_html.xsl"), xslPath);
                System.out.println("Wrote " + xslPath);
            }
            document.setXmlStandalone(true);
            document.insertBefore(
                    document.createProcessingInstruction("xml-stylesheet", "type=\"text/xsl\" href=\"to_html.xsl\""),
                    document.getDocumentElement());
            final StreamResult xmlResult = new StreamResult(new StringWriter());
            TransformerFactory.newInstance()
                    .newTransformer()
                    .transform(new DOMSource(document), xmlResult);
            final Path resultPath = Paths.get(dir.getCanonicalPath(), document.getDocumentElement().getAttribute("timestamp") + ".xml");
            try (FileWriter writer = new FileWriter(resultPath.toFile())) {
                writer.write(xmlResult.getWriter().toString());
            }
            System.out.println("Wrote " + resultPath);
        } catch (Exception ex) {
            throw new RuntimeException(ex); // @todo blow up or not?
        }
    }

    /**
     * Receive a XML string from the WebCrawler topic.
     *
     * @return xml string.
     * @throws JMSException
     */
    private String receive() throws JMSException {
        try (TopicConnection connection = topicFactory.createTopicConnection(user, pass)) {
            connection.setClientID(CLIENT_ID);
            connection.start();
            final TextMessage message = (TextMessage) connection
                    .createTopicSession(false/* not transacted */, TopicSession.AUTO_ACKNOWLEDGE)
                    .createDurableSubscriber(topic, SUBSCRIPTION_NAME)
                    .receive();
            return message.getText();
        }
    }

    /**
     * Starting point of the console app.
     *
     * @param args ignored.
     * @throws Exception if anything bad happens
     */
    public static void main(String[] args) throws Exception {
        final HTMLSummaryCreator app = new HTMLSummaryCreator();
        app.run();
    }

}
