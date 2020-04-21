package abid.currency.xrate.api.dao;

import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Rates;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryRatesDao implements RatesDao {

    private final Map<String, Rates> MEMORY_STORE = new HashMap<>();

    @Override
    public void store(Currency base, LocalDate date, Rates rates) {
        final String key = buildKey(base, date);
        MEMORY_STORE.put(key, rates);
    }

    @Override
    public Rates retrieve(Currency base, LocalDate date) {
        final String key = buildKey(base, date);
        return MEMORY_STORE.get(key);
    }

    private String buildKey(Currency base, LocalDate date) {
        return base + "_" + date.toEpochDay();
    }
}
