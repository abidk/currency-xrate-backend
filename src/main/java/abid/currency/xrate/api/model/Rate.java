package abid.currency.xrate.api.model;

import java.util.Objects;

public final class Rate {

    private final Currency currency;
    private final Quote quote;

    public Rate(Currency currency, Quote quote) {
        this.currency = currency;
        this.quote = quote;
    }

    public Currency getCurrency() {
        return currency;
    }

    public Quote getQuote() {
        return quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rate rate = (Rate) o;
        return Objects.equals(currency, rate.currency) &&
                Objects.equals(quote, rate.quote);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, quote);
    }

    @Override
    public String toString() {
        return "Rate{" +
                "currency=" + currency +
                ", quote=" + quote +
                '}';
    }
}
