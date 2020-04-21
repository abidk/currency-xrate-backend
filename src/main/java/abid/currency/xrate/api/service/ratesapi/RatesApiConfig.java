package abid.currency.xrate.api.service.ratesapi;

import abid.currency.xrate.api.model.Currency;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
@ConfigurationProperties(prefix = "rates-api")
public class RatesApiConfig {

    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHistoricalRatesUrl(Currency base, final LocalDate date) {
        final String formattedDate = date.format(DATE_FORMAT);
        return String.format("%s/%s?base=%s", this.url, formattedDate, base.getValue());
    }
}
