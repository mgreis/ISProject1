<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transform.xsl
    Created on : October 4, 2015, 3:56 PM
    Author     : Flávio J. Saraiva
    Description:
        Create a summary of the xml data.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:timestamp="java://io.project1.summary.TimestampFormatter"
                extension-element-prefixes="timestamp">
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <title>Report Summary</title>
            </head>
            <body>
                <p>
                    Report Timestamp:
                    <xsl:value-of select="report/@timestamp"/>
                    <!-- @todo transform timestamp -->
                </p>
                <table border="1">
                    <thead>
                        <tr bgcolor="#9acd32">
                            <th style="text-align:left">Title</th>
                            <th style="text-align:left">Price</th>
                            <th style="text-align:left">Details</th>
                        </tr>
                    </thead>
                    <tbody>
                        <xsl:for-each select="report/smartphone">
                            <tr>
                                <td>
                                    <xsl:value-of select="title"/>
                                </td>
                                <td>
                                    <xsl:value-of select="price"/>
                                    <xsl:text> </xsl:text>
                                    <xsl:value-of select="price/@currency"/>
                                </td>
                                <td>
                                    <a href="{details}">link</a>
                                </td>
                            </tr>
                        </xsl:for-each>
                    </tbody>
                </table>
            </body>
        </html>
    </xsl:template>

</xsl:stylesheet>
