<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">

<xsl:template match="/">
  <html>
  <body>
  <h2>Report</h2>
    <table border="1">
      <tr bgcolor="#9acd32">
        <th style="text-align:left">Metric Name</th>
        <th style="text-align:left">Timestamp</th>
        <th style="text-align:left">Value</th>
        <th style="text-align:left">Type</th>
        <th style="text-align:left">Units</th>
        <th style="text-align:left">Spoof</th>
        <th style="text-align:left">Direction</th>
      </tr>
      <xsl:for-each select="report/metric_data">
      <tr>
        <td><xsl:value-of select="metric_name"/></td>
        <td><xsl:value-of select="timestamp"/></td>
        <td><xsl:value-of select="value"/></td>
        <td><xsl:value-of select="type"/></td>
        <td><xsl:value-of select="units"/></td>
        <td><xsl:value-of select="spoof"/></td>
        <td><xsl:value-of select="direction"/></td>
      </tr>
      </xsl:for-each>
    </table>
  </body>
  </html>
</xsl:template>
</xsl:stylesheet>
