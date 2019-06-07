package by.anelkin.xmlparsing;

import by.anelkin.xmlparsing.builder.TariffListBuilder;
import by.anelkin.xmlparsing.builder.TariffListBuilder.ParserType;
import by.anelkin.xmlparsing.parser.ParserTariffSax;
import by.anelkin.xmlparsing.entity.Tariff;
import by.anelkin.xmlparsing.parser.ParserTariffDom;
import by.anelkin.xmlparsing.parser.ParserTariffStax;
import by.anelkin.xmlparsing.validator.XMLTariffValidator;
import org.apache.log4j.Logger;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import static by.anelkin.xmlparsing.builder.TariffListBuilder.ParserType.*;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("====================");

        TariffListBuilder builder = new TariffListBuilder();
        List<Tariff> listTariff = builder.buildTariffList(new FileInputStream("src/main/resources/data/tariffs.xml"), SAX);
        System.out.println(listTariff);
        System.out.println(listTariff.size());



    }
}

