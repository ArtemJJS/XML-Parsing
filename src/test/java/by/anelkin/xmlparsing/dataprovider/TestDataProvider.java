package by.anelkin.xmlparsing.dataprovider;

import by.anelkin.xmlparsing.entity.Tariff;
import org.testng.annotations.DataProvider;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;

import static by.anelkin.xmlparsing.entity.Tariff.*;
import static by.anelkin.xmlparsing.entity.Tariff.CallPriceParameters.*;
import static by.anelkin.xmlparsing.entity.Tariff.TariffParameters.*;

public class TestDataProvider {
    private Map<CallPriceParameters, BigDecimal> callPrices_1 = Map.of(
            INSIDE_NETWORK, new BigDecimal("0.1"),
            OUTSIDE_NETWORK, new BigDecimal("0.2"),
            ON_LAND_LINE, new BigDecimal("0.25"),
            ON_FAVORITE_NUMBER, new BigDecimal("0")
    );
    private Map<TariffParameters, String> tariffParameters_1 = Map.ofEntries(
            Map.entry(FAVORITE_NUMBERS_AMOUNT, "3"),
            Map.entry(TARIFICATION, "12"),
            Map.entry(ACTIVATION_PAYMENT, "10")
    );

    private Map<CallPriceParameters, BigDecimal> callPrices_2 = Map.of(
        INSIDE_NETWORK, new BigDecimal("0"),
        OUTSIDE_NETWORK, new BigDecimal("0.5"),
        ON_LAND_LINE, new BigDecimal("0.5")
);
    private Map<TariffParameters, String> tariffParameters_2 = Map.of(
            FAVORITE_NUMBERS_AMOUNT, "0",
            TARIFICATION, "1",
            ACTIVATION_PAYMENT, "0"
    );


    //initializing all from xml (no defaults):
    private Tariff testTariff_1 = new Tariff("VL100101", "Privet Light", "Velcom", new BigDecimal(14),
            callPrices_1, new BigDecimal("0.05"), tariffParameters_1, new Date(1428699600000L), new Date(1502312400000L));

    //all available optional must be parsed with default values:
    private Tariff testTariff_2 = new Tariff("VL100102", "Privet Active", "Velcom", new BigDecimal(5),
            callPrices_2, new BigDecimal("0.1"), tariffParameters_2, new Date(1483221600000L), new Date(1893448800000L));



    @DataProvider(name = "getTestParserData")
    public Object[][] getTestParserData(){
        return new Object[][]{
                {0, testTariff_1},
                {1, testTariff_2}
        };
    }
}
