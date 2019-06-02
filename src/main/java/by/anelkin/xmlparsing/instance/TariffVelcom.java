package by.anelkin.xmlparsing.instance;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class TariffVelcom extends Tariff {
    private String name;
    private String operatorName;
    private BigDecimal payRoll;
    private Map<CallPriceParameters, BigDecimal> callPrices;
    private BigDecimal smsPrice;
    // TODO: 6/1/2019 как тут избавиться от object??? внутри int, double(BigDecimal) и enum
    // TODO: 6/1/2019 поставил BigDecimal, но там два инта и один BigDecimal
    //на данный момент в мапе стринг, чтобы пользователь сам выбирал тип от параметра enum'a
    private Map<Parameters, String> parameters;
    private Date openDate;
    //optional
    private Date closeDate;


    public TariffVelcom(String name, String operatorName, BigDecimal payRoll, Map<CallPriceParameters, BigDecimal> callPrices,
                        BigDecimal smsPrice, Map<Parameters, String> parameters) {
        this.name = name;
        this.operatorName = operatorName;
        this.payRoll = payRoll;
        this.callPrices = callPrices;
        this.smsPrice = smsPrice;
        this.parameters = parameters;
    }


    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Tariff. Name: " + name + ", OperatorName: " +operatorName + ", OpenDate: " + openDate);
        if (closeDate != null){
            builder.append(", CloseDate: " + closeDate);
        }
        builder.append(", PayRoll: " + payRoll + ", CallPrices: " + callPrices + ", SmsPrice: " + smsPrice
        + ", Parameters: " + parameters + "\n");
        return builder.toString();
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
