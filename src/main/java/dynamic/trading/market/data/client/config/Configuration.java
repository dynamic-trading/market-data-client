package dynamic.trading.market.data.client.config;

import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

public class Configuration {
    private final String CONFIG = "market-data-client.config";
    private Config config;

    private static class Holder{
        private static final Configuration INSTANCE = new Configuration();
    }

    public static Configuration getInstance(){
        return Holder.INSTANCE;
    }

    private Configuration() { }

    public void load(){
        try{
            String rootPath = new File(".").getCanonicalPath();

            String fileSuffix = rootPath.contains("/") ? "/" : "\\src\\main\\resources\\";
            String path = rootPath + fileSuffix;

            File ff = new File(path+CONFIG);
            BufferedReader reader = new BufferedReader(new FileReader(ff));

            Gson gson = new Gson();
            this.config = gson.fromJson(reader, Config.class);
        }catch (Exception ex){
            ex.printStackTrace();
            System.exit(1);
        }
    }

    public Config getConfig() {
        return config;
    }
}
