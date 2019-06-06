package by.anelkin.xmlparsing.builder;

import by.anelkin.xmlparsing.entity.Tariff;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static by.anelkin.xmlparsing.builder.BuilderTariff.*;
import static by.anelkin.xmlparsing.entity.Tariff.CallPriceParameters.*;
import static by.anelkin.xmlparsing.entity.Tariff.TariffParameters.*;

class SaxTariffHandler extends DefaultHandler {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private List<Tariff> tariffs;
    private Tariff tariff = new Tariff();
    private StringBuilder elementValueBuilder = new StringBuilder();
    private Map<Tariff.CallPriceParameters, BigDecimal> callPrices = new HashMap<>();
    private Map<Tariff.TariffParameters, String> parameters = new HashMap<>();

    public List<Tariff> getTariffs() {
        return tariffs;
    }

    @Override
    public void startDocument() {
        tariffs = new ArrayList<>();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equals("tariff")) {
            for (int i = 0; i < attributes.getLength(); i++) {
                String attributeValue = attributes.getValue(i);
                switch (TariffAttribute.valueOf(attributes.getQName(i).toUpperCase())) {
                    case ID:
                        tariff.setID(attributeValue);
                        break;
                    case NAME:
                        tariff.setName(attributeValue);
                        break;
                    case OPERATORNAME:
                        tariff.setOperatorName(attributeValue);
                        break;
                    case OPENDATE:
                        try {
                            tariff.setOpenDate(DATE_FORMAT.parse(attributeValue));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    case CLOSEDATE:
                        try {
                            tariff.setCloseDate(DATE_FORMAT.parse(attributeValue));
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        break;
                    default:
                        throw new RuntimeException("Wrong attribute name: " + attributes.getQName(i));
                }
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName){
        String elementValue = elementValueBuilder.toString().trim();
        switch (ParserEnum.valueOf(qName.toUpperCase())) {
            case TARIFF:
                tariffs.add(tariff);
                tariff = new Tariff();
                callPrices = new HashMap<>();
                parameters = new HashMap<>();
                break;
            case SMSPRICE:
                tariff.setSmsPrice(new BigDecimal(elementValue));
                break;
            case PAYROLL:
                tariff.setPayRoll(new BigDecimal(elementValue));
                break;
            case ONFAVORITENUMBER:
                callPrices.put(ON_FAVORITE_NUMBER, new BigDecimal(elementValue));
                break;
            case OUTSIDENETWORK:
                callPrices.put(OUTSIDE_NETWORK, new BigDecimal(elementValue));
                break;
            case INSIDENETWORK:
                callPrices.put(INSIDE_NETWORK, new BigDecimal(elementValue));
                break;
            case ONLANDLINE:
                callPrices.put(ON_LAND_LINE, new BigDecimal(elementValue));
                break;
            case TARIFICATION:
                parameters.put(TARIFICATION, elementValue);
                break;
            case FAVORITENUMBERSAMOUNT:
                parameters.put(FAVORITE_NUMBERS_AMOUNT, elementValue);
                break;
            case ACTIVATIONPAYMENT:
                parameters.put(ACTIVATION_PAYMENT, elementValue);
                break;
            case PARAMETERS:
                tariff.setTariffParameters(parameters);
                break;
            case CALLPRICES:
                tariff.setCallPrices(callPrices);
                break;
            case TARIFFS:
                break;
            default:
                throw new RuntimeException("Wrong tag-name: " + qName);
        }
        elementValueBuilder = new StringBuilder();
    }

    // accumulating value in stringBuilder,
    // because some elements call method characters twice or more times:
    @Override
    public void characters(char[] ch, int start, int length) {
        elementValueBuilder.append(new String(ch, start, length));
    }


}
