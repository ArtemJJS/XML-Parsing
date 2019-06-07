package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.entity.Tariff;

import java.io.InputStream;
import java.util.List;

public interface ParserTariff {
    List<Tariff> parse(InputStream inputStream);

    enum ParserEnum {
        TARIFFS,
        TARIFF,
        PAYROLL,
        CALLPRICES,
        SMSPRICE,
        PARAMETERS,
        INSIDENETWORK,
        OUTSIDENETWORK,
        ONLANDLINE,
        ONFAVORITENUMBER,
        FAVORITENUMBERSAMOUNT,
        TARIFICATION,
        ACTIVATIONPAYMENT
    }

    enum TariffAttribute {
        ID,
        NAME,
        OPERATORNAME,
        OPENDATE,
        CLOSEDATE
    }

}
