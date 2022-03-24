package VTTP2022.NUSISS.March23CurrConverter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import VTTP2022.NUSISS.March23CurrConverter.models.Currency;
import VTTP2022.NUSISS.March23CurrConverter.services.CurrencyService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping
public class CurrencyController {
    
    private static final Logger logger = LoggerFactory.getLogger(CurrencyService.class);

    @Autowired
    CurrencyService currSvc;

    @GetMapping
    public String getCurrency(Model model){

        List<Currency> currencyList = currSvc.getAllCurrencies(); 

        model.addAttribute("currencies",currencyList);
        return "index";
    }

    @PostMapping(path= "/convert", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String convertCurrency(
     @RequestBody MultiValueMap<String,String> form,
    Model model){
        String fromCurrency = form.getFirst("from_currency");
        String toCurrency = form.getFirst("to_currency");
        Integer amount = Integer.parseInt(form.getFirst("amount"));

        Double result = currSvc.convertCurrency(amount, fromCurrency, toCurrency);
        
        // logger.info("This is from currency"+fromCurrency);
        // logger.info("This is to currency"+toCurrency);
        // logger.info("This is amount"+amount);
        // logger.info("This is result" +result);
        result = result * amount;
        model.addAttribute("fromCurrency", fromCurrency);
        model.addAttribute("toCurrency", toCurrency);
        model.addAttribute("fromInitialAmount", amount);
        model.addAttribute("result",result);
        return "converted";
    }

  

}
