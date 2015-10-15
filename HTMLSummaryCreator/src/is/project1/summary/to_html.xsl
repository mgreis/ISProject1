<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : transform.xsl
    Created on : October 4, 2015, 3:56 PM
    Author     : Flávio J. Saraiva
    Description:
        Create a summary of the xml data.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">

    <xsl:output method="html"/>

    <!-- Html page -->
    <xsl:template match="/">
        <html>
            <head>
                <title>Report</title>
                <style>
                    /* table border */
                    table {border-collapse: collapse;}
                    table, th, td {border: 1px solid black;}

                    /* table header background */
                    th {background-color: #9acd32;}

                    /* list 'headers' */
                    dt {font-weight: bold;}
                </style>
            </head>
            <body>
                <xsl:apply-templates select="report" />
            </body>
        </html>
    </xsl:template>

    <!-- Put data in a table -->
    <xsl:template match="report">
        <dl>
            <dt>Version</dt>
            <dd>
                <xsl:value-of select="@version"/>
            </dd>
            <dt>Timestamp</dt>
            <dd>
                <xsl:value-of select="@timestamp"/>
                <!-- @todo transform timestamp -->
            </dd>
            <dt>Crawler</dt>
            <dd>
                <xsl:value-of select="@crawler"/>
            </dd>
        </dl>
        <table>
            <thead>
                <tr>
                    <th style="text-align:left">Title</th>
                    <th style="text-align:left">Description</th>
                    <th style="text-align:left">Price</th>
                    <th style="text-align:left">URL</th>
                </tr>
            </thead>
            <tbody>
                <xsl:apply-templates select="smartphone" />
            </tbody>
        </table>
    </xsl:template>

    <!-- Smartphone table row -->
    <xsl:template match="smartphone">
        <tr>
            <td>
                <xsl:value-of select="title"/>
            </td>
            <td>
                <dl>
                    <xsl:for-each select="description">
                        <dt>
                            <xsl:value-of select="@name"/>
                        </dt>
                        <dd>
                            <xsl:value-of select="."/>
                        </dd>
                    </xsl:for-each>
                </dl>
            </td>
            <td>
                <xsl:value-of select="price"/>
                <xsl:text> </xsl:text>
                <xsl:value-of select="price/@currency"/>
            </td>
            <td>
                <a href="{url}">link</a>
            </td>
        </tr>
    </xsl:template>
  
</xsl:stylesheet>
