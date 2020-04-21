package abid.currency.xrate.api.dao;

import abid.currency.xrate.api.model.Currency;
import abid.currency.xrate.api.model.Rates;

import java.time.LocalDate;

public interface RatesDao {

    void store(Currency base, LocalDate date, Rates rates);

    Rates retrieve(Currency base, LocalDate date);
}
