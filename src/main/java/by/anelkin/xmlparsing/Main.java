package by.anelkin.xmlparsing;

import by.anelkin.xmlparsing.builder.BuilderTariffSax;
import by.anelkin.xmlparsing.entity.Tariff;
import by.anelkin.xmlparsing.builder.BuilderTariffDom;
import by.anelkin.xmlparsing.builder.BuilderTariffStax;
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
        List<Tariff> listStax = new ArrayList<>();
        try {
            listStax = builderTariffVelcom.parse(new FileInputStream("src/main/resources/data/tariffs.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println(listStax);
//        System.out.println(listStax.size());

        System.out.println("====================");

        BuilderTariffDom builderTariffDom = new BuilderTariffDom();
        List<Tariff> listDom = new ArrayList<>();
        try {
            listDom = builderTariffDom.parse(new FileInputStream("src/main/resources/data/tariffs.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println(listDom);
//        System.out.println(listDom.size());


        System.out.println("====================");

        BuilderTariffSax builderTariffSax = new BuilderTariffSax();
        List<Tariff> listSax = new ArrayList<>();
        try {
            listSax = builderTariffSax.parse(new FileInputStream("src/main/resources/data/tariffs.xml"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        System.out.println(listSax);
//        System.out.println(listSax.size());


        System.out.println("======================");

        for (int i = 0; i < listSax.size(); i++) {
            if (!listSax.get(i).equals(listDom.get(i)) || !listSax.get(i).equals(listStax.get(i))) {
                System.out.println("NOT EQUALS");
                System.out.println(listSax.get(i));
                System.out.println(listStax.get(i));
                System.out.println(listDom.get(i));
            }
        }

        System.out.println(listSax.get(0).hashCode() == listDom.get(0).hashCode()
                && listSax.get(0).hashCode() == listStax.get(0).hashCode());


        System.out.println(listSax.get(0).equals(listDom.get(0))
                && listSax.get(0).equals(listStax.get(0)));
    }
}

