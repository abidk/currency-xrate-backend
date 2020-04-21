package abid.currency.xrate.api.service.ratesapi;

import abid.currency.xrate.api.model.Currency;
import org.springframework.web.client.HttpClientErrorException;

import java.time.LocalDate;

public class RatesRequestFailedException extends RuntimeException {

    public RatesRequestFailedException(Currency base, LocalDate date, HttpClientErrorException e) {
        super("Failed to request for Base='" + base + "' Date='" + date + "'", e);
    }
}
