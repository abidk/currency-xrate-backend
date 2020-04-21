package abid.currency.xrate.api.dao;

import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Quote;
import abid.currency.xrate.api.model.Rate;
import abid.currency.xrate.api.model.Rates;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class InMemoryRatesDaoTest {

    @Test
    public void shouldStoreNullWhenResultDoesNotExist() {
        RatesDao service = new InMemoryRatesDao();
        Rates result = service.get(Currency.of("EUR"), LocalDate.now());

        assertNull(result);
    }

    @Test
    public void shouldStoreAndReturnCorrectResult() {
        Currency givenCurrency = Currency.of("EUR");
        LocalDate givenDate = LocalDate.now();
        Rates givenRates = new Rates(givenCurrency, List.of(new Rate(Currency.of("USD"), Quote.of("1"))));

        // store data
        RatesDao service = new InMemoryRatesDao();
        service.store(givenCurrency, givenDate, givenRates);

        // retrieve data
        Rates results = service.get(givenCurrency, givenDate);

        // assert results
        assertEquals(givenRates, results);
    }
}