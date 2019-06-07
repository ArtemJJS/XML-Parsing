package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.entity.Tariff;
import org.apache.log4j.Logger;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class ParserTariffSax implements ParserTariff {
    private static final Logger logger = Logger.getLogger(Tariff.class);

    @Override
    public List<Tariff> parse(InputStream inputStream) {
        logger.info("Start parsing with SAX-parser.");
        List<Tariff> list;
        SAXParserFactory parserFactory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = parserFactory.newSAXParser();
            SaxTariffHandler handler = new SaxTariffHandler();
            parser.parse(inputStream, handler);
            list = handler.getTariffs();
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException(e);
        }

        logger.info("Sax-parser completed parsing.");
        return list;
    }
}
