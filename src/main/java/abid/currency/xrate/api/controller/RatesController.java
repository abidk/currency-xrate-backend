package abid.currency.xrate.api.controller;

import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Rates;
import abid.currency.xrate.api.service.ExchangeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/rates")
public class RatesController {

    private static final Logger LOG = LoggerFactory.getLogger(RatesController.class);
    private final ExchangeService exchangeService;

    @Autowired
    public RatesController(ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @GetMapping("/{currency:[A-Z]{3}}/{date}")
    public RatesResponse rates(@PathVariable String currency, @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        LOG.info("Received rates request for Currency='{}' and Date='{}'", currency, date);
        final Rates rates = exchangeService.retrieveRates(Currency.of(currency), date);
        return new RatesResponse(rates);
    }
}