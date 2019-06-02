package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.instance.Tariff;

import java.io.InputStream;
import java.util.List;

public interface BuilderTariff {
    void reset();

    // TODO: 6/2/2019 сделать parameters мапой? ключ enum и value его значение???
    // TODO: 6/2/2019 может просто каждый параметр отдельным полем?

    List<Tariff> parse(InputStream inputStream);

    Tariff getTariff();

    public enum ParserEnum {
        //it doesn't works without TARIFFS!
        TARIFFS,
        TARIFF,
        PAYROLL,
        CALLPRICES,
        SMSPRICE,
        PARAMETERS
    }

    public enum TariffAttribute {
        NAME,
        OPERATORNAME,
        OPENDATE,
        CLOSEDATE
    }


}
