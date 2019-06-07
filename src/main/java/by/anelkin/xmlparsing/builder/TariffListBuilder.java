package by.anelkin.xmlparsing.builder;

import by.anelkin.xmlparsing.entity.Tariff;
import by.anelkin.xmlparsing.parser.ParserTariff;
import by.anelkin.xmlparsing.parser.ParserTariffDom;
import by.anelkin.xmlparsing.parser.ParserTariffSax;
import by.anelkin.xmlparsing.parser.ParserTariffStax;

import java.io.InputStream;
import java.util.List;

public class TariffListBuilder {
    public enum ParserType {
        DOM,
        StAX,
        SAX
    }

    public List<Tariff> buildTariffList(InputStream inputStream, ParserType parserType) {
        ParserTariff parser = null;
        switch (parserType) {
            case DOM:
                parser = new ParserTariffDom();
                break;
            case SAX:
                parser = new ParserTariffSax();
                break;
            case StAX:
                parser = new ParserTariffStax();
                break;
            default:
                throw new RuntimeException("Wrong parser type " + parserType);
        }

        return parser.parse(inputStream);
    }
}
