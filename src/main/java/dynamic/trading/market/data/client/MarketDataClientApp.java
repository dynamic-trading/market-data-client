package dynamic.trading.market.data.client;

import dynamic.trading.market.data.client.config.Configuration;
import dynamic.trading.market.data.client.network.MDBootstrap;

public class MarketDataClientApp {
    public static void main(String[] args) {
        try{
            Configuration.getInstance().load();
            MDBootstrap mdBootstrap = new MDBootstrap();
            mdBootstrap.connect();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
