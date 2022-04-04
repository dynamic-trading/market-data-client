package dynamic.trading.market.data.client.constant;

import org.agrona.collections.Int2ObjectHashMap;

public class Exchange {
    public static final int BITMEX = 1000;
    public static final int FTX = 2000;

    public static final int BINANCE = 3000;
    public static final int HUOBI = 4000;

    public static final int OKEX = 5000;
    public static final int KRAKEN = 6000;

    public static final int DERIBIT = 7000;
    public static final int COINBASE = 8000;

    public static final int BITSTAMP = 9000;

    private static final Int2ObjectHashMap<String> EXCHANGE_MAP;

    static {
        EXCHANGE_MAP = new Int2ObjectHashMap();
        EXCHANGE_MAP.put(BITMEX, "BITMEX");

        EXCHANGE_MAP.put(FTX, "FTX");
        EXCHANGE_MAP.put(BINANCE, "BINANCE");

        EXCHANGE_MAP.put(HUOBI, "HUOBI");
        EXCHANGE_MAP.put(OKEX, "OKEX");

        EXCHANGE_MAP.put(KRAKEN, "KRAKEN");
        EXCHANGE_MAP.put(DERIBIT, "DERIBIT");

        EXCHANGE_MAP.put(COINBASE, "COINBASE");
        EXCHANGE_MAP.put(BITSTAMP, "BITSTAMP");
    }

    public static String getExchangeName(final int exchangeId){
        return EXCHANGE_MAP.get(exchangeId);
    }
}
