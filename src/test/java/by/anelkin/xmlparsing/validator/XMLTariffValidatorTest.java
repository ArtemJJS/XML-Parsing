package by.anelkin.xmlparsing.validator;

import org.testng.Assert;
import org.testng.annotations.Test;

public class XMLTariffValidatorTest {
    private XMLTariffValidator validator = new XMLTariffValidator();


    @Test
    public void validateShouldBeTrue() {
        boolean expected = true;
        boolean actual = validator.validate("src/main/resources/data/test_data/test_tariffs.xml");

        Assert.assertEquals(actual, expected);
    }

    @Test
    public void validateShouldBeFalse() {
        boolean expected = false;
        boolean actual = validator.validate("src/main/resources/data/test_data/test_tariffs_incorrect.xml");

        Assert.assertEquals(actual, expected);
    }
}