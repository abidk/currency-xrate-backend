package abid.currency.xrate.api.service.ratesapi;

import java.beans.ConstructorProperties;
import java.time.LocalDate;
import java.util.Map;

public final class RatesResult {

    private final String base;
    private final LocalDate date;
    private final Map<String, String> rates;

    @ConstructorProperties({"base", "date", "rates"})
    public RatesResult(String base, LocalDate date, Map<String, String> rates) {
        this.base = base;
        this.date = date;
        this.rates = rates;
    }

    public String getBase() {
        return base;
    }

    public LocalDate getDate() {
        return date;
    }

    public Map<String, String> getRates() {
        return rates;
    }

    @Override
    public String toString() {
        return "RatesResponse{" +
                "base='" + base + '\'' +
                ", date='" + date + '\'' +
                ", rates=" + rates +
                '}';
    }
}
