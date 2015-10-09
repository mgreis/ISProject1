/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webcrawler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.*;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import is.project1.xml.*;
import java.math.BigDecimal;

/**
 *
 * @author Mário
 */
public class Engine extends Thread {

    private ArrayList<SmartPhone> smartPhoneList = new ArrayList<>();
    private List<String> brandAndModel;
    private List<String> webAddresses;
    private Report report = new Report();

    public void run() {
        this.getSmartPhonesFromFile();
        this.getWebAddresses();
        this.crawl();
    }

    private void crawl() {

        try {

            for (String s : webAddresses) {

                //System.out.println(s);
                File input = new File("temp.html");
                if (!input.exists()) {
                    input.createNewFile();
                }

                Document doc = Jsoup.connect(s).timeout(20000).get();

                Elements exp = doc.select("article");

                for (Element article : exp) {
                    Smartphone phone = new Smartphone();
                    Elements description3 = article.select("[itemprop=\"description\"] li");

                    // title
                    Elements title = article.select(".productAdditional [href]");
                    phone.setDetails(title.text());

                    // descrition
                    for (Element desc : description3) {
                        phone.getDescription().add(desc.text());

                    }

                    // price
                    Money money = new Money();
                    Elements currentPrice = article.select("[itemprop=\"price\"]");
                    money.setValue(new BigDecimal(currentPrice.attr("content")));

                    Elements currency = article.select("[itemprop=\"priceCurrency\"]");
                    money.setCurrency(currency.attr("content"));
                    phone.setPrice(money);

                    //details
                    Elements url = article.select(".productAdditional [href]");
                    phone.setDetails(url.attr("href"));

                    report.getSmartphone().add(phone);
                }

            }

        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("not found");
        }

        // img with src ending .png
    }

    private void getSmartPhonesFromFile() {

        try {
            brandAndModel = Files.readAllLines(Paths.get("smartphones.txt"), StandardCharsets.UTF_8);
            /*int cont=1;
             for(String s:lines){
             System.out.println(cont+s);
             cont++;
             }*/

        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("smartphones.txt not found.");
        }

    }

    private void getWebAddresses() {

        try {
            webAddresses = Files.readAllLines(Paths.get("webAdresses.txt"), StandardCharsets.UTF_8);
            /*int cont=1;
             for(String s:webAddresses){
             System.out.println(cont+s);
             cont++;
             }*/

        } catch (IOException ex) {
            Logger.getLogger(Engine.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("webAdresses.txt not found.");
        }

    }

}
