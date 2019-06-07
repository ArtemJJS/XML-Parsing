package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.dataprovider.TestDataProvider;
import by.anelkin.xmlparsing.entity.Tariff;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class ParserTariffDomTest {
    private ParserTariffDom parserDom = new ParserTariffDom();

    @Test(dataProviderClass = TestDataProvider.class, dataProvider = "getTestParserData")
    public void testParse(int index, Tariff expected) throws FileNotFoundException {
        Tariff actual =
                parserDom.parse(new FileInputStream("src/main/resources/data/test_data/test_tariffs.xml")).get(index);


        Assert.assertEquals(actual, expected);
    }
}