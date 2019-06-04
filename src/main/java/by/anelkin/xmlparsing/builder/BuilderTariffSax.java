package by.anelkin.xmlparsing.builder;

import by.anelkin.xmlparsing.entity.Tariff;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class BuilderTariffSax implements BuilderTariff{

    @Override
    public List<Tariff> parse(InputStream inputStream) {
       List<Tariff> list = new ArrayList<>();
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try {
           SAXParser parser = parserFactory.newSAXParser();
           SaxTariffHandler handler = new SaxTariffHandler();
           parser.parse(inputStream, handler);
            list = handler.getTariffs();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return list;
    }
}
