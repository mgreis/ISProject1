
import generated.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Flávio
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.out.println("Usage: main <input file> <output file>");
        }

        // xml para objecto
        FileReader reader = new FileReader(args[0]);
        JAXBContext context = JAXBContext.newInstance(Report.class, ObjectFactory.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();
        Report report = (Report) unmarshaller.unmarshal(reader);
        reader.close();

        // objecto para xml
        FileWriter writer = new FileWriter(args[1]);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        marshaller.marshal(report, writer);
        writer.close();
    }

}
