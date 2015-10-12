//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.11 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.10.12 at 01:38:55 AM BST 
//


package is.project1.xml;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for Price complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="Price"&gt;
 *   &lt;simpleContent&gt;
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema&gt;decimal"&gt;
 *       &lt;attribute name="currency" default="EUR"&gt;
 *         &lt;simpleType&gt;
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string"&gt;
 *             &lt;enumeration value="AED"/&gt;
 *             &lt;enumeration value="AFN"/&gt;
 *             &lt;enumeration value="ALL"/&gt;
 *             &lt;enumeration value="AMD"/&gt;
 *             &lt;enumeration value="ANG"/&gt;
 *             &lt;enumeration value="AOA"/&gt;
 *             &lt;enumeration value="ARS"/&gt;
 *             &lt;enumeration value="AUD"/&gt;
 *             &lt;enumeration value="AWG"/&gt;
 *             &lt;enumeration value="AZN"/&gt;
 *             &lt;enumeration value="BAM"/&gt;
 *             &lt;enumeration value="BBD"/&gt;
 *             &lt;enumeration value="BDT"/&gt;
 *             &lt;enumeration value="BGN"/&gt;
 *             &lt;enumeration value="BHD"/&gt;
 *             &lt;enumeration value="BIF"/&gt;
 *             &lt;enumeration value="BMD"/&gt;
 *             &lt;enumeration value="BND"/&gt;
 *             &lt;enumeration value="BOB"/&gt;
 *             &lt;enumeration value="BOV"/&gt;
 *             &lt;enumeration value="BRL"/&gt;
 *             &lt;enumeration value="BSD"/&gt;
 *             &lt;enumeration value="BTN"/&gt;
 *             &lt;enumeration value="BWP"/&gt;
 *             &lt;enumeration value="BYR"/&gt;
 *             &lt;enumeration value="BZD"/&gt;
 *             &lt;enumeration value="CAD"/&gt;
 *             &lt;enumeration value="CDF"/&gt;
 *             &lt;enumeration value="CHE"/&gt;
 *             &lt;enumeration value="CHF"/&gt;
 *             &lt;enumeration value="CHW"/&gt;
 *             &lt;enumeration value="CLF"/&gt;
 *             &lt;enumeration value="CLP"/&gt;
 *             &lt;enumeration value="CNY"/&gt;
 *             &lt;enumeration value="COP"/&gt;
 *             &lt;enumeration value="COU"/&gt;
 *             &lt;enumeration value="CRC"/&gt;
 *             &lt;enumeration value="CUC"/&gt;
 *             &lt;enumeration value="CUP"/&gt;
 *             &lt;enumeration value="CVE"/&gt;
 *             &lt;enumeration value="CZK"/&gt;
 *             &lt;enumeration value="DJF"/&gt;
 *             &lt;enumeration value="DKK"/&gt;
 *             &lt;enumeration value="DOP"/&gt;
 *             &lt;enumeration value="DZD"/&gt;
 *             &lt;enumeration value="EGP"/&gt;
 *             &lt;enumeration value="ERN"/&gt;
 *             &lt;enumeration value="ETB"/&gt;
 *             &lt;enumeration value="EUR"/&gt;
 *             &lt;enumeration value="FJD"/&gt;
 *             &lt;enumeration value="FKP"/&gt;
 *             &lt;enumeration value="GBP"/&gt;
 *             &lt;enumeration value="GEL"/&gt;
 *             &lt;enumeration value="GHS"/&gt;
 *             &lt;enumeration value="GIP"/&gt;
 *             &lt;enumeration value="GMD"/&gt;
 *             &lt;enumeration value="GNF"/&gt;
 *             &lt;enumeration value="GTQ"/&gt;
 *             &lt;enumeration value="GYD"/&gt;
 *             &lt;enumeration value="HKD"/&gt;
 *             &lt;enumeration value="HNL"/&gt;
 *             &lt;enumeration value="HRK"/&gt;
 *             &lt;enumeration value="HTG"/&gt;
 *             &lt;enumeration value="HUF"/&gt;
 *             &lt;enumeration value="IDR"/&gt;
 *             &lt;enumeration value="ILS"/&gt;
 *             &lt;enumeration value="INR"/&gt;
 *             &lt;enumeration value="IQD"/&gt;
 *             &lt;enumeration value="IRR"/&gt;
 *             &lt;enumeration value="ISK"/&gt;
 *             &lt;enumeration value="JMD"/&gt;
 *             &lt;enumeration value="JOD"/&gt;
 *             &lt;enumeration value="JPY"/&gt;
 *             &lt;enumeration value="KES"/&gt;
 *             &lt;enumeration value="KGS"/&gt;
 *             &lt;enumeration value="KHR"/&gt;
 *             &lt;enumeration value="KMF"/&gt;
 *             &lt;enumeration value="KPW"/&gt;
 *             &lt;enumeration value="KRW"/&gt;
 *             &lt;enumeration value="KWD"/&gt;
 *             &lt;enumeration value="KYD"/&gt;
 *             &lt;enumeration value="KZT"/&gt;
 *             &lt;enumeration value="LAK"/&gt;
 *             &lt;enumeration value="LBP"/&gt;
 *             &lt;enumeration value="LKR"/&gt;
 *             &lt;enumeration value="LRD"/&gt;
 *             &lt;enumeration value="LSL"/&gt;
 *             &lt;enumeration value="LYD"/&gt;
 *             &lt;enumeration value="MAD"/&gt;
 *             &lt;enumeration value="MDL"/&gt;
 *             &lt;enumeration value="MGA"/&gt;
 *             &lt;enumeration value="MKD"/&gt;
 *             &lt;enumeration value="MMK"/&gt;
 *             &lt;enumeration value="MNT"/&gt;
 *             &lt;enumeration value="MOP"/&gt;
 *             &lt;enumeration value="MRO"/&gt;
 *             &lt;enumeration value="MUR"/&gt;
 *             &lt;enumeration value="MVR"/&gt;
 *             &lt;enumeration value="MWK"/&gt;
 *             &lt;enumeration value="MXN"/&gt;
 *             &lt;enumeration value="MXV"/&gt;
 *             &lt;enumeration value="MYR"/&gt;
 *             &lt;enumeration value="MZN"/&gt;
 *             &lt;enumeration value="NAD"/&gt;
 *             &lt;enumeration value="NGN"/&gt;
 *             &lt;enumeration value="NIO"/&gt;
 *             &lt;enumeration value="NOK"/&gt;
 *             &lt;enumeration value="NPR"/&gt;
 *             &lt;enumeration value="NZD"/&gt;
 *             &lt;enumeration value="OMR"/&gt;
 *             &lt;enumeration value="PAB"/&gt;
 *             &lt;enumeration value="PEN"/&gt;
 *             &lt;enumeration value="PGK"/&gt;
 *             &lt;enumeration value="PHP"/&gt;
 *             &lt;enumeration value="PKR"/&gt;
 *             &lt;enumeration value="PLN"/&gt;
 *             &lt;enumeration value="PYG"/&gt;
 *             &lt;enumeration value="QAR"/&gt;
 *             &lt;enumeration value="RON"/&gt;
 *             &lt;enumeration value="RSD"/&gt;
 *             &lt;enumeration value="RUB"/&gt;
 *             &lt;enumeration value="RWF"/&gt;
 *             &lt;enumeration value="SAR"/&gt;
 *             &lt;enumeration value="SBD"/&gt;
 *             &lt;enumeration value="SCR"/&gt;
 *             &lt;enumeration value="SDG"/&gt;
 *             &lt;enumeration value="SEK"/&gt;
 *             &lt;enumeration value="SGD"/&gt;
 *             &lt;enumeration value="SHP"/&gt;
 *             &lt;enumeration value="SLL"/&gt;
 *             &lt;enumeration value="SOS"/&gt;
 *             &lt;enumeration value="SRD"/&gt;
 *             &lt;enumeration value="SSP"/&gt;
 *             &lt;enumeration value="STD"/&gt;
 *             &lt;enumeration value="SYP"/&gt;
 *             &lt;enumeration value="SZL"/&gt;
 *             &lt;enumeration value="THB"/&gt;
 *             &lt;enumeration value="TJS"/&gt;
 *             &lt;enumeration value="TMT"/&gt;
 *             &lt;enumeration value="TND"/&gt;
 *             &lt;enumeration value="TOP"/&gt;
 *             &lt;enumeration value="TRY"/&gt;
 *             &lt;enumeration value="TTD"/&gt;
 *             &lt;enumeration value="TWD"/&gt;
 *             &lt;enumeration value="TZS"/&gt;
 *             &lt;enumeration value="UAH"/&gt;
 *             &lt;enumeration value="UGX"/&gt;
 *             &lt;enumeration value="USD"/&gt;
 *             &lt;enumeration value="USN"/&gt;
 *             &lt;enumeration value="USS"/&gt;
 *             &lt;enumeration value="UYI"/&gt;
 *             &lt;enumeration value="UYU"/&gt;
 *             &lt;enumeration value="UZS"/&gt;
 *             &lt;enumeration value="VEF"/&gt;
 *             &lt;enumeration value="VND"/&gt;
 *             &lt;enumeration value="VUV"/&gt;
 *             &lt;enumeration value="WST"/&gt;
 *             &lt;enumeration value="XAF"/&gt;
 *             &lt;enumeration value="XAG"/&gt;
 *             &lt;enumeration value="XAU"/&gt;
 *             &lt;enumeration value="XBA"/&gt;
 *             &lt;enumeration value="XBB"/&gt;
 *             &lt;enumeration value="XBC"/&gt;
 *             &lt;enumeration value="XBD"/&gt;
 *             &lt;enumeration value="XCD"/&gt;
 *             &lt;enumeration value="XDR"/&gt;
 *             &lt;enumeration value="XFU"/&gt;
 *             &lt;enumeration value="XOF"/&gt;
 *             &lt;enumeration value="XPD"/&gt;
 *             &lt;enumeration value="XPF"/&gt;
 *             &lt;enumeration value="XPT"/&gt;
 *             &lt;enumeration value="XSU"/&gt;
 *             &lt;enumeration value="XTS"/&gt;
 *             &lt;enumeration value="XUA"/&gt;
 *             &lt;enumeration value="XXX"/&gt;
 *             &lt;enumeration value="YER"/&gt;
 *             &lt;enumeration value="ZAR"/&gt;
 *             &lt;enumeration value="ZMW"/&gt;
 *           &lt;/restriction&gt;
 *         &lt;/simpleType&gt;
 *       &lt;/attribute&gt;
 *     &lt;/extension&gt;
 *   &lt;/simpleContent&gt;
 * &lt;/complexType&gt;
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "Price", propOrder = {
    "value"
})
public class Price {

    @XmlValue
    protected BigDecimal value;
    @XmlAttribute(name = "currency")
    protected String currency;

    /**
     * Gets the value of the value property.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setValue(BigDecimal value) {
        this.value = value;
    }

    /**
     * Gets the value of the currency property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrency() {
        if (currency == null) {
            return "EUR";
        } else {
            return currency;
        }
    }

    /**
     * Sets the value of the currency property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrency(String value) {
        this.currency = value;
    }

}
