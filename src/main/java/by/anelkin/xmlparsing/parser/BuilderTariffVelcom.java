package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.instance.Tariff;
import by.anelkin.xmlparsing.instance.TariffVelcom;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.InputStream;
import java.io.StringReader;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.anelkin.xmlparsing.instance.TariffVelcom.*;

public class BuilderTariffVelcom implements BuilderTariff {
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
        while (reader.hasNext()) {
            String tagName;
            int type = reader.next();
            switch (type) {
                case XMLStreamConstants.START_ELEMENT:
                    tagName = reader.getLocalName().toUpperCase();
                    switch (ParserEnum.valueOf(tagName)) {
                        case PAYROLL: {
                            payRoll = new BigDecimal(reader.getElementText());
                            System.out.println("payRoll: " + payRoll);
                            break;
                        }
                        case SMSPRICE: {
                            smsPrice = new BigDecimal(reader.getElementText());
                            System.out.println("SmsPrice: " + smsPrice);
                            break;
                        }
                        case TARIFF: {
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                String attributeName = reader.getAttributeLocalName(i).toUpperCase();
                                switch (TariffAttribute.valueOf(attributeName)) {
                                    case NAME: {
                                        name = reader.getAttributeValue(0);
                                        System.out.println("Name: " + name);
                                        break;
                                    }
                                    case OPERATORNAME: {
                                        operatorName = reader.getAttributeValue(1);
                                        System.out.println("operatorName: " + operatorName);
                                        break;
                                    }
                                    case OPENDATE: {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
                                        try {
                                            openDate = dateFormat.parse(reader.getAttributeValue(2));
                                            System.out.println("OpenDate: " + openDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                        break;
                                    }
                                    case CLOSEDATE: {
                                        SimpleDateFormat dateFormat = new SimpleDateFormat("YYYY-MM-DD");
                                        try {
                                            closeDate = dateFormat.parse(reader.getAttributeValue(3));
                                            System.out.println("CloseDate: " + closeDate);
                                        } catch (ParseException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                            break;
                        }
                        case CALLPRICES: {
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                String attributeName = reader.getAttributeLocalName(i).toUpperCase();
                                switch (CallPriceParameters.valueOf(attributeName)) {
                                    case ONLANDLINE: {
                                        callPrices.put(CallPriceParameters.ONLANDLINE, new BigDecimal(reader.getAttributeValue(i)));
                                        break;
                                    }
                                    case ONFAVORITENUMBER: {
                                        callPrices.put(CallPriceParameters.ONFAVORITENUMBER, new BigDecimal(reader.getAttributeValue(i)));
                                        break;
                                    }
                                    case OUTSIDENETWORK: {
                                        callPrices.put(CallPriceParameters.OUTSIDENETWORK, new BigDecimal(reader.getAttributeValue(i)));
                                        break;
                                    }
                                    case INSIDENETWORK: {
                                        callPrices.put(CallPriceParameters.INSIDENETWORK, new BigDecimal(reader.getAttributeValue(i)));
                                    }
                                }
                            }
                            System.out.println(callPrices);
                            break;
                        }
                        case PARAMETERS: {
                            for (int i = 0; i < reader.getAttributeCount(); i++) {
                                String attributeName = reader.getAttributeLocalName(i).toUpperCase();
                                switch (Parameters.valueOf(attributeName)) {
                                    case ACTIVATIONPAYMENT:
                                        parameters.put(Parameters.ACTIVATIONPAYMENT, reader.getAttributeValue(i));
                                        break;
                                    case FAVORITENUMBERSAMOUNT:
                                        parameters.put(Parameters.FAVORITENUMBERSAMOUNT, reader.getAttributeValue(i));
                                        break;
                                    case TARIFICATION:
                                        parameters.put(Parameters.TARIFICATION, reader.getAttributeValue(i));
                                }
                            }
                            System.out.println(parameters);
                        }
                    }
                    break;
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
        String name = null;
        String operatorName = null;
        Date openDate = null;
        Date closeDate = null;
        BigDecimal payRoll = null;
        Map<CallPriceParameters, BigDecimal> callPrices = new HashMap<>();
        BigDecimal smsPrice = null;
        Map<TarifParameters, String> parameters = new HashMap<>();
    }

    @Override
    public Tariff getTariff() {
        Tariff tariff = new TariffVelcom(name, operatorName, payRoll, callPrices, smsPrice, parameters);
        return tariff;
    }
}
