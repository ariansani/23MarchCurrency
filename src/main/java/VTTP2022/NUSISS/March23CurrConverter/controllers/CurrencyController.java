package VTTP2022.NUSISS.March23CurrConverter.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import VTTP2022.NUSISS.March23CurrConverter.models.Currency;
import VTTP2022.NUSISS.March23CurrConverter.services.CurrencyService;

@Controller
@RequestMapping("")
public class CurrencyController {
    
    @Autowired
    CurrencyService currSvc;

    @GetMapping
    public String getCurrency(Model model){

        List<Currency> currencyList = currSvc.getAllCurrencies(); 

        model.addAttribute("currencies",currencyList);
        return "index";
    }

}
