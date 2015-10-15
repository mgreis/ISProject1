<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : seach.xsl
    Created on : October 4, 2015, 3:56 PM
    Author     : Flávio J. Saraiva
    Description:
        Search for smarphones with a specific content by replacing %XPATH%.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="xml" indent="yes"/>

    <!-- root -->
    <xsl:template match="/">
        <xsl:apply-templates select="report"/>
    </xsl:template>

    <!-- copy report -->
    <xsl:template match="report">
        <xsl:copy>
            <xsl:apply-templates select="smartphone"/>
        </xsl:copy>
    </xsl:template>
    
    <!-- copy smartphones that match -->
    <xsl:template match="smartphone">
        <xsl:choose>
            <xsl:when test="%XPATH%">
                <xsl:copy-of select="."/>
            </xsl:when>
        </xsl:choose>
    </xsl:template>
  
</xsl:stylesheet>
