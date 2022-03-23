package VTTP2022.NUSISS.March23CurrConverter.models;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import jakarta.json.JsonValue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Currency {
    private String currencyId;
    private String currencyName;
    private String country;
    private String alpha3;
    private String id;
    private String currencySymbol;

    private static final Logger logger = LoggerFactory.getLogger(Currency.class);
    
    public String getCurrencyId() {
        return currencyId;
    }
    public void setCurrencyId(String currencyId) {
        this.currencyId = currencyId;
    }
    public String getCurrencyName() {
        return currencyName;
    }
    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }
    public String getCountry() {
        return country;
    }
    public void setCountry(String country) {
        this.country = country;
    }
    public String getAlpha3() {
        return alpha3;
    }
    public void setAlpha3(String alpha3) {
        this.alpha3 = alpha3;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCurrencySymbol() {
        return currencySymbol;
    }
    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public static List<Currency> create(String json) throws IOException{

        List<Currency> cList = new LinkedList<>();
        try(InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            JsonObject allCountries = o.getJsonObject("results");
            
            for (String keyStr : allCountries.keySet()) {
                Currency c = new Currency();
                JsonObject keyvalue = allCountries.getJsonObject(keyStr);
        
                logger.info("key: "+ keyStr + " value: " + keyvalue);
                c.alpha3 = keyvalue.getString("alpha3");
                c.country = keyvalue.getString("name");
                c.currencyId = keyvalue.getString("currencyId");
                c.currencyName = keyvalue.getString("currencyName");
                c.currencySymbol = keyvalue.getString("currencySymbol");
                c.id = keyvalue.getString("id");
                cList.add(c);
            }
        }

        return cList;
    }

    public static Double convert(String json) throws IOException{

        double convertedAmt=0;
        try(InputStream is = new ByteArrayInputStream(json.getBytes(StandardCharsets.UTF_8))){
            JsonReader r = Json.createReader(is);
            JsonObject o = r.readObject();
            Collection<JsonValue> values = o.values();
            for(JsonValue value : values){
                convertedAmt= Double.parseDouble(value.toString());
            }
        }

        return convertedAmt;
    }

}
