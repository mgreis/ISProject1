package is.project1.webcrawler;

/**
 * WebCrawler
 *
 * The Web Crawler is a stand-­‐alone command-­‐line application that reads a
 * web page and sends an XML message to a JMS Topic, carrying details of
 * smartphones from the Pixmania site.
 *
 * Project #1 - IS 2015/16 – 1st Semester MEI
 *
 * Deadline: 2015-10-16
 */
import is.project1.config.Config;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;

import java.io.IOException;
import java.io.StringReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.JMSRuntimeException;
import javax.jms.Topic;
import javax.jms.TopicConnectionFactory;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import java.io.FileReader;
import java.io.FileWriter;

/**
 *
 *
 * The program only stops when there is an exception.
 *
 *
 *
 * @author Mário Alves Pereira.
 */
public class Sender implements Runnable {

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
    private TopicConnectionFactory topicFactory;
    private Topic topic;
    private String message = "";

    //Class contructor
    public Sender(String message) throws IOException {
        this.message = message;
        final Properties config = Config.load();
        user = config.getProperty("user", DEFAULT_USER);
        pass = config.getProperty("pass", DEFAULT_PASS);
        dir = new File(config.getProperty("dir", DEFAULT_DIR));
        if (!(dir.isDirectory() && dir.canWrite())) {
            throw new SecurityException("\"" + dir.getCanonicalPath() + "\" is not a writable directory");
        }
        try {
            this.topicFactory = InitialContext.doLookup(config.getProperty("topicFactory", DEFAULT_FACTORY));
            this.topic = InitialContext.doLookup(config.getProperty("topic", DEFAULT_TOPIC));
        } catch (NamingException ex) {
           this.topic=null;
           this.topicFactory=null;
        }
        
        
    }

    @Override
    public void run() {
        
        // verify if the topic is up and running, if not send the date into a file;
        if(this.topic==null ||this.topicFactory==null)
        {
            this.dealWithInitialContextException();
            return;
        }
        
        
        
        try {
            final String xml = message;

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

            //send;
            this.send();
        } catch (Exception ex) {
            throw new RuntimeException(ex); // @todo blow up or not?
        }
    }

    /**
     * Send a XML string to the topic.
     *
     * @throws JMSException
     */
    private void send() throws JMSException {
        int cont = 0;
        while (cont < 10) {
            try (JMSContext jcontext = topicFactory.createContext(user, pass);) {
                JMSProducer mp = jcontext.createProducer();
                
                //send message stored in file if it exists
                String aux = this.readFromFile();
                if ( aux!=null ){
                    mp.send(topic, aux);
                    this.deleteFile();
                }
                
                //send message;
                mp.send(topic, message);
                break;
            } catch (JMSRuntimeException re) {
                re.printStackTrace();
                cont++;

            }
        }
        if (cont == 10) {
            this.createFile();
            this.sendToFile();
        }
    }
    
    
    /**
     * if the initialContext fails we must store the info into a file
     * 
     */
    public void dealWithInitialContextException() {
        try {
            final String xml = message;

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

            //store;
           this.createFile();
           this.sendToFile();
        } catch (Exception ex) {
            throw new RuntimeException(ex); // @todo blow up or not?
        }
    }
    
    
    
    

    /**
     * In case the crawler.txt file doesn't exist it creates it;
     */
    private void createFile() {
        try {
            File file = new File("crawler.txt");
            if (file.createNewFile()) {
                System.out.println("crawler.txt is created!");
            } else {
                System.out.println("crawler.txt already exists.");
                
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * Sends the XML string to a crawler.txt file
     */
    private void sendToFile() {
        try {
            FileWriter file = new FileWriter("crawler.txt");
            file.write(message);
            file.close();
        } catch (IOException ex) {
            Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * reads from crawler.txt and returns string
     * @return 
     */
    private String readFromFile(){
        try {
            FileReader file=new FileReader("crawler.txt");
            String aux = file.toString();
            try {
                file.close();
            } catch (IOException ex) {
                Logger.getLogger(Sender.class.getName()).log(Level.SEVERE, null, ex);
            }
            return aux;
            
        } catch (FileNotFoundException ex) {
            return null;
        }
    }
    
    private void deleteFile(){
        File file = new File("crawler.txt");
        file.delete();
    }
}
