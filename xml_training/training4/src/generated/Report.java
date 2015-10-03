//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.09.18 at 10:02:24 AM BST 
//


package generated;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType&gt;
 *   &lt;complexContent&gt;
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *       &lt;sequence&gt;
 *         &lt;element name="metric_data" maxOccurs="unbounded" minOccurs="0"&gt;
 *           &lt;complexType&gt;
 *             &lt;complexContent&gt;
 *               &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
 *                 &lt;sequence&gt;
 *                   &lt;element name="metric_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
 *                   &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
 *                   &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="spoof" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
 *                   &lt;element name="direction"&gt;
 *                     &lt;simpleType&gt;
 *                       &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *                         &lt;enumeration value="dgsg|boinc"/&gt;
 *                         &lt;enumeration value="dgsg|xtremweb"/&gt;
 *                       &lt;/restriction&gt;
 *                     &lt;/simpleType&gt;
 *                   &lt;/element&gt;
 *                 &lt;/sequence&gt;
 *               &lt;/restriction&gt;
 *             &lt;/complexContent&gt;
 *           &lt;/complexType&gt;
 *         &lt;/element&gt;
 *       &lt;/sequence&gt;
 *       &lt;attribute name="timestamp" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger" /&gt;
 *       &lt;attribute name="timezone" type="{http://www.w3.org/2001/XMLSchema}string" /&gt;
 *       &lt;attribute name="version" type="{http://www.w3.org/2001/XMLSchema}float" /&gt;
 *     &lt;/restriction&gt;
 *   &lt;/complexContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "metricData"
})
@XmlRootElement(name = "report")
public class Report {

    @XmlElement(name = "metric_data")
    protected List<Report.MetricData> metricData;
    @XmlAttribute(name = "timestamp")
    @XmlSchemaType(name = "nonNegativeInteger")
    protected BigInteger timestamp;
    @XmlAttribute(name = "timezone")
    protected String timezone;
    @XmlAttribute(name = "version")
    protected Float version;

    /**
     * Gets the value of the metricData property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the metricData property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMetricData().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Report.MetricData }
     * 
     * 
     */
    public List<Report.MetricData> getMetricData() {
        if (metricData == null) {
            metricData = new ArrayList<Report.MetricData>();
        }
        return this.metricData;
    }

    /**
     * Gets the value of the timestamp property.
     * 
     * @return
     *     possible object is
     *     {@link BigInteger }
     *     
     */
    public BigInteger getTimestamp() {
        return timestamp;
    }

    /**
     * Sets the value of the timestamp property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigInteger }
     *     
     */
    public void setTimestamp(BigInteger value) {
        this.timestamp = value;
    }

    /**
     * Gets the value of the timezone property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimezone() {
        return timezone;
    }

    /**
     * Sets the value of the timezone property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimezone(String value) {
        this.timezone = value;
    }

    /**
     * Gets the value of the version property.
     * 
     * @return
     *     possible object is
     *     {@link Float }
     *     
     */
    public Float getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     * 
     * @param value
     *     allowed object is
     *     {@link Float }
     *     
     */
    public void setVersion(Float value) {
        this.version = value;
    }


    /**
     * <p>Java class for anonymous complex type.
     * 
     * <p>The following schema fragment specifies the expected content contained within this class.
     * 
     * <pre>
     * &lt;complexType&gt;
     *   &lt;complexContent&gt;
     *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType"&gt;
     *       &lt;sequence&gt;
     *         &lt;element name="metric_name" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="timestamp" type="{http://www.w3.org/2001/XMLSchema}nonNegativeInteger"/&gt;
     *         &lt;element name="value" type="{http://www.w3.org/2001/XMLSchema}float"/&gt;
     *         &lt;element name="type" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="units" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="spoof" type="{http://www.w3.org/2001/XMLSchema}string"/&gt;
     *         &lt;element name="direction"&gt;
     *           &lt;simpleType&gt;
     *             &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
     *               &lt;enumeration value="dgsg|boinc"/&gt;
     *               &lt;enumeration value="dgsg|xtremweb"/&gt;
     *             &lt;/restriction&gt;
     *           &lt;/simpleType&gt;
     *         &lt;/element&gt;
     *       &lt;/sequence&gt;
     *     &lt;/restriction&gt;
     *   &lt;/complexContent&gt;
     * &lt;/complexType&gt;
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "metricName",
        "timestamp",
        "value",
        "type",
        "units",
        "spoof",
        "direction"
    })
    public static class MetricData {

        @XmlElement(name = "metric_name", required = true)
        protected String metricName;
        @XmlElement(required = true)
        @XmlSchemaType(name = "nonNegativeInteger")
        protected BigInteger timestamp;
        protected float value;
        @XmlElement(required = true)
        protected String type;
        @XmlElement(required = true)
        protected String units;
        @XmlElement(required = true)
        protected String spoof;
        @XmlElement(required = true)
        protected String direction;

        /**
         * Gets the value of the metricName property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getMetricName() {
            return metricName;
        }

        /**
         * Sets the value of the metricName property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setMetricName(String value) {
            this.metricName = value;
        }

        /**
         * Gets the value of the timestamp property.
         * 
         * @return
         *     possible object is
         *     {@link BigInteger }
         *     
         */
        public BigInteger getTimestamp() {
            return timestamp;
        }

        /**
         * Sets the value of the timestamp property.
         * 
         * @param value
         *     allowed object is
         *     {@link BigInteger }
         *     
         */
        public void setTimestamp(BigInteger value) {
            this.timestamp = value;
        }

        /**
         * Gets the value of the value property.
         * 
         */
        public float getValue() {
            return value;
        }

        /**
         * Sets the value of the value property.
         * 
         */
        public void setValue(float value) {
            this.value = value;
        }

        /**
         * Gets the value of the type property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getType() {
            return type;
        }

        /**
         * Sets the value of the type property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setType(String value) {
            this.type = value;
        }

        /**
         * Gets the value of the units property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getUnits() {
            return units;
        }

        /**
         * Sets the value of the units property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setUnits(String value) {
            this.units = value;
        }

        /**
         * Gets the value of the spoof property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getSpoof() {
            return spoof;
        }

        /**
         * Sets the value of the spoof property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setSpoof(String value) {
            this.spoof = value;
        }

        /**
         * Gets the value of the direction property.
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDirection() {
            return direction;
        }

        /**
         * Sets the value of the direction property.
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDirection(String value) {
            this.direction = value;
        }

    }

}