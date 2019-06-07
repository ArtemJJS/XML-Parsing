package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.entity.Tariff;
import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static by.anelkin.xmlparsing.entity.Tariff.*;
import static by.anelkin.xmlparsing.entity.Tariff.CallPriceParameters.*;
import static by.anelkin.xmlparsing.entity.Tariff.TariffParameters.*;

public class ParserTariffDom implements ParserTariff {
    private static final Logger logger = Logger.getLogger(Tariff.class);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override

    public List<Tariff> parse(InputStream inputStream) {
        logger.info("Start parsing with DOM-parser.");
        List<Tariff> tariffs = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element root = document.getDocumentElement();
            tariffs.addAll(buildTariffs(root));
        } catch (ParserConfigurationException | SAXException | IOException e) {
            throw new RuntimeException("e");
        }
        logger.info("DOM-parser completed parsing.");
        return tariffs;
    }

    private List<Tariff> buildTariffs(Element root) {
        List<Tariff> tariffs = new ArrayList<>();
        NodeList elements = root.getElementsByTagName("tariff");
        for (int i = 0; i < elements.getLength(); i++) {
            Tariff tariff = new Tariff();
            Element element = (Element) elements.item(i);

            tariff.setID(element.getAttribute("id"));
            tariff.setName(element.getAttribute("name"));
            tariff.setOperatorName(element.getAttribute("operatorName"));
            tariff.setSmsPrice(new BigDecimal(getChildByName(element, "smsPrice").getTextContent()));
            tariff.setPayRoll(new BigDecimal(getChildByName(element, "payRoll").getTextContent()));
            try {
                tariff.setOpenDate(DATE_FORMAT.parse(element.getAttribute("openDate")));

                String closeDateString = element.getAttribute("closeDate");
                if (!closeDateString.isEmpty()) {
                    tariff.setCloseDate(DATE_FORMAT.parse(closeDateString));
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            
            Element callPricesElement = getChildByName(element, "callPrices");
            tariff.setCallPrices(fillCallPricesMap(callPricesElement));

            Element parametersElement = getChildByName(element, "parameters");
            tariff.setTariffParameters(fillTariffParametersMap(parametersElement));

            tariffs.add(tariff);
        }

        return tariffs;
    }


    private Element getChildByName(Element element, String tagName) {
        NodeList childs = element.getElementsByTagName(tagName);
        Element child = (Element) childs.item(0);
        return child;
    }

    private Map<CallPriceParameters, BigDecimal> fillCallPricesMap(Element callPricesElement) {
        Map<CallPriceParameters, BigDecimal> callPrices = new HashMap<>();

        Element insideNetwork = getChildByName(callPricesElement, "insideNetwork");
        callPrices.put(INSIDE_NETWORK, new BigDecimal(insideNetwork.getTextContent()));

        Element outsideNetwork = getChildByName(callPricesElement, "outsideNetwork");
        callPrices.put(OUTSIDE_NETWORK, new BigDecimal(outsideNetwork.getTextContent()));

        Element onLandLine = getChildByName(callPricesElement, "onLandLine");
        callPrices.put(ON_LAND_LINE, new BigDecimal(onLandLine.getTextContent()));

        Element onFavoriteNumber = getChildByName(callPricesElement, "onFavoriteNumber");
        if (onFavoriteNumber != null) {
            callPrices.put(ON_FAVORITE_NUMBER, new BigDecimal(onFavoriteNumber.getTextContent()));
        }

        return callPrices;
    }

    private Map<TariffParameters, String> fillTariffParametersMap(Element parametersElement) {
        Map<TariffParameters, String> parameters = new HashMap<>();

        Element tarification = getChildByName(parametersElement, "tarification");
        if (tarification != null) {
            parameters.put(TARIFICATION, tarification.getTextContent());
        }
        Element activationPayment = getChildByName(parametersElement, "activationPayment");
        if (activationPayment != null) {
            parameters.put(ACTIVATION_PAYMENT, activationPayment.getTextContent());
        }
        Element favoriteNumbersAmount = getChildByName(parametersElement, "favoriteNumbersAmount");
        if (favoriteNumbersAmount != null) {
            parameters.put(FAVORITE_NUMBERS_AMOUNT, favoriteNumbersAmount.getTextContent());
        }

        return parameters;
    }
}