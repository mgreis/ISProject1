<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:output method="text"/>
    <xsl:strip-space elements="*"/>


    <xsl:template match="report/smartphone">
        <xsl:value-of select="concat('-------------------------------------------------------------------------------------------------------------------------','&#xA;')"/>
        <xsl:value-of select="concat( title , ' : ' , price , ' ' , price/@currency ,'&#xA;' , url ,'&#xA;','&#xA;')"/>
        <xsl:for-each select="description">
            <dt>
                <xsl:value-of select="@name"/>
            </dt>
            <dt>
                <xsl:value-of select="concat(' ',.,'&#xA;')"/>
            </dt>
                       
                        
        </xsl:for-each>
        <dt>
            <xsl:value-of select="concat('---------------------------------------------------------------------------------------------------------------------','&#xA;')"/>
            
        </dt>
    </xsl:template>
</xsl:stylesheet>