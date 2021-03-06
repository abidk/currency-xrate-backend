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
    public void shouldReturnNullWhenResultDoesNotExist() {
        RatesDao service = new InMemoryRatesDao();
        Rates result = service.retrieve(Currency.of("EUR"), LocalDate.now());

        assertNull(result);
    }

    @Test
    public void shouldStoreAndReturnCorrectResult() {
        Currency givenCurrency = Currency.of("EUR");
        LocalDate givenDate = LocalDate.now();
        Rates givenRates = buildRates(givenCurrency);

        // store data
        RatesDao service = new InMemoryRatesDao();
        service.store(givenCurrency, givenDate, givenRates);

        // retrieve data
        Rates results = service.retrieve(givenCurrency, givenDate);

        // assert result
        assertEquals(givenRates, results);
    }

    @Test
    public void shouldContainOneMemoryStore() {
        Currency givenCurrency = Currency.of("EUR");
        LocalDate givenDate = LocalDate.now();
        Rates givenRates = buildRates(givenCurrency);

        // store data
        RatesDao service1 = new InMemoryRatesDao();
        service1.store(givenCurrency, givenDate, givenRates);
        assertEquals(givenRates, service1.retrieve(givenCurrency, givenDate));

        // load a different and check the data exists
        RatesDao service2 = new InMemoryRatesDao();
        assertEquals(givenRates, service2.retrieve(givenCurrency, givenDate));
    }

    private Rates buildRates(Currency currency) {
        return new Rates(currency, List.of(new Rate(Currency.of("USD"), Quote.of("1"))));
    }
}