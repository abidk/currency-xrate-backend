package abid.currency.xrate.api.model;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Rates {

    private final Currency base;
    private final List<Rate> rates;

    public Rates(Currency base, List<Rate> rates) {
        this.base = base;
        this.rates = rates;
    }

    public Currency getBase() {
        return base;
    }

    public List<Rate> getRates() {
        return rates;
    }

    public Optional<Rate> getRate(Currency currency) {
        return rates.stream()
                .filter(rate -> rate.getCurrency().equals(currency))
                .findFirst();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Rates other = (Rates) o;
        return Objects.equals(base, other.base) &&
                Objects.equals(rates, other.rates);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, rates);
    }

    @Override
    public String toString() {
        return "Rates{" +
                "base=" + base +
                ", rates=" + rates +
                '}';
    }


}
