package by.anelkin.xmlparsing;

import by.anelkin.xmlparsing.validator.XMLFileValidator;
import org.apache.log4j.Logger;


public class Main {
    public static void main(String[] args) {
        Logger logger = Logger.getLogger(Main.class);
        logger.debug("Start of Main");

        XMLFileValidator validator = new XMLFileValidator();
        System.out.println(validator.validate("src/main/resources/data/tariffs.xml"));

        System.out.println("Проверка завершена.");


    }
}

