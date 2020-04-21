package abid.currency.xrate.api.service.ratesapi;

import abid.currency.xrate.api.model.Currency;

import java.time.LocalDate;

public interface RatesApi {
    RatesResult getHistoricalData(Currency base, LocalDate date);
}
