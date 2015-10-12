/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import is.project1.xml.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Mário
 * @author Flávio J. Saraiva
 */
public class Engine extends Thread {

    private List<String> webAddresses;
    private final Report report = new Report();

    @Override
    public void run() {
        try {
            this.getWebAddresses();
            this.crawl();
        } catch (Exception ex) {
            ex.printStackTrace(System.out);
        }
    }

    private void crawl() throws Exception {
        report.setVersion(new BigDecimal("1.0"));
        report.setCrawler("WebCrawler");
        report.setTimestamp(BigInteger.valueOf(System.currentTimeMillis()));

        // buscar dados
        for (String webAddress : webAddresses) {
            if (webAddress.trim().isEmpty()) {
                continue;// ignorar endereços vazios
            }
            System.out.println(webAddress);
            final Document html = Jsoup.connect(webAddress).timeout(20000).get();

            final Elements htmlArticles = html.select("article");
            for (Element htmlArticle : htmlArticles) {
                final Smartphone smartphone = new Smartphone();

                // title
                final Elements htmlTitle = htmlArticle.select(".productTitle");
                smartphone.setTitle(htmlTitle.text().trim());

                // descrition
                final Elements htmlDescriptions = htmlArticle.select("[itemprop=\"description\"] li");
                for (Element htmlDescription : htmlDescriptions) {
                    System.out.println(htmlDescription.text());
                    final String[] parts = htmlDescription.text().split(":", 2);
                    if (parts.length == 2) {
                        final Description description = new Description();
                        description.setName(parts[0].trim());
                        description.setValue(parts[1].trim());
                        smartphone.getDescription().add(description);
                    }
                }

                // price
                final Elements htmlPrice = htmlArticle.select("[itemprop=\"price\"]");
                final Elements htmlCurrency = htmlArticle.select("[itemprop=\"priceCurrency\"]");
                final Price xmlPrice = new Price();
                xmlPrice.setValue(new BigDecimal(htmlPrice.attr("content")));
                xmlPrice.setCurrency(htmlCurrency.attr("content"));
                smartphone.setPrice(xmlPrice);

                // url
                final Elements url = htmlArticle.select(".productAdditional [href]");
                smartphone.setUrl(url.attr("href"));

                report.getSmartphone().add(smartphone);
            }
        }

        // pôr report no tópico em formato xml
        final String xml = XmlHelper.toString(report);
        System.out.println(xml);
        // TODO tópico
    }

    private void getWebAddresses() throws IOException {
        this.webAddresses = Files.readAllLines(Paths.get("webAdresses.txt"), StandardCharsets.UTF_8);
        /*int cont=1;
         for(String s:webAddresses){
         System.out.println(cont+s);
         cont++;
         }*/
    }

}
