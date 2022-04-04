package dynamic.trading.market.data.client.constant;

public class MdEntryType {
    public static final int BID = 48;
    public static final int OFFER = 49;
    public static final int TRADE = 50;

    public static String mdEntryTypeToString(final int mdEntryType){
        switch (mdEntryType){
            case BID:
                return "Bid";
            case OFFER:
                return "Offer";
            case TRADE:
                return "Trade";
            default:
                return null;
        }
    }
}
