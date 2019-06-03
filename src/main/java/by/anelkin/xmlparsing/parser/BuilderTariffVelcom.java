package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.instance.Tariff;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.anelkin.xmlparsing.instance.Tariff.*;

public class BuilderTariffVelcom implements BuilderTariff {
    private String id;
    private String name;
    private String operatorName;
    private Date openDate;
    private Date closeDate;
    private BigDecimal payRoll;
    private Map<CallPriceParameters, BigDecimal> callPrices = new HashMap<>();
    private BigDecimal smsPrice;
    // TODO: 6/2/2019 нормально ли сюда стрин вставлять?:
    private Map<Parameters, String> parameters = new HashMap<>();


    @Override
    public List<Tariff> parse(InputStream inputStream) {
        List<Tariff> tariffs = new ArrayList<>();
        XMLInputFactory inputFactory =
                XMLInputFactory.newInstance();
        try {
            XMLStreamReader reader =
                    inputFactory.createXMLStreamReader(inputStream);
            tariffs.addAll(doParsing(reader));
        } catch (XMLStreamException e) {
            e.printStackTrace();
        }
        return tariffs;
    }

    private List<Tariff> doParsing(XMLStreamReader reader) throws XMLStreamException {
        List<Tariff> tariffs = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                            callPrices.put(CallPriceParameters.ONLANDLINE, new BigDecimal(reader.getElementText()));
                            break;
                        case ONFAVORITENUMBER:
                            callPrices.put(CallPriceParameters.ONFAVORITENUMBER, new BigDecimal(reader.getElementText()));
                            break;
                        case OUTSIDENETWORK:
                            callPrices.put(CallPriceParameters.OUTSIDENETWORK, new BigDecimal(reader.getElementText()));
                            break;
                        case INSIDENETWORK:
                            callPrices.put(CallPriceParameters.INSIDENETWORK, new BigDecimal(reader.getElementText()));
                            break;
                        case ACTIVATIONPAYMENT:
                            parameters.put(Parameters.ACTIVATIONPAYMENT, reader.getElementText());
                            break;
                        case FAVORITENUMBERSAMOUNT:
                            parameters.put(Parameters.FAVORITENUMBERSAMOUNT, reader.getElementText());
                            break;
                        case TARIFICATION:
                            parameters.put(Parameters.TARIFICATION, reader.getElementText());
                            break;
                        case TARIFF: {
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
                                            openDate = dateFormat.parse(reader.getAttributeValue(i));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    case CLOSEDATE:
                                        try {
                                            closeDate = dateFormat.parse(reader.getAttributeValue(i));
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                }
                            }
                        }
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

    @Override
    public void reset() {
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

    @Override
    public Tariff getTariff() {
        return new Tariff(id, name, operatorName, payRoll,
                callPrices, smsPrice, parameters, openDate, closeDate);
    }
}
