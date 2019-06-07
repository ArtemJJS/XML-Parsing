package by.anelkin.xmlparsing.entity;

import org.apache.log4j.Logger;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.Objects;

import static by.anelkin.xmlparsing.entity.Tariff.TariffParameters.ACTIVATION_PAYMENT;
import static by.anelkin.xmlparsing.entity.Tariff.TariffParameters.FAVORITE_NUMBERS_AMOUNT;
import static by.anelkin.xmlparsing.entity.Tariff.TariffParameters.TARIFICATION;

public class Tariff {
    private static final Logger logger = Logger.getLogger(Tariff.class);
    private String id;
    private String name;
    private String operatorName;
    private BigDecimal payRoll;
    private BigDecimal smsPrice;
    private Map<CallPriceParameters, BigDecimal> callPrices;
    private Map<TariffParameters, String> tariffParameters;
    private Date openDate;
    //default closeDate 01.01.2030 (other constructors are deprecated):
    private Date closeDate = new Date(1893448800000L);

    private static final String DEFAULT_ACTIVATION_PAYMENT = "0";
    private static final String DEFAULT_TARIFICATION_INTERVAL_SECONDS = "1";
    private static final String DEFAULT_FAVORITE_NUMBER_AMOUNT = "0";
    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");


    public enum CallPriceParameters {
        INSIDE_NETWORK,
        OUTSIDE_NETWORK,
        ON_LAND_LINE,
        ON_FAVORITE_NUMBER
    }

    public enum TariffParameters {
        TARIFICATION,
        ACTIVATION_PAYMENT,
        FAVORITE_NUMBERS_AMOUNT
    }

    public Tariff() {
        logger.debug("Tariff without parameters created.");
    }

    public Tariff(String id, String name, String operatorName, BigDecimal payRoll, Map<CallPriceParameters, BigDecimal> callPrices,
                  BigDecimal smsPrice, Map<TariffParameters, String> tariffParameters, Date openDate, Date closeDate) {
        this.id = id;
        this.name = name;
        this.operatorName = operatorName;
        this.payRoll = payRoll;
        this.callPrices = callPrices;
        this.smsPrice = smsPrice;
        this.openDate = openDate;

        if (closeDate != null) {
            this.closeDate = closeDate;
        }

        initDefaultTariffParameters(tariffParameters);
        logger.debug("Tariff created. id: " + id);
    }


    private void initDefaultTariffParameters(Map<TariffParameters, String> parameters) {
        tariffParameters = parameters;
        if (!tariffParameters.containsKey(ACTIVATION_PAYMENT)) {
            tariffParameters.put(ACTIVATION_PAYMENT, DEFAULT_ACTIVATION_PAYMENT);
        }
        if (!tariffParameters.containsKey(FAVORITE_NUMBERS_AMOUNT)) {
            tariffParameters.put(FAVORITE_NUMBERS_AMOUNT, DEFAULT_FAVORITE_NUMBER_AMOUNT);
        }
        if (!tariffParameters.containsKey(TARIFICATION)) {
            tariffParameters.put(TARIFICATION, DEFAULT_TARIFICATION_INTERVAL_SECONDS);
        }
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
                + " | tariffParameters: " + tariffParameters + "\n");
        return builder.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || o.getClass() != this.getClass()) return false;
        Tariff tariff = (Tariff) o;
        return Objects.equals(getId(), tariff.getId()) &&
                Objects.equals(getName(), tariff.getName()) &&
                Objects.equals(getOperatorName(), tariff.getOperatorName()) &&
                Objects.equals(getPayRoll(), tariff.getPayRoll()) &&
                Objects.equals(getSmsPrice(), tariff.getSmsPrice()) &&
                Objects.equals(getCallPrices(), tariff.getCallPrices()) &&
                Objects.equals(getTariffParameters(), tariff.getTariffParameters()) &&
                Objects.equals(getOpenDate(), tariff.getOpenDate()) &&
                Objects.equals(getCloseDate(), tariff.getCloseDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getOperatorName(), getPayRoll(),
                getSmsPrice(), getCallPrices(), getTariffParameters(), getOpenDate(), getCloseDate());
    }

    public Map<TariffParameters, String> getTariffParameters() {
        return tariffParameters;
    }

    //setting default values, if not exist in xml
    public void setTariffParameters(Map<TariffParameters, String> tariffParameters) {
        this.tariffParameters = tariffParameters;
        initDefaultTariffParameters(tariffParameters);
    }

    public void setCloseDate(Date closeDate) {
        this.closeDate = closeDate;
    }

    public Date getCloseDate() {
        return closeDate;
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


    public Date getOpenDate() {
        return openDate;
    }

    public void setOpenDate(Date openDate) {
        this.openDate = openDate;
    }
}
