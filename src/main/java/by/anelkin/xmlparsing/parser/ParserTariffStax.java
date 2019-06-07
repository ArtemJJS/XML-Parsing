package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.entity.Tariff;
import org.apache.log4j.Logger;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.anelkin.xmlparsing.entity.Tariff.*;

public class ParserTariffStax implements ParserTariff {
    private static final Logger logger = Logger.getLogger(Tariff.class);
    private String id;
    private String name;
    private String operatorName;
    private Date openDate;
    private Date closeDate;
    private BigDecimal payRoll;
    private BigDecimal smsPrice;
    private Map<CallPriceParameters, BigDecimal> callPrices = new HashMap<>();
    private Map<TariffParameters, String> parameters = new HashMap<>();
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    @Override
    public List<Tariff> parse(InputStream inputStream) {
        logger.info("Start parsing with StAX-parser.");
        List<Tariff> tariffs = new ArrayList<>();
        XMLInputFactory inputFactory = XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader = inputFactory.createXMLStreamReader(inputStream);
            tariffs.addAll(doParsing(reader));
        } catch (XMLStreamException e) {
            throw new RuntimeException(e);
        }
        logger.info("StAX completed parsing.");
        return tariffs;
    }

    private List<Tariff> doParsing(XMLStreamReader reader) throws XMLStreamException {
        List<Tariff> tariffs = new ArrayList<>();
        while (reader.hasNext()) {
            String tagName;
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT: {
                    tagName = reader.getLocalName().toUpperCase();
                    switch (ParserEnum.valueOf(tagName)) {
                        case PAYROLL:
                            payRoll = new BigDecimal(reader.getElementText());
                            break;
                        case SMSPRICE:
                            smsPrice = new BigDecimal(reader.getElementText());
                            break;
                        case ONLANDLINE:
                            callPrices.put(CallPriceParameters.ON_LAND_LINE, new BigDecimal(reader.getElementText()));
                            break;
                        case ONFAVORITENUMBER:
                            callPrices.put(CallPriceParameters.ON_FAVORITE_NUMBER, new BigDecimal(reader.getElementText()));
                            break;
                        case OUTSIDENETWORK:
                            callPrices.put(CallPriceParameters.OUTSIDE_NETWORK, new BigDecimal(reader.getElementText()));
                            break;
                        case INSIDENETWORK:
                            callPrices.put(CallPriceParameters.INSIDE_NETWORK, new BigDecimal(reader.getElementText()));
                            break;
                        case ACTIVATIONPAYMENT:
                            parameters.put(TariffParameters.ACTIVATION_PAYMENT, reader.getElementText());
                            break;
                        case FAVORITENUMBERSAMOUNT:
                            parameters.put(TariffParameters.FAVORITE_NUMBERS_AMOUNT, reader.getElementText());
                            break;
                        case TARIFICATION:
                            parameters.put(TariffParameters.TARIFICATION, reader.getElementText());
                            break;
                        case TARIFF:
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                String attributeName = reader.getAttributeLocalName(i).toUpperCase();
                                switch (TariffAttribute.valueOf(attributeName)) {
                                    case ID:
                                        id = reader.getAttributeValue(i);
                                        break;
                                    case NAME:
                                        name = reader.getAttributeValue(i);
                                        break;
                                    case OPERATORNAME:
                                        operatorName = reader.getAttributeValue(i);
                                        break;
                                    case OPENDATE:
                                        try {
                                            openDate = DATE_FORMAT.parse(reader.getAttributeValue(i));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case CLOSEDATE:
                                        try {
                                            closeDate = DATE_FORMAT.parse(reader.getAttributeValue(i));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                            break;
                            //empty cases to prevent unnecessary default case calling:
                        case TARIFFS:
                            break;
                        case CALLPRICES:
                            break;
                        case PARAMETERS:
                            break;
                        default:
                            throw new RuntimeException("Wrong tag name " + tagName);
                    }
                    break;
                }

                case XMLStreamConstants.END_ELEMENT: {
                    if (reader.getLocalName().equals("tariff")) {
                        tariffs.add(getTariff());
                        reset();
                    }
                }
            }
        }
        return tariffs;
    }


    private void reset() {
        name = null;
        operatorName = null;
        openDate = null;
        closeDate = null;
        payRoll = null;
        callPrices = new HashMap<>();
        smsPrice = null;
        parameters = new HashMap<>();
        id = null;
    }


    private Tariff getTariff() {
        return new Tariff(id, name, operatorName, payRoll,
                callPrices, smsPrice, parameters, openDate, closeDate);
    }
}
