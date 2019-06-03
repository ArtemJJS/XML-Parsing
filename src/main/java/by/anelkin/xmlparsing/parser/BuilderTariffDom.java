package by.anelkin.xmlparsing.parser;

import by.anelkin.xmlparsing.entity.Tariff;
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
import static by.anelkin.xmlparsing.entity.Tariff.Parameters.*;

public class BuilderTariffDom implements BuilderTariff {
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    @Override

    public List<Tariff> parse(InputStream inputStream) {
        List<Tariff> tariffs = new ArrayList<>();
        try {
            DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder documentBuilder = builderFactory.newDocumentBuilder();
            Document document = documentBuilder.parse(inputStream);
            Element root = document.getDocumentElement();
            tariffs.addAll(buildTariffs(root));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
            try {
                tariff.setOpenDate(DATE_FORMAT.parse(element.getAttribute("openDate")));

                String closeDateString = element.getAttribute("closeDate");
                Date closeDate = closeDateString.isEmpty() ? null : DATE_FORMAT.parse(closeDateString);
                //if closeDate == null, it will be changed to default inside setCloseDate():
                tariff.setCloseDate(closeDate);
            } catch (ParseException e) {
                e.printStackTrace();
            }

            tariff.setSmsPrice(new BigDecimal(getChildByName(element, "smsPrice").getTextContent()));
            tariff.setPayRoll(new BigDecimal(getChildByName(element, "payRoll").getTextContent()));

            Element callPricesElement = getChildByName(element, "callPrices");
            tariff.setCallPrices(fillCallPrices(callPricesElement));

            Element parametersElement = getChildByName(element, "parameters");
            tariff.setParameters(fillParameters(parametersElement));

            tariffs.add(tariff);
        }

        return tariffs;
    }


    private Element getChildByName(Element element, String tagName) {
        NodeList childs = element.getElementsByTagName(tagName);
        Element child = (Element) childs.item(0);
        return child;
    }

    private Map<CallPriceParameters, BigDecimal> fillCallPrices(Element callPricesElement) {
        Map<CallPriceParameters, BigDecimal> callPrices = new HashMap<>();

        Element insideNetwork = getChildByName(callPricesElement, "insideNetwork");
        callPrices.put(INSIDENETWORK, new BigDecimal(insideNetwork.getTextContent()));

        Element outsideNetwork = getChildByName(callPricesElement, "outsideNetwork");
        callPrices.put(OUTSIDENETWORK, new BigDecimal(outsideNetwork.getTextContent()));

        Element onLandLine = getChildByName(callPricesElement, "onLandLine");
        callPrices.put(ONLANDLINE, new BigDecimal(onLandLine.getTextContent()));

        Element onFavoriteNumber = getChildByName(callPricesElement, "onFavoriteNumber");
        if (onFavoriteNumber != null) {
            callPrices.put(ONFAVORITENUMBER, new BigDecimal(onFavoriteNumber.getTextContent()));
        }

        return callPrices;
    }

    private Map<Parameters, String> fillParameters(Element parametersElement) {
        Map<Parameters, String> parameters = new HashMap<>();

        Element tarification = getChildByName(parametersElement, "tarification");
        if (tarification != null) {
            parameters.put(TARIFICATION, tarification.getTextContent());
        }
        Element activationPayment = getChildByName(parametersElement, "activationPayment");
        if (activationPayment != null) {
            parameters.put(ACTIVATIONPAYMENT, activationPayment.getTextContent());
        }
        Element favoriteNumbersAmount = getChildByName(parametersElement, "favoriteNumbersAmount");
        if (favoriteNumbersAmount != null) {
            parameters.put(FAVORITENUMBERSAMOUNT, favoriteNumbersAmount.getTextContent());
        }

        return parameters;
    }
}