package by.anelkin.xmlparsing;

import by.anelkin.xmlparsing.entity.Tariff;
import by.anelkin.xmlparsing.parser.BuilderTariffStax;
import by.anelkin.xmlparsing.validator.XMLFileValidator;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;


public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class);
        logger.debug("Start of Main");

        XMLFileValidator validator = new XMLFileValidator();
        System.out.println(validator.validate("src/main/resources/data/tariffs.xml"));

        System.out.println("Проверка завершена.");

        BuilderTariffStax builderTariffVelcom = new BuilderTariffStax();
        List<Tariff> tariffs = new ArrayList<>();
        try {
            tariffs = builderTariffVelcom.parse(new FileInputStream("src/main/resources/data/tariffs.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println(tariffs);
        System.out.println(tariffs.size());

        System.out.println("====================");

    }
}

