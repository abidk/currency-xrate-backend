package abid.currency.xrate.api.service;

import abid.currency.xrate.api.dao.RatesDao;
import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Quote;
import abid.currency.xrate.api.model.Rate;
import abid.currency.xrate.api.model.Rates;
import abid.currency.xrate.api.service.ratesapi.RatesApi;
import abid.currency.xrate.api.service.ratesapi.RatesResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ExchangeServiceTest {
    @Mock
    private RatesDao ratesDao;
    @Mock
    private RatesApi ratesApi;

    private LocalDate givenDate;
    private Currency givenBase;
    private Rates result;

    @Test
    public void givenNewRateRequestShouldCallDaoAndApi() {
        givenBase("EUR");
        givenDate("2011-01-01");
        givenDaoRequestReturnsNull();
        givenApiRequestReturnsRates();

        whenCallingService();

        thenApiIsCalled();
        thenDaoStoreIsCalled();
        thenResultIsCorrect();
    }

    @Test
    public void givenExistingRateRequestShouldCallDaoOnly() {
        givenBase("EUR");
        givenDate("2011-01-01");
        givenDaoRequestReturnsRates();

        whenCallingService();

        thenApiIsNotCalled();
        thenDaoStoreIsNotCalled();
        thenResultIsCorrect();
    }

    private void thenResultIsCorrect() {
        assertEquals(givenBase, result.getBase());
        assertEquals(1, result.getRates().size());
        assertEquals("2", result.getRate(Currency.of("USD")).get().getQuote().getValue());
    }

    private void thenDaoStoreIsNotCalled() {
        verify(ratesDao, times(0)).store(givenBase, givenDate, result);
    }

    private void thenDaoStoreIsCalled() {
        verify(ratesDao).store(givenBase, givenDate, result);
    }

    private void thenApiIsNotCalled() {
        verifyNoInteractions(ratesApi);
    }

    private void thenApiIsCalled() {
        verify(ratesApi).getHistoricalData(givenBase, givenDate);
    }

    private void givenApiRequestReturnsRates() {
        Map<String, String> rates = new HashMap<>();
        rates.put("USD", "2");

        when(ratesApi.getHistoricalData(givenBase, givenDate))
                .thenReturn(new RatesResult(givenBase.getValue(), givenDate, rates));
    }

    private void givenDaoRequestReturnsNull() {
        when(ratesDao.get(givenBase, givenDate)).thenReturn(null);
    }

    private void givenDaoRequestReturnsRates() {
        when(ratesDao.get(givenBase, givenDate))
                .thenReturn(new Rates(givenBase, List.of(new Rate(Currency.of("USD"), Quote.of("2")))));
    }

    private void givenDate(String date) {
        givenDate = LocalDate.parse(date);
    }

    private void givenBase(String currency) {
        givenBase = Currency.of(currency);
    }

    private void whenCallingService() {
        final ExchangeService service = new ExchangeService(ratesDao, ratesApi);
        result = service.retrieveRates(givenBase, givenDate);
    }
}