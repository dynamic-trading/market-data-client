package dynamic.trading.market.data.client.service;
import dynamic.trading.market.data.client.constant.Exchange;
import dynamic.trading.market.data.client.constant.Instrument;
import dynamic.trading.market.data.client.constant.MdEntryType;
import dynamic.trading.market.data.client.constant.MessageType;
import io.netty.buffer.ByteBuf;

public class MDService {
    public MDService(){ }

    public void read(final ByteBuf byteBuf){
        int bufOffset = 0;
        byte messageType = byteBuf.getByte(bufOffset);
        switch (messageType){
            case MessageType.SNAPSHOT_REFRESH:
                snapshotRefresh(byteBuf);
                break;
            case MessageType.PRICE_REQUEST_RESPONSE:
                priceRequestResponse(byteBuf);
        }
    }

    private static void priceRequestResponse(final ByteBuf byteBuf){
        int offset = Byte.BYTES;
        int instrumentId = byteBuf.getInt(offset);
        offset += Integer.BYTES;
        while(offset < byteBuf.readableBytes()){
            int exchangeId = byteBuf.getInt(offset);
            offset += Integer.BYTES;
            System.out.printf("InstrumentId: %d ExchangeId %d %n", instrumentId, exchangeId);
        }
    }

    private static void snapshotRefresh(final ByteBuf byteBuf){
        int offset = Byte.BYTES;
        while(offset < byteBuf.readableBytes()){
            int instrumentId = byteBuf.getInt(offset);
            offset += Integer.BYTES;
            int numInGroup = byteBuf.getShort(offset);
            offset += Short.BYTES;
            for (int i = 0; i < numInGroup; i++){
                byte mdEntryType = byteBuf.getByte(offset);
                offset += Byte.BYTES;
                double price = byteBuf.getDouble(offset);
                offset += Double.BYTES;
                int exchangeId = byteBuf.getInt(offset);
                offset += Integer.BYTES;
                printPriceString(mdEntryType, exchangeId, instrumentId, price);
            }
        }
    }

    private static void printPriceString(final byte mdEntryType, final int exchangeId, final int instrumentId, final double price){
        String exchangeName = Exchange.getExchangeName(exchangeId);
        String instrumentName = Instrument.getInstrumentName(instrumentId);
        String mdEntryTypeName = MdEntryType.mdEntryTypeToString(mdEntryType);
        System.out.printf("mdEntryType : %s " +
            "Exchange: %s " +
            "Instrument: %s " +
            "Price: %f %n",
            mdEntryTypeName,
            exchangeName,
            instrumentName,
            price);
    }
}
