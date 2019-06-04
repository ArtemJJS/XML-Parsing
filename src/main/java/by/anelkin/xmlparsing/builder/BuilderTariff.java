package by.anelkin.xmlparsing.builder;

import by.anelkin.xmlparsing.entity.Tariff;

import java.io.InputStream;
import java.util.List;

public interface BuilderTariff {
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
