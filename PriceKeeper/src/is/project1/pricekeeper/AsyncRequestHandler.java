/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.project1.pricekeeper;

import is.project1.debug.Debug;
import java.io.InputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Scanner;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 *
 * @author Flávio J. Saraiva
 */
public class AsyncRequestHandler implements MessageListener, AutoCloseable {

    /**
     * Interface that provides raw data to be filtered by the request.
     */
    public interface DataProvider {

        /**
         * Returns the xml data.
         *
         * @return Xml data.
         */
        public String getData();
    }

    private final DataProvider dataProvider;
    private final JMSContext context;

    public AsyncRequestHandler(DataProvider dataProvider, JMSContext context) {
        assert (dataProvider != null);
        assert (context != null);
        this.dataProvider = dataProvider;
        this.context = context;
    }

    /**
     * Start receiving requests.
     *
     * @param queue
     */
    public void start(Queue queue) {
        context.createConsumer(queue).setMessageListener(this);
    }

    @Override
    public void onMessage(Message msg) {
        try {
            if (!(msg instanceof TextMessage)) {
                return; // don't know how to handle this message
            }

            final Destination replyTo = msg.getJMSReplyTo();
            if (replyTo == null) {
                return; // avoid useless effort
            }

            // search
            final String query = msg.getBody(String.class
            );
            Debug.format("Request query: %s\n", query);
            final String result = search(query);

            Debug.format("Request result: %s\n", result);

            // reply
            context.createProducer().send(replyTo, result);
        } catch (Exception ex) {
            System.out.println("ERROR: Failed to handle request.");
            Debug.printStackTrace(ex);
        }
    }

    /**
     * Searches for any of the words in the query.
     *
     * @param query Query string.
     * @return Filtered xml data or null.
     * @throws TransformerConfigurationException When it is not possible to
     * create a Transformer instance.
     * @throws TransformerException If an unrecoverable error occurs during the
     * course of the transformation.
     */
    private String search(String query) throws TransformerConfigurationException, TransformerException {
        assert (query != null);

        // get data
        final String data = dataProvider.getData();
        if (data == null) {
            return null; // no data, send empty body
        }

        // get search template
        // https://weblogs.java.net/blog/pat/archive/2004/10/stupid_scanner.html
        final InputStream templateStream = getClass().getResourceAsStream("/is/project1/pricekeeper/search.xsl");
        final String template = new Scanner(templateStream, "UTF-8").useDelimiter("\\A").next();

        // replace xpath in template
        // http://stackoverflow.com/questions/3655549/xpath-containstext-some-string-doesnt-work-when-used-with-node-with-more
        final StringBuilder xpath = new StringBuilder();
        if (query.trim().isEmpty()) {
            // all smartphones
            xpath.append("*");
        } else {
            xpath.append("*[text()[");
            boolean appendAnd = false;
            for (String word : query.split("\\s")) {
                if (word.isEmpty()) {
                    continue; // ignore empty
                }
                if (appendAnd) {
                    xpath.append(" and ");
                }
                xpath.append("contains(.,'")
                        .append(word.replace("'", "\"").replace("\"", "\\\""))
                        .append("')");
                appendAnd = true;
            }
            xpath.append("]]");
        }
        Debug.format("XPath: %s\n", xpath);
        final String xsl = template.replace("%XPATH%", xpath.toString());
        Debug.println(xsl);

        // filter data
        final StringWriter writer = new StringWriter();
        final StreamResult xmlResult = new StreamResult(writer);
        TransformerFactory.newInstance()
                .newTransformer(new StreamSource(new StringReader(xsl)))
                .transform(new StreamSource(new StringReader(data)), xmlResult);

        // return result
        return writer.toString();
    }

    @Override
    public void close() {
        context.close();
    }

}
