package VTTP2022.NUSISS.March23CurrConverter.services;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.LinkedList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import VTTP2022.NUSISS.March23CurrConverter.models.Currency;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class CurrencyService {
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);
    private static final String URL = "https://free.currconv.com/api/v7/%s";

    // SET FREE_CURR_CONV= your api key
    @Value("${free.curr.conv}")
    private String apiKey;

    private boolean hasKey;
    private List<Currency> currencyAll;
    

    @PostConstruct
    private void init() {
        hasKey = null != apiKey;
        logger.info(">>>>API KEY SET: %s  = %s".formatted(hasKey, apiKey));
        currencyAll = getAllCurrencies();
    }

    
 


    public List<Currency> getAllCurrencies() {
        String currencyUrl = UriComponentsBuilder
                .fromUriString(URL.formatted("countries/"))
                .queryParam("apiKey", apiKey)
                .toUriString();

 
        RequestEntity req = RequestEntity
                .get(currencyUrl)
                .accept(MediaType.APPLICATION_JSON)
                .build();

    
        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;
        

        List<Currency> currencyList = new LinkedList<>();
        try {
            resp = template.exchange(req, String.class);
            currencyList = Currency.create(resp.getBody());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }
        return currencyList;
    }

    public Double convertCurrency(Integer amount, String fromCurrency, String toCurrency){


        String convertUrl = UriComponentsBuilder
        .fromUriString(URL.formatted("convert/"))
        .queryParam("q",fromCurrency + "_"+ toCurrency)
        .queryParam("compact","ultra")
        .queryParam("apiKey", apiKey)
        .toUriString();

   
        RequestEntity req = RequestEntity
                .get(convertUrl)
                .accept(MediaType.APPLICATION_JSON)
                .acceptCharset(StandardCharsets.UTF_8)
                .build();


        RestTemplate template = new RestTemplate();

        ResponseEntity<String> resp = null;
        resp = template.exchange(req, String.class);

        logger.info(">>>>>>"+ resp.getBody());
        
        double convertedAmt=0;
        try {
            
            convertedAmt = Currency.convert(resp.getBody());
        } catch (Exception e) {
            //TODO: handle exception
            e.printStackTrace();
        }

        return convertedAmt;
    }

}
