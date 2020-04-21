package abid.currency.xrate.api.integration;

import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.service.ratesapi.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class RatesApiIntegrationTest {

    @Autowired
    private RatesApiConfig config;
    @Autowired
    private RestTemplate restTemplate;

    private LocalDate givenDate;
    private RatesApi service;
    private RatesResult result;
    private Currency givenBase;

    @Test()
    public void whenCallingRatesServiceForUnknownBaseShouldReturnExpectedResults() {
        givenDefaultApi();
        givenBaseCurrency("ABC");
        givenDate(LocalDate.of(2011, 01, 01));

        assertThrows(RatesRequestFailedException.class, () -> {
            whenCallingService();
        });
    }

    @Test
    public void whenCallingRatesServiceShouldReturnExpectedResultsForUSD() {
        givenDefaultApi();
        givenBaseCurrency("USD");
        givenDate(LocalDate.of(2011, 01, 01));

        whenCallingService();

        thenResultHasCorrectBase("USD");
        thenResultHasCorrectDate(LocalDate.of(2010, 12, 31));
        thenResultHasExpectedRate("GBP", "0.6441775183");
    }

    @Test
    public void whenCallingRatesServiceShouldReturnExpectedResultsForEUR() {
        givenDefaultApi();
        givenBaseCurrency("EUR");
        givenDate(LocalDate.of(2011, 01, 01));

        whenCallingService();

        thenResultHasCorrectBase("EUR");
        thenResultHasCorrectDate(LocalDate.of(2010, 12, 31));
        thenResultHasExpectedRate("GBP", "0.86075");
        thenResultHasExpectedRate("HKD", "10.3856");
        thenResultHasExpectedRate("IDR", "12002.14");
        thenResultHasExpectedRate("PLN", "3.975");
        thenResultHasExpectedRate("DKK", "7.4535");
        thenResultHasExpectedRate("LVL", "0.7094");
        thenResultHasExpectedRate("INR", "59.758");
        thenResultHasExpectedRate("CHF", "1.2504");
        thenResultHasExpectedRate("MXN", "16.5475");
        thenResultHasExpectedRate("CZK", "25.061");
        thenResultHasExpectedRate("SGD", "1.7136");
        thenResultHasExpectedRate("THB", "40.17");
        thenResultHasExpectedRate("BGN", "1.9558");
        thenResultHasExpectedRate("MYR", "4.095");
        thenResultHasExpectedRate("NOK", "7.8");
        thenResultHasExpectedRate("CNY", "8.822");
        thenResultHasExpectedRate("HRK", "7.383");
        thenResultHasExpectedRate("PHP", "58.3");
        thenResultHasExpectedRate("SEK", "8.9655");
        thenResultHasExpectedRate("LTL", "3.4528");
        thenResultHasExpectedRate("ZAR", "8.8625");
        thenResultHasExpectedRate("CAD", "1.3322");
        thenResultHasExpectedRate("BRL", "2.2177");
        thenResultHasExpectedRate("RON", "4.262");
        thenResultHasExpectedRate("EEK", "15.6466");
        thenResultHasExpectedRate("NZD", "1.72");
        thenResultHasExpectedRate("TRY", "2.0694");
        thenResultHasExpectedRate("JPY", "108.65");
        thenResultHasExpectedRate("RUB", "40.82");
        thenResultHasExpectedRate("KRW", "1499.06");
        thenResultHasExpectedRate("USD", "1.3362");
        thenResultHasExpectedRate("HUF", "277.95");
        thenResultHasExpectedRate("AUD", "1.3136");
    }

    private void givenBaseCurrency(String base) {
        this.givenBase = Currency.of(base);
    }

    private void thenResultHasExpectedRate(String expectedCurrency, String expectedRate) {
        final String actualRate = result.getRates().get(expectedCurrency);
        assertEquals(expectedRate, actualRate);
    }

    private void thenResultHasCorrectBase(String expectedCurrency) {
        assertEquals(expectedCurrency, result.getBase());
    }

    private void thenResultHasCorrectDate(LocalDate expectedDate) {
        assertEquals(expectedDate, result.getDate());
    }

    private void whenCallingService() {
        result = service.getHistoricalData(givenBase, givenDate);
    }

    private void givenDate(LocalDate date) {
        this.givenDate = date;
    }

    private void givenDefaultApi() {
        service = new DefaultRatesApi(restTemplate, config);
    }
}