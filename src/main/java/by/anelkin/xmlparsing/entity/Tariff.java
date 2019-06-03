package by.anelkin.xmlparsing.entity;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

public class Tariff {
    private String id;
    private String name;
    private String operatorName;
    private BigDecimal payRoll;
    private BigDecimal smsPrice;
    private Map<CallPriceParameters, BigDecimal> callPrices;
    private Map<Parameters, String> parameters;
    private Date openDate;
    private Date closeDate;
    //default closeDate 01.01.2030
    private static final Date DEFAULT_CLOSE_DATE = new Date(1893448800000L);
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");

    public enum CallPriceParameters {
        INSIDENETWORK,
        OUTSIDENETWORK,
        ONLANDLINE,
        ONFAVORITENUMBER
    }

    public enum Parameters {
        TARIFICATION,
        ACTIVATIONPAYMENT,
        FAVORITENUMBERSAMOUNT
    }

    public Tariff(String id, String name, String operatorName, BigDecimal payRoll, Map<CallPriceParameters, BigDecimal> callPrices,
                  BigDecimal smsPrice, Map<Parameters, String> parameters, Date openDate, Date closeDate) {
        this.id = id;
        this.name = name;
        this.operatorName = operatorName;
        this.payRoll = payRoll;
        this.callPrices = callPrices;
        this.smsPrice = smsPrice;
        this.parameters = parameters;
        this.openDate = openDate;
        this.closeDate = closeDate == null ? DEFAULT_CLOSE_DATE : closeDate;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tariff{ID: " + id + " | Name: " + name + " | OperatorName: " + operatorName);
        builder.append(" | OpenDate: ").append(DATE_FORMAT.format(openDate));
        if (closeDate != null) {
            builder.append(" | CloseDate: ").append(DATE_FORMAT.format(closeDate));
        }
        builder.append(" | PayRoll: " + payRoll + " | CallPrices: " + callPrices + " | SmsPrice: " + smsPrice
                + " | Parameters: " + parameters + "\n");
        return builder.toString();
    }

    public String getId() {
        return id;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public BigDecimal getPayRoll() {
        return payRoll;
    }

    public void setPayRoll(BigDecimal payRoll) {
        this.payRoll = payRoll;
    }

    public Map<CallPriceParameters, BigDecimal> getCallPrices() {
        return callPrices;
    }

    public void setCallPrices(Map<CallPriceParameters, BigDecimal> callPrices) {
        this.callPrices = callPrices;
    }

    public BigDecimal getSmsPrice() {
        return smsPrice;
    }

    public void setSmsPrice(BigDecimal smsPrice) {
        this.smsPrice = smsPrice;
    }

    public Map<Parameters, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<Parameters, String> parameters) {
        this.parameters = parameters;
    }

    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }

    public Date getCloseDate() {
        return closeDate;
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }
}
