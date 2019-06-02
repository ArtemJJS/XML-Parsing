package by.anelkin.xmlparsing.instance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Tariff {
    private String name;
    private String operatorName;
    private BigDecimal payRoll;
    private List<BigDecimal> callPrices;
    private BigDecimal smsPrice;
    // TODO: 6/1/2019 как тут избавиться от object??? внутри int, double(BigDecimal) и enum
    // TODO: 6/1/2019 поставил BigDecimal, но там два инта и один BigDecimal
    private List<BigDecimal> parameters;
    //optional:
    private Date openDate;
    private Date closeDate;

    public Tariff(String name, String operatorName, BigDecimal payRoll, List<BigDecimal> callPrices,
                  BigDecimal smsPrice, List<BigDecimal> parameters) {
        this.name = name;
        this.operatorName = operatorName;
        this.payRoll = payRoll;
        this.callPrices = callPrices;
        this.smsPrice = smsPrice;
        this.parameters = parameters;
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

    public List<BigDecimal> getCallPrices() {
        return callPrices;
    }

    public void setCallPrices(List<BigDecimal> callPrices) {
        this.callPrices = callPrices;
    }

    public BigDecimal getSmsPrice() {
        return smsPrice;
    }

    public void setSmsPrice(BigDecimal smsPrice) {
        this.smsPrice = smsPrice;
    }

    public List<BigDecimal> getParameters() {
        return parameters;
    }

    public void setParameters(List<BigDecimal> parameters) {
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
