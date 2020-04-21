package abid.currency.xrate.api.service.ratesapi;

import abid.currency.xrate.api.model.Currency;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class DefaultRatesApi implements RatesApi {

    private final RestTemplate restTemplate;
    private final RatesApiConfig ratesApiConfig;

    @Autowired
    public DefaultRatesApi(RestTemplate restTemplate, RatesApiConfig ratesApiConfig) {
        this.restTemplate = restTemplate;
        this.ratesApiConfig = ratesApiConfig;
    }

    @Override
    public RatesResult getHistoricalData(Currency base, final LocalDate date) {
        try {
            final String historicalEndpoint = ratesApiConfig.getHistoricalRatesUrl(base, date);
            return restTemplate.getForObject(historicalEndpoint, RatesResult.class);
        } catch (HttpClientErrorException e) {
            throw new RatesRequestFailedException(base, date, e);
        }
    }
}
