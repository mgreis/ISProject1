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

/**
 *
 * @author Mário
 */
public class Engine extends Thread {

    private ArrayList<SmartPhone> smartPhoneList = new ArrayList<>();
    private List<String> brandAndModel;
    private List<String> webAddresses;

    public void run() {
        this.getSmartPhonesFromFile();
        this.getWebAddresses();
        this.crawl();
    }

    private void crawl() {
        int cont = 1;
        try {

            for (String s : webAddresses) {

                //System.out.println(s);
                File input = new File("temp.html");
                if (!input.exists()) {
                    input.createNewFile();
                }

                Document doc = Jsoup.connect(s).timeout(20000).get();

                for (String s2 : brandAndModel) {

                    Elements elements1 = doc.select("article:contains(" + s2 + ") li");
                    Elements elements2 = doc.select("article:contains(" + s2 + ") a");
                    if (!elements1.isEmpty() && !elements2.isEmpty()) {
                        SmartPhone phone = new SmartPhone();
                       // System.out.println("attrib: "+elements2.get(0).attr("href"));
                        if (elements1.size() >0) {
                            phone.setProcessor(elements1.get(0).text());
                        }
                        if (elements1.size() >1) {
                            phone.setScreenTech(elements1.get(1).text());
                        }
                        if (elements1.size() >2) {
                            phone.setScreenSize(elements1.get(2).text());
                        }
                        if (elements1.size() >3) {
                            phone.setOtherInfo(elements1.get(3).text());
                        }
                        if (elements2.size() >3) {
                            phone.setBestPrice(elements2.get(3).text());
                        }
                        if (elements2.size() >4) {
                            phone.setOurPrice(elements2.get(4).text());
                        }
                        if (elements2.size() > 0){
                            phone.setAddress(elements2.get(0).attr("href"));
                        }
                        if (elements2.size() >1) {
                            String[] auxBrand = elements2.get(1).text().split(" ", 2);
                            phone.setBrand(auxBrand[0]);
                            phone.setModel(auxBrand[1]);
                            phone.printPhone();
                            
                            smartPhoneList.add(phone);
                        }

                    }

                }
                cont++;
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
