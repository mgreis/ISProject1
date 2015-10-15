<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
    <xsl:output omit-xml-declaration="yes" indent="yes"/>
    <xsl:output method="text"/>
    <xsl:strip-space elements="*"/>


    <xsl:template match="report/smartphone">
        <xsl:value-of select="concat( title , ' : ' , price , ' ' , price/@currency ,'&#xA;')"/>
    </xsl:template>
</xsl:stylesheet>