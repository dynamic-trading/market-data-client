package dynamic.trading.market.data.client.constant;

import org.agrona.collections.Int2ObjectHashMap;
import java.util.HashSet;
import java.util.Set;

public class Instrument {
    public static int BTC_SPOT = 3129;
    public static int ETH_SPOT = 3130;

    public static int BCH_SPOT = 3131;
    public static int LINK_SPOT = 3132;

    public static int LTC_SPOT = 3133;
    public static int XRP_SPOT = 3134;

    public static int GRT_SPOT = 3135;

    public static int BTC_PERPETUAL = 7129;
    public static int ETH_PERPETUAL = 7130;

    public static int BCH_PERPETUAL = 7131;
    public static int LINK_PERPETUAL = 7132;

    public static int LTC_PERPETUAL = 7133;
    public static int XRP_PERPETUAL = 7134;

    public static int GRT_PERPETUAL = 7135;

    private static final Int2ObjectHashMap<String> INSTRUMENT_MAP;

    static {
        INSTRUMENT_MAP = new Int2ObjectHashMap();
        INSTRUMENT_MAP.put(BTC_SPOT, "BTC_SPOT");
        INSTRUMENT_MAP.put(ETH_SPOT, "ETH_SPOT");

        INSTRUMENT_MAP.put(BCH_SPOT, "BCH_SPOT");
        INSTRUMENT_MAP.put(LINK_SPOT, "LINK_SPOT");

        INSTRUMENT_MAP.put(LTC_SPOT, "LTC_SPOT");
        INSTRUMENT_MAP.put(XRP_SPOT, "XRP_SPOT");

        INSTRUMENT_MAP.put(GRT_SPOT, "GRT_SPOT");
        INSTRUMENT_MAP.put(BTC_PERPETUAL, "BTC_PERPETUAL");

        INSTRUMENT_MAP.put(ETH_PERPETUAL, "ETH_PERPETUAL");
        INSTRUMENT_MAP.put(BCH_PERPETUAL, "BCH_PERPETUAL");

        INSTRUMENT_MAP.put(LINK_PERPETUAL, "LINK_PERPETUAL");
        INSTRUMENT_MAP.put(LTC_PERPETUAL, "LTC_PERPETUAL");

        INSTRUMENT_MAP.put(XRP_PERPETUAL, "XRP_PERPETUAL");
        INSTRUMENT_MAP.put(GRT_PERPETUAL, "GRT_PERPETUAL");
    }

    public static String getInstrumentName(final int instrumentId){
        return INSTRUMENT_MAP.get(instrumentId);
    }

    public static Set<Integer> getAllInstruments(){
        return new HashSet<>(INSTRUMENT_MAP.keySet());
    }
}
