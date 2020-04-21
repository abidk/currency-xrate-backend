package abid.currency.xrate.api.service;

import abid.currency.xrate.api.dao.RatesDao;
import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Quote;
import abid.currency.xrate.api.model.Rate;
import abid.currency.xrate.api.model.Rates;
import abid.currency.xrate.api.service.ratesapi.RatesApi;
import abid.currency.xrate.api.service.ratesapi.RatesResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExchangeService {

    private final RatesDao ratesDao;
    private final RatesApi ratesApi;

    @Autowired
    public ExchangeService(RatesDao ratesDao, RatesApi ratesApi) {
        this.ratesDao = ratesDao;
        this.ratesApi = ratesApi;
    }

    public Rates retrieveRates(Currency base, LocalDate date) {
        Rates results = ratesDao.get(base, date);
        if (results == null) {
            RatesResult historicalData = ratesApi.getHistoricalData(base, date);
            results = convert(historicalData);
            ratesDao.store(base, date, results);
        }
        return results;
    }

    private Rates convert(RatesResult data) {
        final Currency base = Currency.of(data.getBase());
        final List<Rate> rates = convert(data.getRates());
        return new Rates(base, rates);
    }

    private List<Rate> convert(Map<String, String> data) {
        return data.entrySet().stream().map(item -> {
            final Currency currency = Currency.of(item.getKey());
            final Quote quote = Quote.of(item.getValue());
            return new Rate(currency, quote);
        }).collect(Collectors.toList());
    }
}
