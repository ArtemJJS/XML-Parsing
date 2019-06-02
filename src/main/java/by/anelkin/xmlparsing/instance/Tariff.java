package by.anelkin.xmlparsing.instance;

public abstract class Tariff {
    public enum TarifParameters {
        FAVORITE_NUMBERS,
        TARIFICATION,
        ACTIVATION_PAYMENT
    }

    public enum CallPriceParameters {
        INSIDENETWORK,
        OUTSIDENETWORK,
        ONLANDLINE,
        ONFAVORITENUMBER
    }

    public enum Parameters {
        FAVORITENUMBERSAMOUNT,
        TARIFICATION,
        ACTIVATIONPAYMENT
    }

    public enum Tarification{
        SECONDS_1,
        SECONDS_12,
        SECONDS_30,
        SECONDS_60
    }
}
