//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.04 at 04:40:28 PM BST 
//
package is.project1.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;

/**
 * <p>
 * Java class for Smartphone complex type.
 *
 * <p>
 * The following schema fragment specifies the expected content contained within
 * this class.
 *
 * <pre>
 * &lt;complexType name="Smartphone"&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="title" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *         &lt;element name="price" type="{}Money"/&gt;
 *         &lt;element name="details" type="{http://www.w3.org/2001/XMLSchema}anyURI"/&gt;
 *       &lt;/sequence&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Smartphone", propOrder = {
    "title",
    "price",
    "details"
})
public class Smartphone {

    @XmlElement(required = true)
    protected String title;
    @XmlElement(required = true)
    protected Money price;
    @XmlElement(required = true)
    @XmlSchemaType(name = "anyURI")
    protected String details;

    /**
     * Gets the value of the title property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getTitle() {
        return title;
    }

    /**
     * Sets the value of the title property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setTitle(String value) {
        this.title = value;
    }

    /**
     * Gets the value of the price property.
     *
     * @return possible object is {@link Money }
     *
     */
    public Money getPrice() {
        return price;
    }

    /**
     * Sets the value of the price property.
     *
     * @param value allowed object is {@link Money }
     *
     */
    public void setPrice(Money value) {
        this.price = value;
    }

    /**
     * Gets the value of the details property.
     *
     * @return possible object is {@link String }
     *
     */
    public String getDetails() {
        return details;
    }

    /**
     * Sets the value of the details property.
     *
     * @param value allowed object is {@link String }
     *
     */
    public void setDetails(String value) {
        this.details = value;
    }

}