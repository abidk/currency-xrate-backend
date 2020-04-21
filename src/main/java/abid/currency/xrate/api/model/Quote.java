package abid.currency.xrate.api.model;

import java.util.Objects;

public final class Quote {

    private final String value;

    public Quote(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Quote quote = (Quote) o;
        return Objects.equals(value, quote.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return "Quote{" +
                "value='" + value + '\'' +
                '}';
    }

    public static Quote of(String value) {
        return new Quote(value);
    }
}
