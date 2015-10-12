/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package is.project1.xml;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
import javax.xml.XMLConstants;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

/**
 * Helper class for xml.
 *
 * @author Flávio J. Saraiva
 */
public class XmlHelper {

    /**
     * Converts a report object to a xml string.
     *
     * @param report object to be converted.
     * @return xml string.
     * @throws JAXBException if an error occurs while converting the object to
     * xml.
     */
    public static String toString(Report report) throws JAXBException {
        assert (report != null);
        final StringWriter writer = new StringWriter();
        final JAXBContext context = JAXBContext.newInstance(ObjectFactory.class, Report.class, Smartphone.class, Description.class, Price.class);
        final Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, writer);
        return writer.toString();
    }

    /**
     * Converts a document to a xml string.
     *
     * @param document Document to be converted.
     * @return Xml string.
     * @throws TransformerConfigurationException When it is not possible to
     * create a Transformer instance.
     * @throws TransformerException If an unrecoverable error occurs during the
     * course of the transformation.
     */
    public static String toString(Document document) throws TransformerConfigurationException, TransformerException {
        assert (document != null);
        StringWriter writer = new StringWriter();
        final StreamResult xmlResult = new StreamResult(writer);
        TransformerFactory.newInstance()
                .newTransformer()
                .transform(new DOMSource(document), xmlResult);
        return writer.toString();
    }

    /**
     * Converts a xml string to a document.
     *
     * @param xml Xml string.
     * @return Converted document.
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */
    public static Document toDocument(String xml) throws ParserConfigurationException, SAXException, IOException {
        assert (xml != null);
        return DocumentBuilderFactory.newInstance()
                .newDocumentBuilder()
                .parse(new InputSource(new StringReader(xml)));
    }

    /**
     * Validates a xml source according to the schema in this package.
     *
     * @param xmlSource Xml source.
     * @throws SAXException If a SAX error occurs during parsing.
     * @throws IOException If the validator is processing a SAXSource and the
     * underlying XMLReader throws an IOException.
     */
    public static void validate(Source xmlSource) throws SAXException, IOException {
        assert (xmlSource != null);
        final StreamSource schemaSource = new StreamSource(XmlHelper.class.getResourceAsStream("/is/project1/xml/schema.xsd"));
        SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI)
                .newSchema(schemaSource)
                .newValidator()
                .validate(xmlSource);
    }
}
